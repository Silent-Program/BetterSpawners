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

public class BetterSpawners extends JavaPlugin {
    private DataManager dataManager;
    private ConfigManager configManager;
    private Data dataConfig;
    public Keys KEYS;
    
    @Override
    public void onEnable() {
        saveDefaultConfig();
        startBstats();
        KEYS = new Keys(this);
        configStartup();
        initialize();
    }
    
    @Override
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
        Metrics metrics = new Metrics(this, 15632);
        metrics.addCustomChart(new SimplePie("server_ip", () -> getServer().getIp() + ":" + getServer().getPort()));
    }
    
    private void configStartup(){
        dataManager = new DataManager(this);
        configManager = new ConfigManager(this);
        dataConfig = dataManager.getConfig();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            dataManager.saveConfig();
        }, 0, 6000);
    }
    
    private void initialize(){
        new SpawnerListener(this);
        new MainCommand(this);
    }
    
}
