package entity.npc;

import items.Items;
import java.util.Map;
import main.GamePanel;

public class Abigail extends NPC {

    public Abigail(GamePanel gp) {
        super("Abigail", gp);
    }
    
    @Override
    protected Items[] mapLovedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Blueberry"),
            itemsMap.get("Melon"),
            itemsMap.get("Pumpkin"),
            itemsMap.get("Grape"),
            itemsMap.get("Cranberry")
        };
    }

    @Override
    protected Items[] mapLikedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Baguette"),
            itemsMap.get("Pumpkin Pie"),
            itemsMap.get("Wine")
        };
    }

    @Override
    protected Items[] mapHatedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Hot Pepper"),
            itemsMap.get("Cauliflower"),
            itemsMap.get("Parsnip"),
            itemsMap.get("Wheat")
        };
    }
    
}
