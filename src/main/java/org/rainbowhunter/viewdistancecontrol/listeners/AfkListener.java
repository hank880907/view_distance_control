package org.rainbowhunter.viewdistancecontrol.listeners;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

import java.util.logging.Logger;

public class AfkListener implements Listener {

    private final ViewDistanceManager viewDistanceManager;
    private final Logger logger;

    public AfkListener(JavaPlugin plugin, ViewDistanceManager viewDistanceManager) {
        this.viewDistanceManager = viewDistanceManager;
        this.logger = plugin.getLogger();
    }

    @EventHandler
    public void onAfkStatusChange(AfkStatusChangeEvent event) {
        logger.info("[DEBUG] AfkListener.onAfkStatusChange triggered for " + event.getAffected().getName() + ", afk=" + event.getValue());
        viewDistanceManager.handleAfkChange(event.getAffected().getUUID(), event.getValue());
    }
}
