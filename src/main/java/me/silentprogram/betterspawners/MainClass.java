package me.silentprogram.betterspawners;

import me.silentprogram.betterspawners.interfaces.GuiManagerAbstract;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MainClass extends JavaPlugin {
    /**
     * Gives you the gui manager for the premium or free version.
     * @return Returns the gui manager.
     */
    public abstract GuiManagerAbstract getGuiManager();
}
