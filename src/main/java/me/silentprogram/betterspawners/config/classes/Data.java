package me.silentprogram.betterspawners.config.classes;

import me.silentprogram.betterspawners.util.SerializationUtils;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Data {
    private final Map<UUID, String> playerListMap = new HashMap<>();
    
    /**
     * This method is used to get a clone of a players items from the config.  If no player exists, it returns an empty array.
     * @param playerUUID The target players UUID.
     * @return Returns clone of players items.
     */
    public List<ItemStack> getPlayerItems(UUID playerUUID) {
        if (!playerListMap.containsKey(playerUUID)) {
            return new ArrayList<>();
        }
        return List.of(SerializationUtils.deserialize(playerListMap.get(playerUUID)).clone());
    }
    
    /**
     * This method simply puts the players items into the hashmap saved into the config
     * @param playerUUID The target player.
     * @param items The target playuers items.
     */
    public void putPlayerItems(UUID playerUUID, List<ItemStack> items) {
        playerListMap.put(playerUUID, SerializationUtils.serialize(items.toArray(new ItemStack[0])));
    }
    
}
