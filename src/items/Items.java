package items;

import java.awt.image.BufferedImage;

public class Items {
    private String name;
    private String description = null;
    private Integer sellPrice = null;
    private Integer buyPrice = null;
    private BufferedImage image = null;

    public Items(String name){
        this.name = name;
    }

    public Items(String name, Integer sellPrice, Integer buyPrice){
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
}