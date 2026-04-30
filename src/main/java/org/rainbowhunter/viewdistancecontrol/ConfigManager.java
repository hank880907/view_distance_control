package org.rainbowhunter.viewdistancecontrol;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private final JavaPlugin plugin;
    private int defaultViewDistance;
    private int defaultAfkViewDistance;
    private boolean afkEnabled;
    private int afkDelaySeconds;
    private boolean notifyPlayer;
    private String notifyMessage;
    private boolean consoleLog;
    private boolean debug;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        plugin.reloadConfig();
        defaultViewDistance = plugin.getConfig().getInt("default-view-distance", 10);
        defaultAfkViewDistance = plugin.getConfig().getInt("default-afk-view-distance", 4);
        afkEnabled = plugin.getConfig().getBoolean("afk-view-distance-enable", true);
        afkDelaySeconds = Math.max(0, plugin.getConfig().getInt("afk-distance-delay", 5));
        notifyPlayer = plugin.getConfig().getBoolean("notify-player", false);
        notifyMessage = plugin.getConfig().getString("notify-message",
                "Your view distance has been changed to %viewdistancecontrol_distance%");
        consoleLog = plugin.getConfig().getBoolean("console-log", false);
        debug = plugin.getConfig().getBoolean("debug", false);

        int serverMax = Bukkit.getServer().getViewDistance();
        if (defaultViewDistance > serverMax) {
            plugin.getLogger().warning("default-view-distance (" + defaultViewDistance + ") exceeds server view-distance (" + serverMax + "). Clamping to " + serverMax + ".");
            defaultViewDistance = serverMax;
        }
        if (defaultAfkViewDistance > serverMax) {
            plugin.getLogger().warning("default-afk-view-distance (" + defaultAfkViewDistance + ") exceeds server view-distance (" + serverMax + "). Clamping to " + serverMax + ".");
            defaultAfkViewDistance = serverMax;
        }
    }

    public int getDefaultViewDistance() { return defaultViewDistance; }
    public int getDefaultAfkViewDistance() { return defaultAfkViewDistance; }
    public boolean isAfkEnabled() { return afkEnabled; }
    public int getAfkDelaySeconds() { return afkDelaySeconds; }
    public boolean isNotifyPlayer() { return notifyPlayer; }
    public String getNotifyMessage() { return notifyMessage; }
    public boolean isConsoleLog() { return consoleLog; }
    public boolean isDebug() { return debug; }
}
