package me.silentprogram.betterspawners.core.config.classes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Data {
	Map<UUID, List<ItemClass>> playerListMap = new HashMap<>();
	Map<UUID, Long> playerTimeMap = new HashMap<>();
	
	public Map<UUID, List<ItemClass>> getPlayerListMap() {
		return playerListMap;
	}
	
	public void setPlayerList(UUID uuid, List<ItemClass> items) {
		playerListMap.remove(uuid);
		playerListMap.put(uuid, items);
	}
	
	public Long getPlayerTime(UUID uuid) {
		Long playerTime = 0L;
		if (playerTimeMap.containsKey(uuid)) {
			playerTime = playerTimeMap.get(uuid);
		}
		return playerTime;
	}
	
	public void setPlayerTime(UUID uuid, Long loong) {
		playerTimeMap.remove(uuid);
		playerTimeMap.put(uuid, loong);
	}
}
