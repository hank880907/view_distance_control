package org.rainbowhunter.viewdistancecontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.rainbowhunter.viewdistancecontrol.ConfigManager;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

import java.util.logging.Logger;

public class PlayerListener implements Listener {

    private final ViewDistanceManager viewDistanceManager;
    private final ConfigManager config;
    private final Logger logger;

    public PlayerListener(JavaPlugin plugin, ConfigManager config, ViewDistanceManager viewDistanceManager) {
        this.viewDistanceManager = viewDistanceManager;
        this.config = config;
        this.logger = plugin.getLogger();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (config.isDebug()) logger.info("[DEBUG] PlayerListener.onPlayerJoin triggered for " + event.getPlayer().getName());
        viewDistanceManager.applyViewDistance(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (config.isDebug()) logger.info("[DEBUG] PlayerListener.onPlayerQuit triggered for " + event.getPlayer().getName());
        viewDistanceManager.removePlayer(event.getPlayer().getUniqueId());
    }
}
