package farmTile;

import items.seeds.Seeds;

public class PlantedTile extends TileObject {
    private Seeds seed;
    private int plantedDay;
    private int lastWatered = 0;
    private boolean readyToHarvest = false;

    public PlantedTile(Seeds seed, int plantedDay){
        super("Planted", 0, 0);
        this.seed = seed;
        this.plantedDay = plantedDay;
    }

    public void water(int currentDay){
        this.lastWatered = currentDay;
    }

    public void update(int currentDay, boolean isRaining){
        if (!isNeglected(currentDay, isRaining)){
            int daysPassed = currentDay - plantedDay;
            if (daysPassed >= seed.getHarvestDays()) readyToHarvest = true;
            else readyToHarvest = false;
        }
    }

    public boolean isWateredToday(int currentDay, boolean isRaining){
        if (isRaining) water(currentDay);
        return lastWatered == currentDay;
    }

    public boolean isNeglected(int currentDay, boolean isRaining){
        if (isRaining){
            water(currentDay);
            return false;
        } 
        return currentDay - plantedDay > 1  && currentDay - lastWatered > 2;
    }

    public boolean isReadyToHarvest(){ return readyToHarvest; }
    public Seeds getSeed(){ return seed; }
    public int getLastWatered(){ return lastWatered; }

}
