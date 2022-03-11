package me.silentprogram.betterspawners.core.listeners;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.api.SpawnerFactory;
import me.silentprogram.betterspawners.core.tasks.XPTask;

import org.bukkit.Material;
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
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

import com.github.stefvanschie.inventoryframework.pane.StaticPane;

public class SpawnerListener implements Listener {
	BetterSpawners plugin;
	HashMap<Player, BukkitTask> openInventories;

	public SpawnerListener(BetterSpawners plugin) {
		this.plugin = plugin;
		openInventories = new HashMap<>();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Block block = event.getBlock();
		Player plr = event.getPlayer();

		if (block.getType() != Material.SPAWNER)
			return;
		CreatureSpawner spawner = (CreatureSpawner) block.getState();

		if (!plr.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
			return;
		String group = "gui.groups.default-group";
		for (String i : plugin.getConfig().getConfigurationSection("gui.groups.custom-groups").getKeys(false)) {
			if (plr.hasPermission("betterspawners.group." + i)) {
				group = "gui.groups.custom-groups." + i;
				break;
			}
		}
		if (!plugin.getConfig().getBoolean(group + ".cansilk"))
			return;

		block.getWorld().dropItemNaturally(block.getLocation(),
				new SpawnerFactory(plugin, spawner.getSpawnedType(), 2, plr.getName(), 0, System.currentTimeMillis())
						.getSpawner());

		event.setExpToDrop(0);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.getItemInHand().getType() != Material.SPAWNER)
			return;
		if (event.getItemInHand().getItemMeta() == null)
			return;
		if (!event.getItemInHand().getItemMeta().getPersistentDataContainer().has(plugin.ENTITY_TYPE_KEY,
				PersistentDataType.STRING))
			return;

		event.setCancelled(true);
	}

	@EventHandler
	public void onLeaveEvent(PlayerQuitEvent e) {
		plugin.gui.getPlayerGuiMap().remove(e.getPlayer());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (!(e.getPlayer() instanceof Player))
			return;

		Player p = (Player) e.getPlayer();

		if (!plugin.gui.getPlayerGuiMap().containsKey(p))
			return;
		if (!checkInventoryForItem(e.getInventory()))
			return;
		if (openInventories.containsKey(p)) {
			openInventories.get(p).cancel();
			openInventories.remove(p);
			return;
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (!(e.getPlayer() instanceof Player))
			return;
		Player p = (Player) e.getPlayer();
		if (!plugin.gui.getPlayerGuiMap().containsKey(p))
			return;
		if (!checkInventoryForItem(e.getInventory()))
			return;
		openInventories.put(p, new XPTask(p, plugin).runTaskTimer(plugin, 500, 1000));

	}

	private boolean checkInventoryForItem(Inventory playerInv) {
		for (ItemStack item : playerInv.getContents()) {
			if (item == null)
				continue;
			if (item.getItemMeta() == null)
				continue;
			if (item.getItemMeta().getDisplayName().contains("Click to collect"))
				return true;
		}
		return false;
	}

	public static <Pane> int findObjectOfType(List<Pane> panes) {
		for (int i = 0; i < panes.size() - 1; i++) {
			if (panes.get(i) instanceof StaticPane) {
				return i;
			}
		}
		return -1;
	}
}
