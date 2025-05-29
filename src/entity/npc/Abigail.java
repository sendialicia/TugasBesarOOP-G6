package entity.npc;

import items.Items;
import java.util.Map;
import main.GamePanel;

public class Abigail extends NPC {

    public Abigail(GamePanel gp) {
        super("Abigail", gp);
        direction = "down";
        speed = 1;

        getImage();
    }

    public void getImage() {
        up1 = setup("/npc/Abigail/images/Abigail_05");
        up2 = setup("/npc/Abigail/images/Abigail_06");
        down1 = setup("/npc/Abigail/images/Abigail_01");
        down2 = setup("/npc/Abigail/images/Abigail_02");
        left1 = setup("/npc/Abigail/images/Abigail_07");
        left2 = setup("/npc/Abigail/images/Abigail_08");
        right1 = setup("/npc/Abigail/images/Abigail_03");
        right2 = setup("/npc/Abigail/images/Abigail_04");
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
