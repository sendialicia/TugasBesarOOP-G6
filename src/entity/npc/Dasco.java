package entity.npc;

import items.Items;
import java.util.Map;
import main.GamePanel;

public class Dasco extends NPC {

    public Dasco(GamePanel gp) {
        super("Dasco", gp);
    }

    @Override
    protected Items[] mapLovedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("The Legends of Spakbor"),
            itemsMap.get("Cooked Pig's Head"),
            itemsMap.get("Wine"),
            itemsMap.get("Fugu"),
            itemsMap.get("Spakbor Salad")
        };
    }

    @Override
    protected Items[] mapLikedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Fish Sandwich"),
            itemsMap.get("Fish Stew"),
            itemsMap.get("Baguette"),
            itemsMap.get("Fish n’ Chips")
        };
    }
    
    @Override
    protected Items[] mapHatedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Legend"),
            itemsMap.get("Grape"),
            itemsMap.get("Cauliflower"),
            itemsMap.get("Wheat"),
            itemsMap.get("Pufferfish"),
            itemsMap.get("Salmon")
        };
    }
    
}
