package me.silentprogram.betterspawners.util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializationUtils {
    /**
     * This method serializes a itemstack array into a string to be stored
     * @param itemList The array to serialize.
     * @return Returns a serialized string from the passed array.
     */
    public static String serialize(List<ItemStack> itemList) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream)) {
            ItemStack[] itemArray = new ItemStack[itemList.size()];
            itemList.toArray(itemArray);
            
            dataOutput.writeInt(itemArray.length);
    
            for (ItemStack itemStack : itemArray) {
                dataOutput.writeObject(itemStack);
            }
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }
    
    /**
     * This method deserializes a previously serialized ItemStack array.
     * @param str The serialized string to deserialize.
     * @return Returns a deserialized ItemStack array.
     */
    public static List<ItemStack> deserialize(String str) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(str)); BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream)) {
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            
            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) dataInput.readObject();
            }
            return Arrays.asList(items);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
