package items.equipments;

import entity.Player;

public class Hoe extends Equipments {
    public Hoe() { 
        super("Hoe", "/items/Equipments/Hoe.png"); 
        super.setDescription("A tool used for tilling \nsoil and preparing it \nfor planting crops.");
    }

    @Override
    public void onUse(Player player) { System.out.println("You till the soil using the hoe."); }
}
