package me.silentprogram.betterspawners.core.commands;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

public class GiveCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("spawners.give")) return null;

        if (args.length == 1){
            return Stream.of(EntityType.values()).map(type -> type.name()).collect(Collectors.toList());
        }
        if (args.length == 2){
            return List.of("<Number>");
        }

        return null;

        
    }
    
}
