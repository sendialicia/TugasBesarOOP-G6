package items.equipments;

import entity.Player;

public class WateringCan extends Equipments {
    public WateringCan() {
        super("Watering Can", "/items/Equipments/Watering Can.png");
        super.setDescription("A watering can used to \nwater crops and plants.");
    }

    @Override
    public void onUse(Player player) { System.out.println("You water the crops with the watering can."); }
}
