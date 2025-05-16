public class Item {
    private int itemId;
    private String itemName;
    private int priceBuy;
    private int priceSell;
    private String description;

    // Constructor
    public Item(int itemId, String itemName, int priceBuy, int priceSell, String description) {}

    // Overloaded constructor for compatibility
    public Item(String itemName) {}

    // Getters and Setters
    public int getItemId() { return 0; }
    public String getName() { return null; }
    public void setItemName(String itemName) {}
    public int getPriceBuy() { return 0; }
    public void setPriceBuy(int priceBuy) {}
    public int getPriceSell() { return 0; }
    public void setPriceSell(int priceSell) {}
    public String getDescription() { return null; }
    public String getItemInfo() { return null; }
}