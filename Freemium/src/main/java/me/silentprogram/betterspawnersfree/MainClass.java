package me.silentprogram.betterspawnersfree;

import me.silentprogram.betterspawners.BetterSpawners;
import org.bukkit.plugin.java.JavaPlugin;

public class MainClass extends JavaPlugin {
    @Override
    public void onEnable() {
        BetterSpawners mainClass = new BetterSpawners(this);
    }
}
