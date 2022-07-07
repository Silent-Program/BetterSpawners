package me.silentprogram.betterspawners;

import me.silentprogram.betterspawners.core.commands.MainCommand;
import me.silentprogram.betterspawners.core.config.DataManager;
import me.silentprogram.betterspawners.core.config.classes.Data;
import me.silentprogram.betterspawners.core.inventorys.GuiManager;
import me.silentprogram.betterspawners.core.listeners.SpawnerListener;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterSpawners extends JavaPlugin {
	public final NamespacedKey ENTITY_TYPE_KEY = new NamespacedKey(this, "bs.entityType");
	public final NamespacedKey MULTIPLIER_KEY = new NamespacedKey(this, "bs.multiplier");
	public final NamespacedKey MINED_KEY = new NamespacedKey(this, "bs.wasMined");
	public final NamespacedKey OWNER_KEY = new NamespacedKey(this, "bs.ownerName");
	public final NamespacedKey XP_KEY = new NamespacedKey(this, "bs.xpAmount");
	public final NamespacedKey LAST_GEN_KEY = new NamespacedKey(this, "bs.lastGen");
	public GuiManager gui;
	private Data dataConfig;
	private DataManager dataManager;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		Metrics metrics = new Metrics(this, 15632);
		metrics.addCustomChart(new SimplePie("server_ip", () -> getServer().getIp() + ":" + getServer().getPort()));
		dataManager = new DataManager(this);
		dataConfig = dataManager.initializeConfig();
		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			dataManager.saveConfig();
		}, 0, 6000);
		gui = new GuiManager(this);
		new SpawnerListener(this);
		new MainCommand(this);
	}
	
	@Override
	public void onDisable() {
		dataManager.saveConfig();
	}
	
	public Data getDataConfig() {
		return dataConfig;
	}
	
}
