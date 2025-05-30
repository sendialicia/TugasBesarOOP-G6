package main;

import entity.NPC_OldMan;
import java.util.Random;
import object.OBJ_Bin;
import object.OBJ_House;
import object.OBJ_Pond;
import object.SuperObject;

public class AssetSetter {
    GamePanel gp;
    Random random = new Random();

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        OBJ_House house = new OBJ_House(gp, 0, 0);
        OBJ_Bin bin = new OBJ_Bin(gp, 0, 0);
        OBJ_Pond pond = new OBJ_Pond(gp, 0, 0);

        do { 
            int houseX = random.nextInt(32);
            int houseY = random.nextInt(32);

            house.worldX = houseX * gp.tileSize;
            house.worldY = houseY * gp.tileSize;

            int binX = houseX + 7;
            int binY = random.nextInt(houseY, houseY + 7);

            bin.worldX = binX * gp.tileSize;
            bin.worldY = binY * gp.tileSize;
        } while (!checkFitInMap(house) || !checkFitInMap(bin));

        gp.obj[0] = house;
        gp.obj[1] = bin;

        do {
            int pondX = random.nextInt(50);
            int pondY = random.nextInt(50);

            pond.worldX = pondX * gp.tileSize;
            pond.worldY = pondY * gp.tileSize;
        } while (!(checkFitInMap(pond) || checkOverlap(pond, house) || checkOverlap(pond, bin)));

        gp.obj[2] = pond;
    }

    private boolean checkOverlap(SuperObject object, SuperObject other) {
        if (object == null || other == null) return false;

        int objLeft = object.worldX;
        int objRight = object.worldX + object.width * gp.tileSize;
        int objTop = object.worldY;
        int objBottom = object.worldY + object.height * gp.tileSize;

        int otherLeft = other.worldX;
        int otherRight = other.worldX + other.width * gp.tileSize;
        int otherTop = other.worldY;
        int otherBottom = other.worldY + other.height * gp.tileSize;

        return !(objRight <= otherLeft || objLeft >= otherRight || objBottom <= otherTop || objTop >= otherBottom);
    }


    public boolean checkFitInMap(SuperObject object){
        int x = object.worldX / gp.tileSize;
        int y = object.worldY / gp.tileSize;

        int width = x + object.width;
        int height = y + object.height;

        for (int i = x; i < width; i++){
            for (int j = y; j < height; j++){
                if (i < 0 || i >= gp.maxWorldCol || j < 0 || j >= gp.maxWorldRow) return false;
                if (gp.tileM.mapTileNum[i][j] != 293 && gp.tileM.mapTileNum[i][j] != 294 &&
                    gp.tileM.mapTileNum[i][j] != 370 && gp.tileM.mapTileNum[i][j] != 375 ) return false;
            }
        }
        return true;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
}