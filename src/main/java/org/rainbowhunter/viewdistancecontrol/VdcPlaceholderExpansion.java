package org.rainbowhunter.viewdistancecontrol;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VdcPlaceholderExpansion extends PlaceholderExpansion {

    private final ViewDistanceControl plugin;

    public VdcPlaceholderExpansion(ViewDistanceControl plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "viewdistancecontrol";
    }

    @Override
    public @NotNull String getAuthor() {
        return "RainbowHunter";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        // Keep expansion registered across PAPI reloads
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) return "";
        if (params.equals("distance")) {
            return String.valueOf(player.getSendViewDistance());
        }
        return null;
    }
}
