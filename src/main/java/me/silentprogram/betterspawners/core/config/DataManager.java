package me.silentprogram.betterspawners.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.core.config.classes.Data;

import java.io.File;
import java.io.IOException;

public class DataManager {
	Data config;
	BetterSpawners plugin;
	File configFile;
	ObjectMapper om = new ObjectMapper();
	
	public DataManager(BetterSpawners plugin){
		this.plugin = plugin;
		configFile = new File(plugin.getDataFolder(), "data.json");
	}
	
	public Data initializeConfig() {
		try {
			if (!configFile.exists()) {
				om.writeValue(configFile, new Data());
			}
			config = om.readValue(configFile, Data.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public void saveConfig(){
		try{
			om.writeValue(configFile, config);
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public Data getConfig() {
		return config;
	}
}
