package me.silentprogram.betterspawners.core.commands;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class MainCommandTabCompleter implements TabCompleter{
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("betterspawners.reload"))
            return List.of("reload", sender.getName());

        if (sender.hasPermission("betterspawners.open"))
            return Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());

        if (sender.hasPermission("betterspawners.reload") && sender.hasPermission("betterspawners.open")){
            List<String> completeList = Bukkit.getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());
            completeList.add("reload");
            return completeList;
        }
        
        return null;
    }
    
}
