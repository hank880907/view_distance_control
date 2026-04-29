package org.rainbowhunter.viewdistancecontrol.listeners;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.rainbowhunter.viewdistancecontrol.ConfigManager;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AfkListener implements Listener {

    private final JavaPlugin plugin;
    private final ConfigManager config;
    private final ViewDistanceManager viewDistanceManager;
    private final Map<UUID, BukkitTask> pendingAfkTasks = new ConcurrentHashMap<>();

    public AfkListener(JavaPlugin plugin, ConfigManager config, ViewDistanceManager viewDistanceManager) {
        this.plugin = plugin;
        this.config = config;
        this.viewDistanceManager = viewDistanceManager;
    }

    @EventHandler
    public void onAfkStatusChange(AfkStatusChangeEvent event) {
        UUID uuid = event.getAffected().getUUID();

        if (event.getValue()) {
            // Entering AFK — cancel any stale pending task
            BukkitTask old = pendingAfkTasks.remove(uuid);
            if (old != null) old.cancel();

            int delayTicks = config.getAfkDelaySeconds() * 20;
            if (delayTicks <= 0) {
                viewDistanceManager.setAfk(uuid, true);
            } else {
                BukkitTask task = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    pendingAfkTasks.remove(uuid);
                    Player p = Bukkit.getPlayer(uuid);
                    if (p != null) viewDistanceManager.setAfk(uuid, true);
                }, delayTicks);
                pendingAfkTasks.put(uuid, task);
            }
        } else {
            // Leaving AFK — cancel pending task if AFK was never applied, else restore immediately
            BukkitTask pending = pendingAfkTasks.remove(uuid);
            if (pending != null) {
                pending.cancel();
            } else {
                viewDistanceManager.setAfk(uuid, false);
            }
        }
    }

    public void cleanup() {
        pendingAfkTasks.values().forEach(BukkitTask::cancel);
        pendingAfkTasks.clear();
    }
}
