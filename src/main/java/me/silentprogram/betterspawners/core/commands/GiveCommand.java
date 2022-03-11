package me.silentprogram.betterspawners.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.api.SpawnerFactory;

public class GiveCommand implements CommandExecutor {

    private final BetterSpawners plugin;

    public GiveCommand(BetterSpawners plugin){
        this.plugin = plugin;
        plugin.getCommand("givespawner").setExecutor(this);
        plugin.getCommand("givespawner").setTabCompleter(new GiveCommandTabCompleter());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;

        if (args.length < 0){
            return false;
        }

        Player plr = (Player) commandSender;

        if (commandSender.hasPermission("betterspawners.give")){
                if (args.length > 0){
                    EntityType type;
                    try {
                        type = EntityType.valueOf(args[0]);
                    } catch (IllegalArgumentException e) {
                        plr.sendMessage("Invalid Entity");
                        return false;
                    }
                    if (args.length > 1){
                        if (Integer.valueOf(args[1]) instanceof Integer){
                            Integer multiplier = Integer.valueOf(args[1]);
                            SpawnerFactory factory = new SpawnerFactory(plugin, type, multiplier, 0, System.currentTimeMillis());
                            plr.getInventory().addItem(factory.getSpawner());
                            return true;
                        }else{
                            plr.sendMessage("Invalid number");
                            return false;
                        }
                    }
                }
            }



        return false;
    }
    
}
