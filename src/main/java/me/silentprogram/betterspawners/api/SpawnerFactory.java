package me.silentprogram.betterspawners.api;

import me.silentprogram.betterspawners.BetterSpawners;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpawnerFactory {
	//Spawner Variables
	EntityType spawnerType;
	int multiplier;
	int xp;
	long lastGen;
	String creatorName;
	boolean mined;
	//Actual Spawner
	ItemStack spawner;
	//Other stuff
	BetterSpawners plugin;
	NamespacedKey entityKey;
	NamespacedKey multiplierKey;
	NamespacedKey minedKey;
	NamespacedKey ownerKey;
	NamespacedKey xpKey;
	NamespacedKey lastGenKey;
	
	public SpawnerFactory(BetterSpawners plugin, EntityType spawnerType, int multiplier, String creatorName, int xp, long lastGen) {
		factoryInitialize(plugin, spawnerType, multiplier, xp, lastGen);
		this.creatorName = creatorName;
		this.mined = true;
		updateSpawner();
	}
	//For when spawner is created via commands
	public SpawnerFactory(BetterSpawners plugin, EntityType spawnerType, int multiplier, int xp, long lastGen) {
		factoryInitialize(plugin, spawnerType, multiplier, xp, lastGen);
		this.mined = false;
		updateSpawner();
	}
	
	//Run every time you want a new spawner with updated variables
	public SpawnerFactory updateSpawner() {
		ItemStack item = new ItemStack(Material.SPAWNER);
		List<String> lore = new ArrayList<>();
		
		ItemMeta itemMeta = item.getItemMeta();
		PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();
		
		//Change specific parts of item like NBT or lore based on if the item was mined by a player or not
		String loreMessage;
		if (mined) {
			loreMessage = ChatColor.AQUA + "Mined by " + creatorName;
			itemData.set(minedKey, PersistentDataType.BYTE, (byte) 1);
			itemData.set(ownerKey, PersistentDataType.STRING, creatorName);
		} else {
			loreMessage = ChatColor.AQUA + "Created via command";
			itemData.set(minedKey, PersistentDataType.BYTE, (byte) 0);
			itemData.set(ownerKey, PersistentDataType.STRING, "console");
		}
		
		//Regular meta stuff
		String str = spawnerType.name().toLowerCase();
		
		String output = str.substring(0, 1).toUpperCase() + str.substring(1);
		itemMeta.setDisplayName(output + " spawner");
		
		lore.add("");
		lore.add(loreMessage);
		lore.add("Multiplier: " + multiplier);
		lore.add("\nSpawns" + output + "s.");
		
		//Persistent Data
		itemData.set(entityKey, PersistentDataType.STRING, spawnerType.name());
		itemData.set(multiplierKey, PersistentDataType.INTEGER, multiplier);
		itemData.set(xpKey, PersistentDataType.INTEGER, xp);
		itemData.set(lastGenKey, PersistentDataType.LONG, lastGen);
		
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		this.spawner = item;
		return this;
	}
	
	
	//Getters
	
	public ItemStack getSpawner() {
		return spawner;
	}
	
	public EntityType getSpawnerType() {
		return spawnerType;
	}
	
	public void setSpawnerType(EntityType spawnerType) {
		this.spawnerType = spawnerType;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	
	//Setters
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	public void setMined(boolean mined) {
		this.mined = mined;
	}
	
	
	public void factoryInitialize(BetterSpawners plugin, EntityType spawnerType, int multiplier, int xp, long lastGen){
		this.plugin = plugin;
		this.entityKey = plugin.ENTITY_TYPE_KEY;
		this.multiplier = multiplier;
		this.xp = xp;
		this.lastGen = lastGen;
		this.multiplierKey = plugin.MULTIPLIER_KEY;
		this.spawnerType = spawnerType;
		this.ownerKey = plugin.OWNER_KEY;
		this.minedKey = plugin.MINED_KEY;
		this.xpKey = plugin.XP_KEY;
		this.lastGenKey = plugin.LAST_GEN_KEY;
	}
}
