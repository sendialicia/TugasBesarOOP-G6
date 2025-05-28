package items.crops;

import entity.Player;
import items.Edible;
import items.Items;

public class Crops extends Items implements Edible {
    private int harvestedAmount;

    private static final int ENERGY = 3;

    public Crops(String name, int harvestedAmount, int sellPrice, int buyPrice) {
        super(name, sellPrice, buyPrice);
        this.harvestedAmount = harvestedAmount;
    }

    public int getEnergy(){ return ENERGY; }
    public int harvestedAmount(){ return harvestedAmount; }

    public void setHarvestedAmount(int harvestedAmount){ this.harvestedAmount = harvestedAmount; }

    @Override
    public void onEat(Player player){ player.addEnergy(ENERGY); }
}
