package items.miscellaneous;
import java.awt.image.BufferedImage;

import items.Items;

public class Miscellaneous extends Items {
    public Miscellaneous(String name, Integer sellPrice, Integer buyPrice, BufferedImage image, String description) {
        super(name, null, null, image, description);
        if (sellPrice != null && buyPrice != null && sellPrice < buyPrice){
            super.setBuyPrice(buyPrice);
            super.setSellPrice(sellPrice);
        } else {
            throw new IllegalArgumentException("Sell price cannot be less than buy price");
        }
    }
}