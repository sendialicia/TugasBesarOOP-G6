package items.equipments;

import entity.Player;

public class Hoe extends Equipments {
    public Hoe() { super("Hoe", ""); }

    @Override
    public void onUse(Player player) { System.out.println("You till the soil using the hoe."); }
}
