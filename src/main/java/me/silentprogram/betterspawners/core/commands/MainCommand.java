package me.silentprogram.betterspawners.core.commands;

import me.silentprogram.betterspawners.BetterSpawners;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
	private final BetterSpawners plugin;
	
	public MainCommand(BetterSpawners plugin) {
		this.plugin = plugin;
		plugin.getCommand("spawners").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
		plugin.gui.createGui((Player) commandSender).show((Player) commandSender);
		return false;
	}
}
