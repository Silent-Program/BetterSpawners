package me.silentprogram.betterspawners.util;

import me.silentprogram.betterspawners.BetterSpawners;
import org.bukkit.NamespacedKey;

public class Keys {
    private BetterSpawners plugin;
    
    public Keys(BetterSpawners plugin) {
        this.plugin = plugin;
    }
    public final NamespacedKey ENTITY_TYPE_KEY = new NamespacedKey(plugin, "bs.entityType");
    public final NamespacedKey MULTIPLIER_KEY = new NamespacedKey(plugin, "bs.multiplier");
    public final NamespacedKey MINED_KEY = new NamespacedKey(plugin, "bs.wasMined");
    public final NamespacedKey OWNER_KEY = new NamespacedKey(plugin, "bs.ownerName");
    public final NamespacedKey XP_KEY = new NamespacedKey(plugin, "bs.xpAmount");
    public final NamespacedKey LAST_GEN_KEY = new NamespacedKey(plugin, "bs.lastGen");
}
