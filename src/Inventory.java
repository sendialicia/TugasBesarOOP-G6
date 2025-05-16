import java.util.Map;

public class Inventory {
    private Map<Item, Integer> items;

    // Constructor
    public Inventory() {}

    // Getter
    public boolean isEmpty() { return false; }
    public boolean hasItem(Item item) { return false; }
    public boolean hasItem(String itemName) { return false; }
    public boolean hasSellableItems() { return false; }
    public int countUnique() { return 0; }
    public int getItemQuantity(Item item) { return 0; }
    public Map<Item, Integer> getAll() { return null; }

    // Setter
    public void addItem(Item item, int quantity) {}
    public void removeItem(Item item, int quantity) {}
    public void clear() {}
}