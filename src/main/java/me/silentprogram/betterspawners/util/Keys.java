package me.silentprogram.betterspawners.util;

import me.silentprogram.betterspawners.StartupClass;
import org.bukkit.NamespacedKey;

public class Keys {
    private StartupClass plugin;
    
    public Keys(StartupClass plugin) {
        this.plugin = plugin;
    }
    
    public final NamespacedKey ENTITY_TYPE_KEY = new NamespacedKey(plugin.getPlugin(), "bs.entityType");
    public final NamespacedKey MULTIPLIER_KEY = new NamespacedKey(plugin.getPlugin(), "bs.multiplier");
    public final NamespacedKey MINED_KEY = new NamespacedKey(plugin.getPlugin(), "bs.wasMined");
    public final NamespacedKey OWNER_KEY = new NamespacedKey(plugin.getPlugin(), "bs.ownerName");
    public final NamespacedKey LAST_GEN_KEY = new NamespacedKey(plugin.getPlugin(), "bs.lastGen");
}
