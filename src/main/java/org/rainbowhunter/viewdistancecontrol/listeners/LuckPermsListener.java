package org.rainbowhunter.viewdistancecontrol.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.rainbowhunter.viewdistancecontrol.ConfigManager;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LuckPermsListener {

    private final JavaPlugin plugin;
    private final LuckPerms luckPerms;
    private final ConfigManager config;
    private final ViewDistanceManager viewDistanceManager;
    private EventSubscription<UserDataRecalculateEvent> subscription;
    // Debounce: only the last event in a burst of UserDataRecalculateEvents takes effect.
    private final Map<UUID, BukkitTask> pending = new ConcurrentHashMap<>();

    public LuckPermsListener(JavaPlugin plugin, ConfigManager config, LuckPerms luckPerms, ViewDistanceManager viewDistanceManager) {
        this.plugin = plugin;
        this.config = config;
        this.luckPerms = luckPerms;
        this.viewDistanceManager = viewDistanceManager;
    }

    public void register() {
        subscription = luckPerms.getEventBus().subscribe(plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
    }

    public void unregister() {
        if (subscription != null) subscription.close();
        pending.values().forEach(BukkitTask::cancel);
        pending.clear();
    }

    private void onUserDataRecalculate(UserDataRecalculateEvent event) {
        UUID uuid = event.getUser().getUniqueId();
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;

        if (config.isDebug()) plugin.getLogger().info("[DEBUG] LuckPermsListener.onUserDataRecalculate triggered for " + player.getName());

        // LuckPerms fires this event multiple times per permission change from async threads;
        // synchronize the compound remove→cancel→schedule→put so concurrent events can't both
        // see an empty map and schedule duplicate tasks.
        synchronized (pending) {
            BukkitTask old = pending.remove(uuid);
            if (old != null) old.cancel();

            BukkitTask task = Bukkit.getScheduler().runTask(plugin, () -> {
                synchronized (pending) {
                    pending.remove(uuid);
                }
                if (!player.isOnline()) return;
                if (config.isDebug()) plugin.getLogger().info("[DEBUG] LuckPermsListener applying view distance for " + player.getName());
                viewDistanceManager.applyViewDistance(player);
            });
            pending.put(uuid, task);
        }
    }
}
