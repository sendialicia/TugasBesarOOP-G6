package farmTile;

import items.ItemFactory;
import items.Items;
import items.crops.Crops;

public class HarvestableTile extends TileObject {
    private Crops crop;

    public HarvestableTile(Crops crop){
        super("Harvestable");
        this.crop = crop;
    }
    
    public HarvestableTile(PlantedTile plantedTile){
        super("Harvestable");
        ItemFactory cropsFactory = new ItemFactory();
        cropsFactory.loadCrops();

        String seedName = plantedTile.getSeed().getName();
        String cropName = seedName.substring(0, seedName.length() - " Seeds".length());

        Items cropItem = cropsFactory.get(cropName);
        this.crop = (Crops) cropItem;

        this.location = plantedTile.location;
    }

    public Crops getCrops(){ return crop; }
}
