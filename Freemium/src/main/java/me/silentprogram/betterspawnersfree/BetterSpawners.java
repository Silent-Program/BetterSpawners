package me.silentprogram.betterspawnersfree;

import me.silentprogram.betterspawners.MainClass;
import me.silentprogram.betterspawners.StartupClass;
import me.silentprogram.betterspawnersfree.inventory.GuiManager;

public class BetterSpawners extends MainClass {
    private StartupClass startupClass;
    private GuiManager guiManager;
    
    @Override
    public void onEnable() {
        startupClass = new StartupClass(this);
        this.guiManager = new GuiManager(startupClass);
    }
    
    @Override
    public void onDisable() {
        startupClass.shutdown();
    }
    
    public GuiManager getGuiManager() {
        return guiManager;
    }
}
