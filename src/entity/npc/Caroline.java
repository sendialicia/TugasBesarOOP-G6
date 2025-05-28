package entity.npc;

import items.Items;
import java.util.Map;
import main.GamePanel;

public class Caroline extends NPC {

    public Caroline(GamePanel gp) {
        super("Caroline", gp);
    }

    @Override
    protected Items[] mapLovedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Firewood"),
            itemsMap.get("Coal")
        };
    }

    @Override
    protected Items[] mapLikedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Potato"),
            itemsMap.get("Wheat")
        };
    }

    @Override
    protected Items[] mapHatedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Hot Pepper"),
        };
    }
    
}
