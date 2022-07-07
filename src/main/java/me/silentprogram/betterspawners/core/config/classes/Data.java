package me.silentprogram.betterspawners.core.config.classes;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Data {
	Map<UUID, List<ItemClass>> playerListMap = new HashMap<>();
	
	public Map<UUID, List<ItemClass>> getPlayerListMap() {
		return playerListMap;
	}
	//Remove all irrelevant code.  Redo config saving form ground up.
	public static String serialize(ItemStack[] obj) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		     BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
			
			
			dataOutput.writeInt(obj.length);
			
			for (int i = 0; i < obj.length; i++) {
				dataOutput.writeObject(obj[i]);
			}
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Unable to save item stacks.", e);
		}
	}
	
	public static ItemStack[] deserialize(String str) {
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(str));
		     BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
			ItemStack[] items = new ItemStack[dataInput.readInt()];
			
			for (int i = 0; i < items.length; i++) {
				items[i] = (ItemStack) dataInput.readObject();
			}
			
			return items;
		} catch (Exception e) {
			return new ItemStack[0];
		}
	}
	
	public void setPlayerList(UUID uuid, List<ItemClass> items) {
		playerListMap.remove(uuid);
		playerListMap.put(uuid, items);
	}
}
