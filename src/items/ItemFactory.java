package items;

import items.crops.CropsLoader;
import items.equipments.FishingRod;
import items.equipments.Hoe;
import items.equipments.Pickaxe;
import items.equipments.WateringCan;
import items.fish.FishLoader;
import items.food.FoodLoader;
import items.miscellaneous.MiscellaneousLoader;
import items.seeds.SeedsLoader;
import java.util.HashMap;
import java.util.Map;

public class ItemFactory {
    private static final Map<String, Items> itemsByName = new HashMap<>();

    public static void register(Items item) {
        itemsByName.put(item.getName().toLowerCase(), item);
    }

    public static Items get(String name) {
        return itemsByName.get(name.toLowerCase());
    }

    public static Map<String, Items> getAllItems() {
        return itemsByName;
    }

    public static void loadEquipments() {
        register(new Hoe());
        register(new FishingRod());
        register(new Pickaxe());
        register(new WateringCan());
    }

    public static void loadFish() {
        FishLoader.load().forEach(ItemFactory::register);
    }

    public static void loadSeeds() {
        SeedsLoader.load().forEach(ItemFactory::register);
    }

    public static void loadFood() {
        FoodLoader.load().forEach(ItemFactory::register);
    }

    public static void loadMiscellaneous() {
        MiscellaneousLoader.load().forEach(ItemFactory::register);
    }

    public static void loadCrops() {
        CropsLoader.load().forEach(ItemFactory::register);
    }

    public static void loadAll() {
        loadFish();
        loadSeeds();
        loadFood();
        loadMiscellaneous();
        loadCrops();
        loadEquipments();
    }
}
