package me.silentprogram.betterspawnerspremium.loot;

import me.silentprogram.betterspawners.util.SpawnerType;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.*;

public class SpawnerLoot {
    private final Map<ItemStack, Double> lootChanceMap;
    private final SpawnerType spawnerType;
    private final Random random;
    private final DecimalFormat df;
    
    public SpawnerLoot(SpawnerType spawnerType, Map<ItemStack, Double> lootChanceMap) {
        this.spawnerType = spawnerType;
        this.lootChanceMap = lootChanceMap;
        this.random = new Random();
        this.df = new DecimalFormat("0.00");
    }
    
    /**
     * Gets the loot to amount map.
     * @param multiplier The multiplier to multiply the amount by
     * @param minutes The amount of minutes to calculate the drops for
     * @return Returns the loot to amount of items map.
     */
    public Map<ItemStack, Integer> getLootAmountMap(int multiplier, int minutes) {
        Map<ItemStack, Integer> itemList = new HashMap<>();
        for(ItemStack i : lootChanceMap.keySet()){
            int itemAmount = (int) Math.round(i.getAmount() * minutes * lootChanceMap.get(i)
                    * multiplier
                    * Integer.parseInt(df.format(0.9 + (0.99 - 0.9) * random.nextDouble())));
            ItemStack itemClone = i.clone();
            itemClone.setAmount(1);
            itemList.put(itemClone, itemAmount);
        }
        return itemList;
    }
}
