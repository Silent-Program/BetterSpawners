package me.silentprogram.betterspawners.listeners;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.util.Keys;
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
    BetterSpawners plugin;
    
    public SpawnerListener(BetterSpawners plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player plr = event.getPlayer();
        
        if (block.getType() != Material.SPAWNER) return;
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        
        if (!plr.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        String group = "gui.groups.default-group";
        for (String i : plugin.getConfig().getConfigurationSection("gui.groups.custom-groups").getKeys(false)) {
            if (plr.hasPermission("betterspawners.group." + i)) {
                group = "gui.groups.custom-groups." + i;
                break;
            }
        }
        if (!plugin.getConfig().getBoolean(group + ".cansilk")) return;
        
        //block.getWorld().dropItemNaturally(block.getLocation(),
        //new SpawnerFactory(plugin, spawner.getSpawnedType(), plugin.getConfig().getInt("mined-multiplier"), plr.getName(), 0, System.currentTimeMillis()).getSpawner());
        
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
