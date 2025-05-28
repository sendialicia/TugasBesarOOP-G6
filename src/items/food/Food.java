package items.food;

import entity.Player;
import items.Edible;
import items.Items;

public class Food extends Items implements Edible {
    private int energy;

    public Food(String name, int sellPrice, int buyPrice, int energy){
        super(name, sellPrice, buyPrice);
        this.energy = energy;
    }

    public int getEnergy(){ return energy; }
    public void setEnergy(int energy){ this.energy = energy; }

    @Override
    public void onEat(Player player){ player.addEnergy(energy); }
}
