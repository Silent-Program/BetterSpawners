package me.silentprogram.betterspawners.commands;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import me.silentprogram.betterspawners.BetterSpawners;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class MainCommand implements CommandExecutor {
    private final BetterSpawners plugin;
    private Map<Player, ChestGui> playerChestGuiMap;
    
    public MainCommand(BetterSpawners plugin) {
        this.plugin = plugin;
        plugin.getCommand("spawners").setExecutor(this);
    }
    
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        ChestGui gui;
        Player plr = (Player) commandSender;
        
        if (args.length > 0) {
            if (commandSender.hasPermission("betterspawners.reload")) {
                if (args[0].equals("reload")) {
                    plugin.reloadConfig();
                    commandSender.sendMessage(ChatColor.AQUA + "Reloaded");
                    return true;
                }
            }
            if (commandSender.hasPermission("betterspawners.open")) {
                plr = Bukkit.getPlayer(args[0]);
                
                if (plr == null) {
                    commandSender.sendMessage(ChatColor.AQUA + "That player does not exist!!");
                    return false;
                }
                if (playerChestGuiMap.containsKey(plr)) {
                    //gui = playerChestGuiMap.get(plr);
                    //if (gui.getViewers().size() > 0) {
                    //gui.show((HumanEntity) commandSender);
                    return true;
                }
            }
        }
        //}
        //gui = plugin.gui.createGui(plr);
        //playerChestGuiMap.put(plr, gui);
        //gui.show((HumanEntity) commandSender);
        return true;
    }
}
