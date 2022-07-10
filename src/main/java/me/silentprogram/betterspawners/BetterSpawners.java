package me.silentprogram.betterspawners;

import me.silentprogram.betterspawners.commands.MainCommand;
import me.silentprogram.betterspawners.config.DataManager;
import me.silentprogram.betterspawners.config.ConfigManager;
import me.silentprogram.betterspawners.config.classes.Data;
import me.silentprogram.betterspawners.listeners.SpawnerListener;
import me.silentprogram.betterspawners.util.Keys;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterSpawners{
    
    private final JavaPlugin plugin;
    private DataManager dataManager;
    private ConfigManager configManager;
    private Data dataConfig;
    public Keys KEYS;
    
    public BetterSpawners(JavaPlugin plugin){
        this.plugin = plugin;
    }
    
    public void onEnable() {
        plugin.saveDefaultConfig();
        startBstats();
        KEYS = new Keys(this);
        configStartup();
        initialize();
    }
    
    public void onDisable() {
        dataManager.saveConfig();
    }
    
    public Data getDataConfig() {
        return dataConfig;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    //Startup below this comment
    private void startBstats(){
        Metrics metrics = new Metrics(plugin, 15632);
        metrics.addCustomChart(new SimplePie("server_ip", () -> plugin.getServer().getIp() + ":" + plugin.getServer().getPort()));
    }
    
    private void configStartup(){
        dataManager = new DataManager(this);
        configManager = new ConfigManager(this);
        dataConfig = dataManager.getConfig();
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            dataManager.saveConfig();
        }, 0, 6000);
    }
    
    private void initialize(){
        new SpawnerListener(this);
        new MainCommand(this);
    }
    
    public JavaPlugin getPlugin() {
        return plugin;
    }
}
