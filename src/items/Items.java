package items;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.UtilityTool;

public class Items {
    private String name;
    private String description = null;
    private Integer sellPrice = null;
    private Integer buyPrice = null;
    protected BufferedImage image = null;

    public Items(String name){
        this.name = name;
    }

    public Items(String name, Integer sellPrice, Integer buyPrice, BufferedImage image, String description) {
        this.description = description;
        this.image = image;
        this.name = name;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;

        if (sellPrice != null && sellPrice < 0) this.sellPrice = null;
        if (buyPrice != null && buyPrice < 0) this.buyPrice = null;
    }

    public String getName(){ return name; }
    public String getDescription() { return description; }
    public BufferedImage getItemImage() { return image; }
    public Integer getSellPrice(){ return sellPrice; }
    public Integer getBuyPrice(){ return buyPrice; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setItemImage(BufferedImage image) { this.image = image; }
    public void setSellPrice(Integer sellPrice){ this.sellPrice = sellPrice; }
    public void setBuyPrice(Integer buyPrice){ this.buyPrice = buyPrice; }

    public BufferedImage setup(String imagePath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
            image = uTool.scaleImage(image, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Items item = (Items) o;
        return this.getName().equals(item.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}