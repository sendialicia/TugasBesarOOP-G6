package items.crops;

import entity.Player;
import items.Edible;
import items.Items;
import java.awt.image.BufferedImage;

public class Crops extends Items implements Edible {
    private int harvestedAmount;

    private static final int ENERGY = 3;

    public Crops(String name, int harvestedAmount, int sellPrice, int buyPrice, BufferedImage image, String description) {
        super(name, sellPrice, buyPrice, image, description);
        this.harvestedAmount = harvestedAmount;
    }

    public int getHarvestedAmount(){ return harvestedAmount; }

    public void setHarvestedAmount(int harvestedAmount){ this.harvestedAmount = harvestedAmount; }

    @Override
    public void onEat(Player player){ player.addEnergy(ENERGY); }

    @Override
    public int getEnergy(){ return ENERGY; }
}
