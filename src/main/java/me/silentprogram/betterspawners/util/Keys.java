package me.silentprogram.betterspawners.util;

import me.silentprogram.betterspawners.MainClass;
import me.silentprogram.betterspawners.StartupClass;
import org.bukkit.NamespacedKey;

public class Keys {
    
    public final NamespacedKey ENTITY_TYPE_KEY;
    public final NamespacedKey MULTIPLIER_KEY;
    public final NamespacedKey MINED_KEY;
    public final NamespacedKey OWNER_KEY;
    public final NamespacedKey LAST_GEN_KEY;
    
    public Keys(StartupClass pluginClass) {
        MainClass plugin = pluginClass.getPlugin();
        
        ENTITY_TYPE_KEY = new NamespacedKey(plugin, "bs.entityType");
        MULTIPLIER_KEY = new NamespacedKey(plugin, "bs.multiplier");
        MINED_KEY = new NamespacedKey(plugin, "bs.wasMined");
        OWNER_KEY = new NamespacedKey(plugin, "bs.ownerName");
        LAST_GEN_KEY = new NamespacedKey(plugin, "bs.lastGen");
    }
    
    
    
    
    
    
}
