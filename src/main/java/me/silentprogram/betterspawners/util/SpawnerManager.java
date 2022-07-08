package me.silentprogram.betterspawners.util;

import me.silentprogram.betterspawners.BetterSpawners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class exists only to have static methods regarding custom spawner management.
 */
public class SpawnerManager {
    private static ItemStack createSpawner(BetterSpawners plugin, EntityType spawnerType, int multiplier, int xp, long lastGen, boolean isMined, String creatorName){
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        List<String> lore = new ArrayList<>();
    
        ItemMeta spawnerMeta = spawner.getItemMeta();
        if(spawnerMeta == null) return null;
        PersistentDataContainer spawnerData = spawnerMeta.getPersistentDataContainer();
        
        String loreMessage;
        if (isMined) {
            loreMessage = ChatColor.AQUA + "Mined by " + creatorName;
            spawnerData.set(plugin.KEYS.MINED_KEY, PersistentDataType.BYTE, (byte) 1);
            spawnerData.set(plugin.KEYS.OWNER_KEY, PersistentDataType.STRING, creatorName);
        } else {
            loreMessage = ChatColor.AQUA + "Created via command";
            spawnerData.set(plugin.KEYS.MINED_KEY, PersistentDataType.BYTE, (byte) 0);
            spawnerData.set(plugin.KEYS.OWNER_KEY, PersistentDataType.STRING, "console");
        }
        
        String str = spawnerType.name().toLowerCase();
    
        String output = str.substring(0, 1).toUpperCase() + str.substring(1);
        spawnerMeta.setDisplayName(output + " spawner");
    
        lore.add("");
        lore.add(loreMessage);
        lore.add("Multiplier: " + multiplier);
        lore.add("\nSpawns" + output + "s.");
    
    
        spawnerData.set(plugin.KEYS.ENTITY_TYPE_KEY, PersistentDataType.STRING, spawnerType.name());
        spawnerData.set(plugin.KEYS.MULTIPLIER_KEY, PersistentDataType.INTEGER, multiplier);
        spawnerData.set(plugin.KEYS.XP_KEY, PersistentDataType.INTEGER, xp);
        spawnerData.set(plugin.KEYS.LAST_GEN_KEY, PersistentDataType.LONG, lastGen);
        
        spawnerMeta.setLore(lore);
        spawner.setItemMeta(spawnerMeta);
        
        return spawner;
    }
    
    public static ItemStack createSpawner(BetterSpawners plugin, EntityType spawnerType, int multiplier, int xp, long lastGen){
        return createSpawner(plugin, spawnerType, multiplier, xp, lastGen, false, "");
    }
    
    public static ItemStack createSpawner(BetterSpawners plugin, EntityType spawnerType, int multiplier, int xp, long lastGen, String creatorName){
        return createSpawner(plugin, spawnerType, multiplier, xp, lastGen, true, creatorName);
    }
}
