package items;

import entity.Player;

public interface Edible {
    public void onEat(Player player);
    public int getEnergy();
}
