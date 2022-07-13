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
    private FileWriter fileWriter;
    private final Gson gson = new Gson();
    private Data config;
    
    /**
     * @param plugin Main plugin instance.
     */
    public DataManager(StartupClass plugin) {
        configFile = new File(plugin.getPlugin().getDataFolder(), "data.json");
        System.out.println("1");
        try {
            fileWriter = new FileWriter(configFile);
            System.out.println("2");
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("err");
        }
        initializeConfig();
    }
    
    /**
     * Should only be used by this class to grab/create the config
     */
    private void initializeConfig() {
        
        try {
            if (!configFile.exists()) {
                gson.toJson(new Data(), fileWriter);
                System.out.println("4");
            }
            config = gson.fromJson(new FileReader(configFile), Data.class);
            System.out.println("5");
        } catch (IOException e) {
            System.out.println("6");
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
        gson.toJson(config, fileWriter);
        System.out.println("7");
    }
}
