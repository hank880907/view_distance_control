package org.rainbowhunter.viewdistancecontrol;

import net.luckperms.api.LuckPerms;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.rainbowhunter.viewdistancecontrol.commands.VdcCommand;
import org.rainbowhunter.viewdistancecontrol.listeners.AfkListener;
import org.rainbowhunter.viewdistancecontrol.listeners.LuckPermsListener;
import org.rainbowhunter.viewdistancecontrol.listeners.PlayerListener;

public class ViewDistanceControl extends JavaPlugin {

    private LuckPermsListener luckPermsListener;
    private AfkListener afkListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigManager configManager = new ConfigManager(this);

        RegisteredServiceProvider<LuckPerms> luckPermsProvider =
                getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (luckPermsProvider == null) {
            getLogger().severe("LuckPerms not found. Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        LuckPerms luckPerms = luckPermsProvider.getProvider();

        ViewDistanceManager viewDistanceManager = new ViewDistanceManager(configManager);

        getServer().getPluginManager().registerEvents(new PlayerListener(viewDistanceManager), this);
        afkListener = new AfkListener(this, configManager, viewDistanceManager);
        getServer().getPluginManager().registerEvents(afkListener, this);
        luckPermsListener = new LuckPermsListener(this, luckPerms, viewDistanceManager);
        luckPermsListener.register();

        VdcCommand cmd = new VdcCommand(configManager, viewDistanceManager);
        PluginCommand vdc = getCommand("vdc");
        if (vdc != null) {
            vdc.setExecutor(cmd);
            vdc.setTabCompleter(cmd);
        }

        new VdcPlaceholderExpansion(this).register();
    }

    @Override
    public void onDisable() {
        if (luckPermsListener != null) luckPermsListener.unregister();
        if (afkListener != null) afkListener.cleanup();
    }
}
