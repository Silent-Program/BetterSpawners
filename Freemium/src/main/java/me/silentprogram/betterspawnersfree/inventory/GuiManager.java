package me.silentprogram.betterspawnersfree.inventory;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.PaginatedPane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.silentprogram.betterspawners.StartupClass;
import me.silentprogram.betterspawners.interfaces.GuiManagerAbstract;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GuiManager extends GuiManagerAbstract {
    StartupClass plugin;
    
    public GuiManager(StartupClass plugin) {
        this.plugin = plugin;
    }
    
    private ChestGui createGui(Player guiPlr){
        ChestGui gui = new ChestGui(6, "Spawners");
        //Create a pages pane to add to the gui
        PaginatedPane pages = new PaginatedPane(0, 0, 9, 5);
        //Create a list of the players spawners in config
        //Add these items to the pages
        //Set click functionality
        pages.setOnClick(event -> {
            event.setCancelled(true);
            if (event.getAction() != InventoryAction.PICKUP_ALL) return;
            Player plr = (Player) event.getWhoClicked();
            ItemStack itemStack = event.getCurrentItem();
        
            if (plr.getInventory().firstEmpty() == -1) {
                plr.sendMessage("Your inventory must be empty!");
                return;
            }
            List<ItemStack> itemList = new ArrayList<>();
            
            pages.clear();
            pages.populateWithItemStacks(itemList);
            plr.getInventory().addItem(itemStack);
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
            if (!itemData.has(plugin.KEYS.ENTITY_TYPE_KEY, PersistentDataType.STRING)) return;
            //Permissions check for multiple spawners in gui
            int itemAmount = pages.getItems().size() + 1;
        
            List<ItemStack> itemList = new ArrayList<>();
            
            ItemStack itemClone = itemStack.clone();
            itemClone.setAmount(1);
            itemList.add(itemClone);
            for(ItemStack i : itemList){
                ItemMeta itemMeta2 = i.getItemMeta();
                PersistentDataContainer itemData2 = itemMeta2.getPersistentDataContainer();
                itemData2.set(plugin.KEYS.LAST_GEN_KEY, PersistentDataType.LONG, System.currentTimeMillis());
                i.setItemMeta(itemMeta2);
            }
            itemStack.setAmount(itemStack.getAmount() - 1);
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
        itemMeta.setDisplayName("Click to collect  + xp!");
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
    
        GuiItem xpItem = new GuiItem(itemStack);
    
        xpItem.setAction(event -> {
            event.setCancelled(true);
        
            Player plr = (Player) event.getWhoClicked();
        
        
            plr.playSound(plr.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
            ItemStack item = xpItem.getItem();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Click to collect 0 xp!");
            item.setItemMeta(meta);
        
            gui.update();
        });
    
        navigation.addItem(xpItem, 4, 0);
    
        gui.addPane(navigation);
        //End gui creation
        return gui;
    }
    
    @Override
    public void showGui(Player target, Player viewer) {
    
    }
    
    @Override
    public void showGui(Player plr) {
        plr.sendMessage("Hi");
    }
}
