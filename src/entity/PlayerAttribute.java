package entity;

public class PlayerAttribute {
    String name;
    String gender;
    int energy;
    int playerX;
    int playerY;
    int MAX_ENERGY = 100;
    int MIN_ENERGY = -20;
    String farmName;
    String partner;
    int gold;
    Inventory inventory;

    public PlayerAttribute(String name, String gender, Inventory inventory, String farmName) {
        this.name = name;
        this.gender = gender;
        this.inventory = inventory;
        this.farmName = farmName;
        this.energy = MAX_ENERGY;
        this.partner = null;
        this.gold = 1000;
    }
}
