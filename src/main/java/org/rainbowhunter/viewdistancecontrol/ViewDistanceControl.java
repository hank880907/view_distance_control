package org.rainbowhunter.viewdistancecontrol;

import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.rainbowhunter.viewdistancecontrol.commands.VdcCommand;
import org.rainbowhunter.viewdistancecontrol.listeners.AfkListener;
import org.rainbowhunter.viewdistancecontrol.listeners.LuckPermsListener;
import org.rainbowhunter.viewdistancecontrol.listeners.PlayerListener;

public class ViewDistanceControl extends JavaPlugin {

    private ConfigManager configManager;
    private ViewDistanceManager viewDistanceManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);

        RegisteredServiceProvider<LuckPerms> luckPermsProvider =
                getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (luckPermsProvider == null) {
            getLogger().severe("LuckPerms not found. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        LuckPerms luckPerms = luckPermsProvider.getProvider();

        viewDistanceManager = new ViewDistanceManager(configManager);

        getServer().getPluginManager().registerEvents(new PlayerListener(viewDistanceManager), this);
        getServer().getPluginManager().registerEvents(new AfkListener(viewDistanceManager), this);
        new LuckPermsListener(this, luckPerms, viewDistanceManager).register();

        VdcCommand cmd = new VdcCommand(this, configManager, viewDistanceManager);
        getCommand("vdc").setExecutor(cmd);
        getCommand("vdc").setTabCompleter(cmd);
    }

    @Override
    public void onDisable() {
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ViewDistanceManager getViewDistanceManager() {
        return viewDistanceManager;
    }
}
