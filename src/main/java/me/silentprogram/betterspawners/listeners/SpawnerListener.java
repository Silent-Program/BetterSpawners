package me.silentprogram.betterspawners.listeners;

import me.silentprogram.betterspawners.StartupClass;
import me.silentprogram.betterspawners.config.ConfigManager;
import me.silentprogram.betterspawners.util.SpawnerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataType;

public class SpawnerListener implements Listener {
    private final StartupClass plugin;
    ConfigManager configManager;
    
    public SpawnerListener(StartupClass plugin) {
        this.plugin = plugin;
        plugin.getPlugin().getServer().getPluginManager().registerEvents(this, plugin.getPlugin());
        this.configManager = plugin.getConfigManager();
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player plr = event.getPlayer();
        
        if (block.getType() != Material.SPAWNER) return;
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        
        if (!plr.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if(!configManager.canPlayerSilk(plr)) return;
        
        block.getWorld().dropItemNaturally(block.getLocation(),
                SpawnerManager.createSpawner(plugin, spawner.getSpawnedType(), plugin.getPlugin().getConfig().getInt("mined-multiplier"), System.currentTimeMillis(), plr.getName()));
        
        event.setExpToDrop(0);
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() != Material.SPAWNER) return;
        if (event.getItemInHand().getItemMeta() == null) return;
        if (!event.getItemInHand().getItemMeta().getPersistentDataContainer().has(plugin.KEYS.ENTITY_TYPE_KEY, PersistentDataType.STRING))
            return;
        
        event.setCancelled(true);
    }
}
