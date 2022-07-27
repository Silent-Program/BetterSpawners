package me.silentprogram.betterspawnerspremium.loot;

import me.silentprogram.betterspawners.util.SpawnerType;
import me.silentprogram.betterspawnerspremium.BetterSpawners;

import java.util.HashMap;
import java.util.Map;

public class SpawnerLootManager {
    private final Map<SpawnerType, SpawnerLoot> spawnerLootMap = new HashMap<>();
    private final BetterSpawners plugin;
    
    
    public SpawnerLootManager(BetterSpawners plugin) {
        this.plugin = plugin;
    }
    
    
}
