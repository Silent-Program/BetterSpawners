package me.silentprogram.betterspawners.config.classes;

public class Group {
    private String name;
    private int spawnerAmount;
    private boolean canSilk;
    
    public Group(String name, int spawnerAmount, boolean canSilk){
        this.name = name;
        this.spawnerAmount = spawnerAmount;
        this.canSilk = canSilk;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getSpawnerAmount() {
        return spawnerAmount;
    }
    
    public void setSpawnerAmount(int spawnerAmount) {
        this.spawnerAmount = spawnerAmount;
    }
    
    public boolean getCanSilk() {
        return canSilk;
    }
    
    public void setCanSilk(boolean canSilk) {
        this.canSilk = canSilk;
    }
}
