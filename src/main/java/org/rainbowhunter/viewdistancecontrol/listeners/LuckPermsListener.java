package org.rainbowhunter.viewdistancecontrol.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventSubscription;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LuckPermsListener {

    private final JavaPlugin plugin;
    private final LuckPerms luckPerms;
    private final ViewDistanceManager viewDistanceManager;
    private EventSubscription<UserDataRecalculateEvent> subscription;
    // Debounce: only the last event in a burst of UserDataRecalculateEvents takes effect.
    private final Map<UUID, BukkitTask> pending = new ConcurrentHashMap<>();

    public LuckPermsListener(JavaPlugin plugin, LuckPerms luckPerms, ViewDistanceManager viewDistanceManager) {
        this.plugin = plugin;
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

        // LuckPerms fires this event multiple times per permission change; cancel the
        // previous pending task so only the final event in the burst applies the distance.
        BukkitTask old = pending.remove(uuid);
        if (old != null) old.cancel();

        BukkitTask task = Bukkit.getScheduler().runTask(plugin, () -> {
            pending.remove(uuid);
            viewDistanceManager.applyViewDistance(player);
        });
        pending.put(uuid, task);
    }
}
