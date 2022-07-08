package me.silentprogram.betterspawners.config;

import me.silentprogram.betterspawners.BetterSpawners;
import me.silentprogram.betterspawners.config.classes.Group;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GroupsManager {
    private final BetterSpawners plugin;
    private final ConfigurationSection configSection;
    private Map<String, Group> groupMap = new HashMap<>();
    
    public GroupsManager(BetterSpawners plugin){
        this.plugin = plugin;
        configSection = plugin.getConfig().getConfigurationSection("gui.groups.custom-groups");
        getGroups();
    }
    
    /**
     * Checks for groups in the config and adds them to the map.
     */
    private void getGroups(){
        for (String i : configSection.getKeys(false)) {
            try{
                groupMap.put(i, new Group(i, configSection.getInt(i + ".spawner-amount"), configSection.getBoolean(i + ".cansilk")));
            }catch(Exception e){
                System.out.println("Error getting group " + i + ".\n" + e);
            }
        }
        //Add the default section to the map.
        ConfigurationSection defaultGroupSection = plugin.getConfig().getConfigurationSection("gui.groups.default-group");
        groupMap.put("default-group", new Group("default-group", defaultGroupSection.getInt("spawner-amount"), defaultGroupSection.getBoolean("cansilk")));
    }
    
    /**
     * Resets the groups inside of the groupMap.
     */
    public void resetGroups(){
        groupMap = new HashMap<>();
        getGroups();
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
    
    public boolean canPlayerSilk(Player plr){
        return getPlayerGroup(plr).getCanSilk();
    }
    
    public int getSpawnerAmount(Player plr){
        return getPlayerGroup(plr).getSpawnerAmount();
    }
}
