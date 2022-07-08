package me.silentprogram.betterspawners.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.config.classes.Data;

import java.io.File;
import java.io.IOException;

public class DataManager {
    private final File configFile;
    private final ObjectMapper om = new ObjectMapper();
    private Data config;
    
    /**
     * @param plugin Main plugin instance.
     */
    public DataManager(BetterSpawners plugin) {
        configFile = new File(plugin.getDataFolder(), "data.json");
        initializeConfig();
    }
    
    /**
     * Should only be used by this class to grab/create the config
     */
    private void initializeConfig() {
        try {
            if (!configFile.exists()) {
                om.writeValue(configFile, new Data());
            }
            config = om.readValue(configFile, Data.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the Data object the config is held in.
     * @return Returns the config object.
     */
    public Data getConfig() {
        return config;
    }
    
    /**
     * Saves the config.
     */
    public void saveConfig() {
        try {
            om.writeValue(configFile, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
