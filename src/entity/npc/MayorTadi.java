package entity.npc;

import items.Items;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.GamePanel;

public class MayorTadi extends NPC {

    public MayorTadi(GamePanel gp) {
        super("Mayor Tadi", gp);
    }

    @Override
    protected Items[] mapLovedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Legend")
        };
    }

    @Override
    protected Items[] mapLikedItems(Map<String, Items> itemsMap) {
        return new Items[] {
            itemsMap.get("Angler"),
            itemsMap.get("Crimsonfish"),
            itemsMap.get("Glacierfish")
        };
    }
    
    @Override
    protected Items[] mapHatedItems(Map<String, Items> itemsMap) {
        Items[] loved = mapLovedItems(itemsMap);
        Items[] liked = mapLikedItems(itemsMap);

        Set<Items> lovedAndLiked = new HashSet<>();
        lovedAndLiked.addAll(Arrays.asList(loved));
        lovedAndLiked.addAll(Arrays.asList(liked));

        List<Items> hated = new ArrayList<>();
        for (Items item : itemsMap.values()) {
            if (!lovedAndLiked.contains(item)) {
                hated.add(item);
            }
        }

        return hated.toArray(Items[]::new);
    }
}
