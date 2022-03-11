package me.silentprogram.betterspawners.core.tasks;

import java.util.List;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.core.config.classes.ItemClass;
import me.silentprogram.betterspawners.core.listeners.SpawnerListener;

import com.github.stefvanschie.inventoryframework.pane.StaticPane;

public class XPTask extends BukkitRunnable {
    Player player;
    BetterSpawners plugin;

    public XPTask(Player player, BetterSpawners plugin) {
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta meta = item.getItemMeta();
        int finalXp = plugin.gui.calculateXp(player);
        meta.setDisplayName("Click to collect " + plugin.gui.calculateXp(player) + " xp!");
        item.setItemMeta(meta);

        GuiItem iGuiItem = new GuiItem(item, event -> {
            event.setCancelled(true);

            player.giveExp(finalXp);
            for (ItemClass i : plugin.getDataConfig().getPlayerListMap().get(player.getUniqueId())) {
                i.setLastGen(System.currentTimeMillis());
            }
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
            meta.setDisplayName("Click to collect " + 0 + " xp!");
            item.setItemMeta(meta);
            plugin.gui.getPlayerGuiMap().get(player).update();
        });
        List<com.github.stefvanschie.inventoryframework.pane.Pane> panes = plugin.gui.getPlayerGuiMap().get(player)
                .getPanes();
        ((StaticPane) plugin.gui.getPlayerGuiMap().get(player).getPanes().get(SpawnerListener.findObjectOfType(panes)))
                .addItem(iGuiItem, 4, 0);
        plugin.gui.getPlayerGuiMap().get(player).update();
    }

}
