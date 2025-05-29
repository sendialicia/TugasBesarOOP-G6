package items.equipments;


import entity.Player;
import items.Items;

public class Equipments extends Items {
    public Equipments(String name, String imagePath){ 
        super(name);
        this.setItemImage(setup(imagePath));
    }

    public void onUse(Player player){ System.out.println("Using the " + super.getName() + ".");}
}
