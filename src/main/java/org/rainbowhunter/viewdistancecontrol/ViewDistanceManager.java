package org.rainbowhunter.viewdistancecontrol;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ViewDistanceManager {

    private static final String DEFAULT_PREFIX = "viewdistancecontrol.default.";
    private static final String AFK_PREFIX = "viewdistancecontrol.afk.";

    private final ConfigManager config;
    private final Set<UUID> afkPlayers = new HashSet<>();

    public ViewDistanceManager(ConfigManager config) {
        this.config = config;
    }

    public void applyViewDistance(Player player) {
        boolean isAfk = afkPlayers.contains(player.getUniqueId());
        int distance;

        if (isAfk && config.isAfkEnabled()) {
            distance = getPermissionDistance(player, AFK_PREFIX);
            if (distance < 0) distance = config.getDefaultAfkViewDistance();
        } else {
            distance = getPermissionDistance(player, DEFAULT_PREFIX);
            if (distance < 0) distance = config.getDefaultViewDistance();
        }

        player.setViewDistance(distance);
    }

    public void applyAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            applyViewDistance(player);
        }
    }

    public void setAfk(UUID uuid, boolean afk) {
        if (afk) {
            afkPlayers.add(uuid);
        } else {
            afkPlayers.remove(uuid);
        }
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            applyViewDistance(player);
        }
    }

    public void removePlayer(UUID uuid) {
        afkPlayers.remove(uuid);
    }

    public boolean isAfk(UUID uuid) {
        return afkPlayers.contains(uuid);
    }

    private int getPermissionDistance(Player player, String prefix) {
        int highest = -1;
        for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
            if (!info.getValue()) continue;
            String node = info.getPermission();
            if (!node.startsWith(prefix)) continue;
            try {
                int val = Integer.parseInt(node.substring(prefix.length()));
                if (val > highest) highest = val;
            } catch (NumberFormatException ignored) {
            }
        }
        return highest;
    }
}
