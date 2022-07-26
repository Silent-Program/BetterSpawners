package me.silentprogram.betterspawners.commands;

import me.silentprogram.betterspawners.StartupClass;
import me.silentprogram.betterspawners.util.SpawnerManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    private final StartupClass plugin;
    
    public MainCommand(StartupClass plugin) {
        this.plugin = plugin;
        plugin.getPlugin().getCommand("spawners").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        
        Player plr = (Player) commandSender;
        
        if (args.length > 0) {
            
            if (args[0].equals("reload")) {
                if (commandSender.hasPermission("betterspawners.reload")) {
                    plugin.getConfigManager().reloadConfigManager();
                    commandSender.sendMessage(ChatColor.AQUA + "Reloaded");
                }
                return true;
            }
            
            if (args[0].equals("give")) {
                if (commandSender.hasPermission("betterspawners.give")) {
                    if (((Player) commandSender).getInventory().firstEmpty() == -1) {
                        commandSender.sendMessage(ChatColor.AQUA + "Your inventory must be empty!");
                        return true;
                    }
                    if (args.length < 2) {
                        commandSender.sendMessage(ChatColor.AQUA + "You must Specify a player name!");
                        return true;
                    }
                    plr = Bukkit.getPlayer(args[1]);
                    
                    if (plr == null) {
                        commandSender.sendMessage(ChatColor.AQUA + "That player does not exist!!");
                        return true;
                    }
                    
                    if (args.length < 3) {
                        commandSender.sendMessage(ChatColor.AQUA + "You must specify a spawner type!");
                        return true;
                    }
                    
                    EntityType entityType;
                    
                    try {
                        entityType = EntityType.valueOf(args[2].toUpperCase());
                    } catch (IllegalArgumentException e) {
                        commandSender.sendMessage("No such spawner type exists!");
                        return true;
                    }
                    
                    int multiplier = plugin.getConfigManager().getMinedMultiplier();
                    
                    if (args.length > 3) {
                        try {
                            multiplier = Integer.parseInt(args[3]);
                        } catch (NumberFormatException e) {
                            commandSender.sendMessage(ChatColor.AQUA + "You must specify a number for the multiplier!");
                        }
                    }
                    ((Player) commandSender).getInventory().addItem(SpawnerManager.createSpawner(plugin, entityType, multiplier));
                }
                return true;
            }
            
            if (commandSender.hasPermission("betterspawners.open")) {
                plr = Bukkit.getPlayer(args[0]);
                
                if (plr == null) {
                    commandSender.sendMessage(ChatColor.AQUA + "That player does not exist!!");
                    return false;
                }
                plugin.getPlugin().getGuiManager().showGui(plr, (Player) commandSender);
                return true;
            }
        }
        plugin.getPlugin().getGuiManager().showGui(plr);
        return true;
    }
}
