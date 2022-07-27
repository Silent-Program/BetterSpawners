package me.silentprogram.betterspawners;

import me.silentprogram.betterspawners.commands.MainCommand;
import me.silentprogram.betterspawners.config.DataManager;
import me.silentprogram.betterspawners.config.ConfigManager;
import me.silentprogram.betterspawners.config.classes.Data;
import me.silentprogram.betterspawners.listeners.SpawnerListener;
import me.silentprogram.betterspawners.util.Keys;
import org.bstats.bukkit.Metrics;

public class StartupClass {
    
    private final MainClass plugin;
    private DataManager dataManager;
    private ConfigManager configManager;
    private final Data dataConfig;
    public Keys KEYS;
    
    public StartupClass(MainClass plugin) {
        this.plugin = plugin;
        startup();
        dataConfig = dataManager.getConfig();
    }
    
    /**
     * Used for plugin startup
     */
    public void startup() {
        plugin.saveDefaultConfig();
        startBstats();
        KEYS = new Keys(this);
        configStartup();
        initialize();
    }
    
    /**
     * Used for plugin shutdown
     */
    public void shutdown() {
        dataManager.saveConfig();
    }
    
    //Begin startup methods
    private void startBstats() {
        Metrics metrics = new Metrics(plugin, 15632);
    }
    
    private void configStartup() {
        dataManager = new DataManager(this);
        configManager = new ConfigManager(this);
        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            dataManager.saveConfig();
        }, 0, 6000);
    }
    
    private void initialize() {
        new SpawnerListener(this);
        new MainCommand(this);
    }
    
    //Begin encapsulation methods
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public MainClass getPlugin() {
        return plugin;
    }
    
    public Data getDataConfig() {
        return dataConfig;
    }
    
}
