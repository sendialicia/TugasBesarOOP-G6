package items.equipments;

import entity.Player;

public class Pickaxe extends Equipments {
    public Pickaxe() { 
        super("Pickaxe", "/items/Equipments/Pickaxe.png"); 
        super.setDescription("A sturdy pickaxe for mining rocks and ores.");
    }

    @Override
    public void onUse(Player player) { System.out.println("You mine rocks using the pickaxe."); }
}
