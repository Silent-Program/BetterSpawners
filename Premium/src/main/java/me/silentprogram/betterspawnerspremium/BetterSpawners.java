package me.silentprogram.betterspawnerspremium;

import me.silentprogram.betterspawners.MainClass;
import me.silentprogram.betterspawners.StartupClass;
import me.silentprogram.betterspawnerspremium.config.PremiumConfigManager;
import me.silentprogram.betterspawnerspremium.inventory.GuiManager;
import org.bukkit.configuration.file.FileConfiguration;

public class BetterSpawners extends MainClass {
    private StartupClass startupClass;
    private GuiManager guiManager;
    private PremiumConfigManager premiumConfigManager;
    
    @Override
    public void onEnable() {
        startupClass = new StartupClass(this);
        premiumConfigManager = new PremiumConfigManager(this);
        guiManager = new GuiManager(startupClass);
    }
    
    @Override
    public void onDisable() {
        startupClass.shutdown();
    }
    
    public GuiManager getGuiManager() {
        return guiManager;
    }
}
