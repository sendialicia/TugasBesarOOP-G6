package items.equipments;

import entity.Player;

public class FishingRod extends Equipments {
    public FishingRod() { super("Fishing Rod", ""); }

    @Override
    public void onUse(Player player) { System.out.println("You cast the fishing rod into the water."); }   
}
