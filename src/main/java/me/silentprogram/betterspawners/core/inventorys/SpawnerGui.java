package me.silentprogram.betterspawners.core.inventorys;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.api.SpawnerFactory;
import me.silentprogram.betterspawners.core.config.classes.Data;
import me.silentprogram.betterspawners.core.config.classes.ItemClass;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnerGui {
	final int maxTime;
	final int xpAmount;
	BetterSpawners plugin;
	Data dataConfig;
	FileConfiguration config;
	Map<Player, ChestGui> playerGuiMap = new HashMap<>();
	
	public SpawnerGui(BetterSpawners plugin) {
		this.plugin = plugin;
		this.dataConfig = plugin.getDataConfig();
		this.config = plugin.getConfig();
		this.maxTime = (int) (config.getInt("max-time") * 60L);
		this.xpAmount = config.getInt("xp-amount");
	}
	
	public ChestGui createGui(Player guiPlr) {
		//Create gui to be edited
		ChestGui gui = new ChestGui(6, "Spawners");
		//Creating the amount of XP to be used later
		int xp = 0;
		
		if (!dataConfig.getPlayerListMap().containsKey(guiPlr.getUniqueId())) {
			dataConfig.getPlayerListMap().put(guiPlr.getUniqueId(), new ArrayList<>());
		}
		xp = calculateXp(guiPlr);
		//Create a pages pane to add to the gui
		PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);
		//Create a list of the players spawners in config
		List<ItemStack> inventoryItemList = new ArrayList<>();
		if (dataConfig.getPlayerListMap().containsKey(guiPlr.getUniqueId()))
			inventoryItemList = getItemList(guiPlr);
		//Add these items to the pages
		pages.populateWithItemStacks(inventoryItemList);
		//Set click functionality
		pages.setOnClick(event -> {
			event.setCancelled(true);
			if (event.getAction() != InventoryAction.PICKUP_ALL) return;
			Player plr = (Player) event.getWhoClicked();
			ItemStack itemStack = event.getCurrentItem();
			if (checkInventoryForItem(plr.getInventory(), itemStack)) return;
			if (plr.getInventory().firstEmpty() == -1) return;
			List<ItemStack> itemList = new ArrayList<>();
			if (dataConfig.getPlayerListMap().containsKey(guiPlr.getUniqueId()))
				itemList = getItemList(guiPlr);
			
			itemList.remove(resetItem(itemStack));
			dataConfig.setPlayerList(guiPlr.getUniqueId(), getItemClassList(itemList));
			plr.getInventory().addItem(itemStack);
			pages.clear();
			pages.populateWithItemStacks(itemList);
			gui.update();
		});
		//Add pages to the gui
		gui.addPane(pages);
		
		//Set actions for when the player clicks on his/her own inventory
		gui.setOnBottomClick(event -> {
			event.setCancelled(true);
			if (event.getAction() != InventoryAction.PICKUP_ALL) return;
			if (event.getCurrentItem() == null || event.getCurrentItem().getType() != Material.SPAWNER) return;
			ItemStack itemStack = event.getCurrentItem();
			if (!itemStack.hasItemMeta() || (!itemStack.hasItemMeta())) return;
			PersistentDataContainer itemData = itemStack.getItemMeta().getPersistentDataContainer();
			if (!itemData.has(plugin.ENTITY_TYPE_KEY, PersistentDataType.STRING)) return;
			//Player plr = (Player) event.getWhoClicked();
			//Permissions check for multiple spawners in gui
			String group = "gui.groups.default-group";
			for (String i : config.getConfigurationSection("gui.groups.custom-groups").getKeys(false)) {
				if (guiPlr.hasPermission("betterspawners.group." + i)) {
					group = "gui.groups.custom-groups." + i;
					break;
				}
			}
			int itemAmount = pages.getItems().size() + 1;
			
			if (itemAmount > config.getInt(group + ".spawner-amount")) return;
			List<ItemStack> itemList = new ArrayList<>();
			if (dataConfig.getPlayerListMap().containsKey(guiPlr.getUniqueId())) {
				itemList = getItemList(guiPlr);
			}
			ItemStack itemClone = itemStack.clone();
			itemClone.setAmount(1);
			itemList.add(itemClone);
			itemStack.setAmount(itemStack.getAmount() - 1);
			dataConfig.setPlayerList(guiPlr.getUniqueId(), getItemClassList(itemList));
			pages.populateWithItemStacks(itemList);
			gui.update();
		});
		//Create a new pane for the background to fill it with black stained glass
		OutlinePane background = new OutlinePane(0, 5, 9, 1);
		background.addItem(new GuiItem(new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
		background.setRepeat(true);
		background.setPriority(Pane.Priority.LOWEST);
		background.setOnClick(event -> event.setCancelled(true));
		
		gui.addPane(background);
		
		//Create a new pane for the navigation items
		StaticPane navigation = new StaticPane(0, 5, 9, 1);
		//Go back a page item
		ItemStack backItem = new ItemStack(Material.RED_WOOL);
		ItemMeta backItemMeta = backItem.getItemMeta();
		backItemMeta.setDisplayName("Last Page");
		backItem.setItemMeta(backItemMeta);
		navigation.addItem(new GuiItem(backItem, event -> {
			event.setCancelled(true);
			if (pages.getPage() > 0) {
				pages.setPage(pages.getPage() - 1);
				
				gui.update();
			}
		}), 0, 0);
		
		//Fix this later
		
		ItemStack itemStack = new ItemStack(Material.EXPERIENCE_BOTTLE);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName("Click to collect " + xp + " xp!");
		itemStack.setItemMeta(itemMeta);
		//Go forward item
		ItemStack nextItem = new ItemStack(Material.GREEN_WOOL);
		ItemMeta nextItemMeta = nextItem.getItemMeta();
		nextItemMeta.setDisplayName("Next Page");
		nextItem.setItemMeta(nextItemMeta);
		navigation.addItem(new GuiItem(nextItem, event -> {
			event.setCancelled(true);
			if (pages.getPage() < pages.getPages() - 1) {
				pages.setPage(pages.getPage() + 1);
				
				gui.update();
			}
		}), 8, 0);
		
		int finalXp = xp;
		GuiItem xpItem = new GuiItem(itemStack);
		
		xpItem.setAction(event -> {
			event.setCancelled(true);
			
			Player plr = (Player) event.getWhoClicked();
			plr.giveExp(finalXp);
			for (ItemClass i : dataConfig.getPlayerListMap().get(guiPlr.getUniqueId())) {
				i.setLastGen(System.currentTimeMillis());
			}
			plr.playSound(plr.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
			ItemStack item = xpItem.getItem();
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("Click to collect " + 0 + " xp!");
			item.setItemMeta(meta);
			gui.update();
		});
		
		navigation.addItem(xpItem, 4, 0);
		
		gui.addPane(navigation);
		//End gui creation
		return gui;
	}
	
	private List<ItemStack> getItemList(Player plr) {
		List<ItemStack> items = new ArrayList<>();
		for (ItemClass i : dataConfig.getPlayerListMap().get(plr.getUniqueId())) {
			ItemStack item = new SpawnerFactory(plugin,
					EntityType.valueOf(i.getEntityType()),
					i.getMultiplier(),
					i.getCreatorName(),
					i.getXp(),
					i.getLastGen()).getSpawner();
			
			items.add(item);
		}
		return items;
	}
	
	private ItemStack resetItem(ItemStack item) {
		PersistentDataContainer itemData = item.getItemMeta().getPersistentDataContainer();
		ItemStack items = new SpawnerFactory(plugin,
				EntityType.valueOf(itemData.get(plugin.ENTITY_TYPE_KEY, PersistentDataType.STRING)),
				itemData.get(plugin.MULTIPLIER_KEY, PersistentDataType.INTEGER)
				, itemData.get(plugin.OWNER_KEY, PersistentDataType.STRING),
				itemData.get(plugin.XP_KEY, PersistentDataType.INTEGER),
				itemData.get(plugin.LAST_GEN_KEY, PersistentDataType.LONG)
		).getSpawner();
		return items;
	}
	
	private List<ItemClass> getItemClassList(List<ItemStack> items) {
		List<ItemClass> itemClassList = new ArrayList<>();
		
		for (ItemStack i : items) {
			PersistentDataContainer itemData = i.getItemMeta().getPersistentDataContainer();
			itemClassList.add(new ItemClass()
					.setCreatorName(itemData.get(plugin.OWNER_KEY, PersistentDataType.STRING))
					.setEntityType(itemData.get(plugin.ENTITY_TYPE_KEY, PersistentDataType.STRING))
					.setMined(itemData.get(plugin.MINED_KEY, PersistentDataType.BYTE) == 1 ? true : false)
					.setMultiplier(itemData.get(plugin.MULTIPLIER_KEY, PersistentDataType.INTEGER))
					.setXp(itemData.get(plugin.XP_KEY, PersistentDataType.INTEGER))
					.setLastGen(itemData.get(plugin.LAST_GEN_KEY, PersistentDataType.LONG)));
		}
		return itemClassList;
	}

	private boolean checkInventoryForItem(Inventory playerInv, ItemStack itemStack){
		for (ItemStack item : playerInv.getContents()){
			if (item == null) continue;
			ItemMeta meta = itemStack.getItemMeta();
			if (meta == null) continue;
			
		}
		return false;
	}
	
	public int calculateXp(Player plr){
		int xp = 0;
		for (ItemClass i : dataConfig.getPlayerListMap().get(plr.getUniqueId())) {
			long time = System.currentTimeMillis() - i.getLastGen();
			time /= 60000;
			if (time > maxTime) time = maxTime;
			xp = xp + (int) ((time * xpAmount * i.getMultiplier()));
		}
		return xp;
	}
	
	public Map<Player, ChestGui> getPlayerGuiMap() {
		return playerGuiMap;
	}
}
