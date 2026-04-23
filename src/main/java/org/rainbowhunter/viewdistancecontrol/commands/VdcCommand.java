package org.rainbowhunter.viewdistancecontrol.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.rainbowhunter.viewdistancecontrol.ConfigManager;
import org.rainbowhunter.viewdistancecontrol.ViewDistanceManager;

import java.util.ArrayList;
import java.util.List;

public class VdcCommand implements CommandExecutor, TabCompleter {

    private final ConfigManager configManager;
    private final ViewDistanceManager viewDistanceManager;

    public VdcCommand(ConfigManager configManager, ViewDistanceManager viewDistanceManager) {
        this.configManager = configManager;
        this.viewDistanceManager = viewDistanceManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /vdc <reload|check <player>>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> handleReload(sender);
            case "check" -> handleCheck(sender, args);
            default -> sender.sendMessage("Unknown subcommand. Usage: /vdc <reload|check <player>>");
        }
        return true;
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("viewdistancecontrol.reload")) {
            sender.sendMessage("You do not have permission to do this.");
            return;
        }
        configManager.load();
        viewDistanceManager.applyAll();
        sender.sendMessage("ViewDistanceControl config reloaded.");
    }

    private void handleCheck(CommandSender sender, String[] args) {
        if (!sender.hasPermission("viewdistancecontrol.check")) {
            sender.sendMessage("You do not have permission to do this.");
            return;
        }
        if (args.length < 2) {
            sender.sendMessage("Usage: /vdc check <player>");
            return;
        }
        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            sender.sendMessage("Player not found: " + args[1]);
            return;
        }
        boolean isAfk = viewDistanceManager.isAfk(target.getUniqueId());
        int distance = target.getSendViewDistance();
        sender.sendMessage(target.getName() + " view distance: " + distance + (isAfk ? " (AFK)" : ""));
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if ("reload".startsWith(args[0].toLowerCase())) completions.add("reload");
            if ("check".startsWith(args[0].toLowerCase())) completions.add("check");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("check")) {
            String partial = args[1].toLowerCase();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().startsWith(partial)) {
                    completions.add(p.getName());
                }
            }
        }
        return completions;
    }
}
