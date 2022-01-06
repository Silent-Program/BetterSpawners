package me.silentprogram.betterspawners.core.config.classes;

public class ItemClass {
	int multiplier = 1;
	int xp = 0;
	long lastGen;
	String entityType;
	String creatorName;
	boolean mined;
	
	public boolean isMined() {
		return mined;
	}
	
	public ItemClass setMined(boolean mined) {
			this.mined = mined;
		return this;
	}
	
	public ItemClass setLastGen(long lastGen) {
		this.lastGen = lastGen;
		return this;
	}
	
	public long getLastGen() {
		return lastGen;
	}
	
	public ItemClass setXp(int xp) {
		this.xp = xp;
		return this;
	}
	
	public int getXp() {
		return xp;
	}
	
	public int getMultiplier() {
		return multiplier;
	}
	
	public ItemClass setMultiplier(int multiplier) {
		this.multiplier = multiplier;
		return this;
	}
	
	public String getCreatorName() {
		return creatorName;
	}
	
	public ItemClass setCreatorName(String creatorName) {
		this.creatorName = creatorName;
		return this;
	}
	
	public String getEntityType() {
		return entityType;
	}
	
	public ItemClass setEntityType(String entityType) {
		this.entityType = entityType;
		return this;
	}
}
