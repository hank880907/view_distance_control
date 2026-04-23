package org.rainbowhunter.viewdistancecontrol.listeners;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

public class LuckPermsListener {

    private final JavaPlugin plugin;
    private final LuckPerms luckPerms;
    private final ViewDistanceManager viewDistanceManager;

    public LuckPermsListener(JavaPlugin plugin, LuckPerms luckPerms, ViewDistanceManager viewDistanceManager) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
        this.viewDistanceManager = viewDistanceManager;
    }

    public void register() {
        luckPerms.getEventBus().subscribe(plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
    }

    private void onUserDataRecalculate(UserDataRecalculateEvent event) {
        Player player = Bukkit.getPlayer(event.getUser().getUniqueId());
        if (player != null) {
            // LuckPerms events fire async; schedule sync to safely call player API
            Bukkit.getScheduler().runTask(plugin, () -> viewDistanceManager.applyViewDistance(player));
        }
    }
}
