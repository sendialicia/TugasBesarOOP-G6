package items;

import java.util.HashMap;
import java.util.Map;

import items.equipments.FishingRod;
import items.equipments.Hoe;
import items.equipments.Pickaxe;
import items.equipments.WateringCan;
import items.fish.Fish;
import items.seeds.Seeds;

public class Inventory {
    private Map<Items, Integer> items;

    public Inventory(Boolean b) {
        items = new HashMap<>();
        ItemFactory.loadSeeds();
        ItemFactory.loadFish();
        if (!b){}
        else{
            Seeds seed = (Seeds) ItemFactory.get("Parsnip Seeds");
            Fish fish = (Fish) ItemFactory.get("Angler");
            Fish fish1 = (Fish) ItemFactory.get("Carp");
            Fish fish2 = (Fish) ItemFactory.get("Bullhead");
            Fish fish3 = (Fish) ItemFactory.get("Legend");
            Fish fish4 = (Fish) ItemFactory.get("Halibut");
            Fish fish5 = (Fish) ItemFactory.get("Largemouth Bass");
            Fish fish6 = (Fish) ItemFactory.get("Midnight Carp");
            Fish fish7 = (Fish) ItemFactory.get("Octopus");
            Fish fish8 = (Fish) ItemFactory.get("Pufferfish");
            Fish fish9 = (Fish) ItemFactory.get("Rainbow Trout");
            Fish fish10 = (Fish) ItemFactory.get("Sardine");
            Fish fish11 = (Fish) ItemFactory.get("Salmon");
            Fish fish12 = (Fish) ItemFactory.get("Sturgeon");
            Fish fish13 = (Fish) ItemFactory.get("Super Cucumber");
            Fish fish14 = (Fish) ItemFactory.get("Catfish");
            Fish fish15 = (Fish) ItemFactory.get("Crimsonfish");
            Fish fish16 = (Fish) ItemFactory.get("Glacierfish");
            items.put(new Hoe(), 1);
            items.put(new FishingRod(), 1);
            items.put(new Pickaxe(), 1);
            items.put(new WateringCan(), 1);
            items.put(seed, 15);
            items.put(fish, 2);
            items.put(fish1, 2);
            items.put(fish2, 2);
            items.put(fish3, 2);
            items.put(fish4, 2);
            items.put(fish5, 2);
            items.put(fish6, 2);
            items.put(fish7, 2);
            items.put(fish8, 2);
            items.put(fish9, 2);
            items.put(fish10, 2);
            items.put(fish11, 2);
            items.put(fish12, 2);
            items.put(fish13, 2);
            items.put(fish14, 2);
            items.put(fish15, 2);
            items.put(fish16, 2);
        }
    }

    public Inventory(Map<Items, Integer> items) {
        this.items = items;
    }

    public Map<Items, Integer> getItems() {
        return items;
    }

    public void addItem(Items item, int quantity) {
        if (items.containsKey(item)) items.put(item, items.get(item) + quantity);
        else items.put(item, quantity);
    }

    public void removeItem(Items item, int quantity) {
        if (items.containsKey(item)) {
            int currentQuantity = items.get(item);
            if (currentQuantity > quantity) items.put(item, currentQuantity - quantity);
            else items.remove(item);
        }
    }

    public String getMostItem() {
        // if (items.isEmpty()) return null;
        // Items mostItem = null;
        // int maxQuantity = 0;
        // for (Map.Entry<Items, Integer> entry : items.entrySet()) {
        //     if (entry.getValue() > maxQuantity) {
        //         maxQuantity = entry.getValue();
        //         mostItem = entry.getKey();
        //     }
        // }
        // return mostItem != null ? mostItem.getName() : null;
        return "The Legends of Spakbor";
    }

    public int totalItems() {
        int total = 0;
        for (int quantity : items.values()) total += quantity;
        return total;
    }
    public int getItemQuantity(Items item) { return items.getOrDefault(item, 0); }
    
    public void clear() { items.clear(); }
    
    public boolean isEmpty() { return items.isEmpty(); }
    
    public Items get(String itemName) {
        for (Items item : items.keySet()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public int count() {
        return items.size();
    }
}
