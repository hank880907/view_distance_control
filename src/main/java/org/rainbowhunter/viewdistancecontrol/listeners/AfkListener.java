package org.rainbowhunter.viewdistancecontrol.listeners;

import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

public class AfkListener implements Listener {

    private final ViewDistanceManager viewDistanceManager;

    public AfkListener(ViewDistanceManager viewDistanceManager) {
        this.viewDistanceManager = viewDistanceManager;
    }

    @EventHandler
    public void onAfkStatusChange(AfkStatusChangeEvent event) {
        viewDistanceManager.handleAfkChange(event.getAffected().getUUID(), event.getValue());
    }
}
