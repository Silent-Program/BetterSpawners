package me.silentprogram.betterspawners.config;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.config.classes.Group;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
    private final BetterSpawners plugin;
    private final ConfigurationSection customGroupsSection;
    private final ConfigurationSection spawnerSettingsSection;
    private Map<String, Group> groupMap;
    int minedMultiplier;
    int xpPerMinute;
    int maxTimePerSpawner;
    
    public ConfigManager(BetterSpawners plugin){
        this.plugin = plugin;
        customGroupsSection = plugin.getConfig().getConfigurationSection("groups.custom-groups");
        spawnerSettingsSection = plugin.getConfig().getConfigurationSection("spawner-settings");
        reloadConfig();
    }
    
    /**
     * Resets groups map, and grabs all groups.
     */
    private void getGroups(){
        groupMap = new HashMap<>();
        for (String i : customGroupsSection.getKeys(false)) {
            try{
                groupMap.put(i, new Group(i, customGroupsSection.getInt(i + ".spawner-amount"), customGroupsSection.getBoolean(i + ".cansilk")));
            }catch(Exception e){
                System.out.println("Error getting group " + i + ".\n" + e);
            }
        }
        //Add the default section to the map.
        ConfigurationSection defaultGroupSection = plugin.getConfig().getConfigurationSection("groups.default-group");
        groupMap.put("default-group", new Group("default-group", defaultGroupSection.getInt("spawner-amount"), defaultGroupSection.getBoolean("cansilk")));
    }
    
    /**
     * Gets the group map.
     * @return Returns a map with all groups.
     */
    public Map<String, Group> getGroupMap() {
        return groupMap;
    }
    
    /**
     * Returns a players group based on their permissions.
     * @param plr The targetted player to get the group.
     * @return Returns the group the player has permissions for.
     */
    private Group getPlayerGroup(Player plr){
        String group = "default-group";
        for (String i : groupMap.keySet()) {
            if (plr.hasPermission("betterspawners.group." + i)) {
                group = i;
                break;
            }
        }
        return groupMap.get(group);
    }
    
    /**
     * Checks if the targeted player has permission to use silk touch on spawners.
     * @param plr The target player.
     * @return Returns true if player can silk and false if not.
     */
    public boolean canPlayerSilk(Player plr){
        return getPlayerGroup(plr).getCanSilk();
    }
    
    /**
     * Checks the amount of spawners a targeted player is allowed to have at once.
     * @param plr Targeted player
     * @return Returns the players max spawner count.
     */
    public int getSpawnerAmount(Player plr){
        return getPlayerGroup(plr).getSpawnerAmount();
    }
    
    /**
     * Gets new values for all saved config entries.
     */
    private void reloadConfig(){
        getGroups();
        this.minedMultiplier = spawnerSettingsSection.getInt("mined-multiplier");
        this.xpPerMinute = spawnerSettingsSection.getInt("xp-per-minute");
        this.maxTimePerSpawner = spawnerSettingsSection.getInt("max-time-per-spawner");
    }
}
