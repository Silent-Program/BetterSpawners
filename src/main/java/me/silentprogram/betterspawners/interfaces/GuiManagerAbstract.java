package me.silentprogram.betterspawners.interfaces;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import me.silentprogram.betterspawners.StartupClass;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class GuiManagerAbstract {
    Map<ChestGui, List<UUID>> guiPlayerMap;
    
    /**
     * Creates and shows a gui made from the target to the viewer.
     * @param target Target player for the gui to create itself off of.
     * @param viewer Target to show the gui to.
     */
    public abstract void showGui(Player target, Player viewer);
    /**
     * Creates and shows a gui made from the target to the viewer.
     * @param targetViewer Target player to create gui from, and show gui to.
     */
    public abstract void showGui(Player targetViewer);
}
