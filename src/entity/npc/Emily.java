package entity.npc;

import items.Items;
import items.seeds.Seeds;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.GamePanel;

public class Emily extends NPC {

    public Emily(GamePanel gp) {
        super("Emily", gp);
    }

    @Override
    protected Items[] mapLovedItems(Map<String, Items> itemsMap) {
        List<Items> loved = new ArrayList<>();
        for (Items item : itemsMap.values()) {
            if (item instanceof Seeds) {
                loved.add(item);
            }
        }

        return loved.toArray(Items[]::new);
    }

    @Override
    protected Items[] mapLikedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Catfish"),
            itemsMap.get("Salmon"),
            itemsMap.get("Sardine")
        };
    }

    @Override
    protected Items[] mapHatedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Coal"),
            itemsMap.get("Wood")
        };
    }
    
}
