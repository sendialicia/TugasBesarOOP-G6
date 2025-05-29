package items.food;

import java.awt.image.BufferedImage;

import entity.Player;
import items.Edible;
import items.Items;

public class Food extends Items implements Edible {
    private int energy;

    public Food(String name, int sellPrice, int buyPrice, int energy, BufferedImage image, String description) {
        super(name, sellPrice, buyPrice, image, description);
        this.energy = energy;
    }

    public int getEnergy(){ return energy; }
    public void setEnergy(int energy){ this.energy = energy; }

    @Override
    public void onEat(Player player){ player.addEnergy(energy); }
}
