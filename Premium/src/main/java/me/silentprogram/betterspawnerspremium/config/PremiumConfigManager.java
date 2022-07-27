package me.silentprogram.betterspawnerspremium.config;

import me.silentprogram.betterspawnerspremium.BetterSpawners;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class PremiumConfigManager {
    private FileConfiguration config;
    private File configFile;
    private final String fileName;
    private final BetterSpawners plugin;
    
    
    public PremiumConfigManager(BetterSpawners plugin) {
        this.plugin = plugin;
        this.fileName = "premium_config.yml";
        configFile = new File(plugin.getDataFolder(), fileName);
        saveDefaultConfig();
    }
    
    /**
     * Saves the config, and creates it if it does not exist
     */
    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            plugin.saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    /**
     * Saves config
     */
    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }
    
    /**
     * Reloads all data in config back from file
     */
    public void reloadConfig() {
        if (!configFile.exists() || configFile == null) configFile = new File(plugin.getDataFolder(), fileName);
        config = YamlConfiguration.loadConfiguration(configFile);
    }
    
    /**
     * Returns the config files object.
     * @return Returns the config file
     */
    public FileConfiguration getConfig() {
        return config;
    }
}
