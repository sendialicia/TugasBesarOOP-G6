package items;

import java.util.HashMap;
import java.util.Map;

import items.crops.Crops;
import items.equipments.FishingRod;
import items.equipments.Hoe;
import items.equipments.Pickaxe;
import items.equipments.WateringCan;
import items.food.Food;
import items.miscellaneous.Miscellaneous;
import items.seeds.Seeds;

    public class Inventory {
        private Map<Items, Integer> items;


    public Inventory() {
        items = new HashMap<>();
        for (Items item : ItemFactory.getAllItems().values()) {
            if (item.getBuyPrice() != null && item.getBuyPrice() > 0 &&
                (item instanceof Seeds || item instanceof Food || 
                item instanceof Miscellaneous || item instanceof Crops)) {
                items.put(item, 999);
            }
        }
    }
    public Inventory(Boolean b) {
        items = new HashMap<>();
        if (!b){}
        else{
            items.put(ItemFactory.get("Hoe"), 1);
            items.put(ItemFactory.get("Fishing Rod"), 1);
            items.put(ItemFactory.get("Pickaxe"), 1);
            items.put(ItemFactory.get("Watering Can"), 1);
            Seeds seed = (Seeds) ItemFactory.get("Parsnip Seeds");
            items.put(seed, 15);

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
            if (items.isEmpty()) return null;
            Items mostItem = null;
            int maxQuantity = 0;
            for (Map.Entry<Items, Integer> entry : items.entrySet()) {
                if (entry.getValue() > maxQuantity) {
                    maxQuantity = entry.getValue();
                    mostItem = entry.getKey();
                }
            }
            return mostItem != null ? mostItem.getName() : null;
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
