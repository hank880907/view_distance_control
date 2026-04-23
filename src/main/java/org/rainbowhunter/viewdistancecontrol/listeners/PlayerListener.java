package org.rainbowhunter.viewdistancecontrol.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

public class PlayerListener implements Listener {

    private final ViewDistanceManager viewDistanceManager;

    public PlayerListener(ViewDistanceManager viewDistanceManager) {
        this.viewDistanceManager = viewDistanceManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        viewDistanceManager.applyViewDistance(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        viewDistanceManager.removePlayer(event.getPlayer().getUniqueId());
    }
}
