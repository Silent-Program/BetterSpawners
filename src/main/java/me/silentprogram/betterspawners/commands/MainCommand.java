package me.silentprogram.betterspawners.commands;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import me.silentprogram.betterspawners.StartupClass;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {
    private final StartupClass plugin;
    
    public MainCommand(StartupClass plugin) {
        this.plugin = plugin;
        plugin.getPlugin().getCommand("spawners").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        ChestGui gui;
        Player plr = (Player) commandSender;
        
        if (args.length > 0) {
            
            if (args[0].equals("reload")) {
                if (commandSender.hasPermission("betterspawners.reload")) {
                    plugin.getConfigManager().reloadConfigManager();
                    commandSender.sendMessage(ChatColor.AQUA + "Reloaded");
                    return true;
                }
            }
            /*
            if(args[0].equals("give")){
                if(commandSender.hasPermission("betterspawners.give")){
                    plr = Bukkit.getPlayer(args[1]);
    
                    if (plr == null) {
                        commandSender.sendMessage(ChatColor.AQUA + "That player does not exist!!");
                        return false;
                    }
                    
                    
                }
            }
            
             */
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
