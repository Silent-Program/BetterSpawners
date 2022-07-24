package me.silentprogram.betterspawners.config;

import com.google.gson.Gson;
import me.silentprogram.betterspawners.StartupClass;
import me.silentprogram.betterspawners.config.classes.Data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    private final File configFile;
    private final Gson gson = new Gson();
    private Data config;
    
    /**
     * @param plugin Main plugin instance.
     */
    public DataManager(StartupClass plugin) {
        configFile = new File(plugin.getPlugin().getDataFolder(), "data.json");
        initializeConfig();
    }
    
    /**
     * Should only be used by this class to grab/create the config
     */
    private void initializeConfig() {
        try {
            if (!configFile.exists()) {
                FileWriter fileWriter = new FileWriter(configFile);
                gson.toJson(new Data(), fileWriter);
                fileWriter.close();
            }
            
            config = gson.fromJson(new FileReader(configFile), Data.class);
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
            FileWriter fileWriter = new FileWriter(configFile);
            gson.toJson(config, fileWriter);
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
