package me.silentprogram.betterspawners.interfaces;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class GuiManagerAbstract {
    private final Map<UUID, ChestGui> guiPlayerMap = new HashMap<>();
    
    /**
     * Creates and shows a gui made from the target to the viewer.
     * @param target Target player for the gui to create itself off of.
     * @param viewer Target to show the gui to.
     */
    public abstract void showGui(Player target, Player viewer);
    
    public abstract ChestGui createGui(Player target);
    
    /**
     * Creates and shows a gui made from the target to the viewer.
     * @param targetViewer Target player to create gui from, and show gui to.
     */
    public abstract void showGui(Player targetViewer);
    
    /**
     * Gets a gui from a players UUID, if there already is one it returns the previously used one.  If it does not exist, it creates a new one.
     * @return Returns the map used to keep track of players with the gui open.
     */
    public ChestGui getGuiFromUUID(Player plr) {
        ChestGui gui = guiPlayerMap.get(plr.getUniqueId());
        if(gui == null){
            gui = createGui(plr);
            guiPlayerMap.put(plr.getUniqueId(), gui);
        }
        return gui;
    }
    
    /**
     * Remove the gui from the list of open guis.
     * @param plr The owner of the gui to remove.
     */
    public void removeGuiFromList(Player plr){
        guiPlayerMap.remove(plr.getUniqueId());
    }
    
}
