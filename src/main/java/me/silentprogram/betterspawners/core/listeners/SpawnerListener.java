package me.silentprogram.betterspawners.core.listeners;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.api.SpawnerFactory;
import me.silentprogram.betterspawners.core.config.classes.ItemClass;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
public class SpawnerListener implements Listener {
	BetterSpawners plugin;
	BukkitTask runnable;
	
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
		
		block.getWorld().dropItemNaturally(block.getLocation(),
				new SpawnerFactory(plugin, spawner.getSpawnedType(), 2, plr.getName(), 0, System.currentTimeMillis()).getSpawner());
		
		event.setExpToDrop(0);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if (event.getItemInHand().getType() != Material.SPAWNER) return;
		if(event.getItemInHand().getItemMeta() == null) return;
		if(!event.getItemInHand().getItemMeta().getPersistentDataContainer().has(plugin.ENTITY_TYPE_KEY, PersistentDataType.STRING)) return;
		
		event.setCancelled(true);
	}

	@EventHandler
	public void onLeaveEvent(PlayerQuitEvent e){
		plugin.gui.getPlayerGuiMap().remove(e.getPlayer());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		if (!plugin.gui.getPlayerGuiMap().containsKey(e.getPlayer())) return;
		if (!checkInventoryForItem(e.getInventory())) return;
		runnable.cancel();
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e){
		if (!plugin.gui.getPlayerGuiMap().containsKey(e.getPlayer())) return;
		if (!checkInventoryForItem(e.getInventory())) return;
		runnable = new BukkitRunnable() {

			@Override
			public void run() {
				ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
				ItemMeta meta = item.getItemMeta();
				int finalXp = plugin.gui.calculateXp((Player) e.getPlayer());
				meta.setDisplayName("Click to collect " + plugin.gui.calculateXp((Player) e.getPlayer()) + " xp!");
				item.setItemMeta(meta);
				
				GuiItem iGuiItem = new GuiItem(item, event -> {
					event.setCancelled(true);
					
					Player plr = (Player) event.getWhoClicked();
					plr.giveExp(finalXp);
					for (ItemClass i : plugin.getDataConfig().getPlayerListMap().get(e.getPlayer().getUniqueId())) {
						i.setLastGen(System.currentTimeMillis());
					}
					plr.playSound(plr.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
					ItemStack item1 = new ItemStack(Material.EXPERIENCE_BOTTLE);
					ItemMeta meta1 = item1.getItemMeta();
					meta1.setDisplayName("Click to collect " + 0 + " xp!");
					item1.setItemMeta(meta1);
					plugin.gui.getPlayerGuiMap().get(e.getPlayer()).update();
				});
				List<com.github.stefvanschie.inventoryframework.pane.Pane> panes = plugin.gui.getPlayerGuiMap().get(e.getPlayer()).getPanes();
				((StaticPane) plugin.gui.getPlayerGuiMap().get(e.getPlayer()).getPanes().get(findObjectOfType(panes))).addItem(iGuiItem, 4, 0);
				plugin.gui.getPlayerGuiMap().get(e.getPlayer()).update();
				Bukkit.getLogger().info("Runnable is running with " + plugin.gui.calculateXp((Player) e.getPlayer()));
			}
			
		}.runTaskTimer(plugin, 100L, 5000L);

		
		


	}

	private boolean checkInventoryForItem(Inventory playerInv){
		for (ItemStack item : playerInv.getContents()){
			if (item == null) continue;
			if (item.getItemMeta() == null) continue;
			if (item.getItemMeta().getDisplayName().contains("Click to collect")) return true;
		}
		return false;
	}

	private <Pane> int findObjectOfType(List<Pane> panes){
		for (int i = 0; i < panes.size()-1; i++){
			if (panes.get(i) instanceof StaticPane){
				return i;
			}
		}
		return -1;
	}
}
