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

    final int MAX_ATTEMPTS = 1000000;
    int attempt;

    attempt = 0;
    do {
        int houseX = random.nextInt(gp.maxWorldCol);
        int houseY = random.nextInt(gp.maxWorldRow);

        house.worldX = houseX * gp.tileSize;
        house.worldY = houseY * gp.tileSize;

        int binX = houseX + 7;
        int binY = houseY + 3 + random.nextInt(3);

        bin.worldX = binX * gp.tileSize;
        bin.worldY = binY * gp.tileSize;

        attempt++;
        if (attempt > MAX_ATTEMPTS) {
            System.out.println("Failed to place house after " + MAX_ATTEMPTS + " attempts.");
            return;
        }
    } while (!checkFitInMap(house) || !checkFitInMap(bin));
    gp.obj[0] = house;
    gp.obj[1] = bin;

    // Place pond (no overlap with house/bin)
    System.out.println("Placing pond...");
    attempt = 0;
    do {
        int pondX = random.nextInt(gp.maxWorldCol);
        int pondY = random.nextInt(gp.maxWorldRow);

        pond.worldX = pondX * gp.tileSize;
        pond.worldY = pondY * gp.tileSize;

        attempt++;
        if (attempt > MAX_ATTEMPTS) {
            System.out.println("Failed to place pond after " + MAX_ATTEMPTS + " attempts.");
            return;
        }
    } while (!checkFitInMap(pond) || checkOverlap(pond, house) || checkOverlap(pond, bin));
    gp.obj[2] = pond;

    System.out.println("All objects placed successfully.");
}

    private boolean checkOverlap(SuperObject object, SuperObject other) {
        if (object == null || other == null) return false;

        int objLeft = object.worldX;
        int objRight = object.worldX + object.width;
        int objTop = object.worldY;
        int objBottom = object.worldY + object.height;

        int otherLeft = other.worldX;
        int otherRight = other.worldX + other.width;
        int otherTop = other.worldY;
        int otherBottom = other.worldY + other.height;

        return !(objRight <= otherLeft || objLeft >= otherRight || objBottom <= otherTop || objTop >= otherBottom);
    }


    public boolean checkFitInMap(SuperObject object){
        int x = object.worldX / gp.tileSize;
        int y = object.worldY / gp.tileSize;

        int width = x + object.width / gp.tileSize;
        int height = y + object.height / gp.tileSize;

        for (int i = x; i < width; i++){
            for (int j = y; j < height; j++){
                if (i < 0 || i >= gp.maxWorldCol || j < 0 || j >= gp.maxWorldRow) return false;
                if (gp.tileM.mapTileNum[i][j] != 293 && gp.tileM.mapTileNum[i][j] != 294 &&
                    gp.tileM.mapTileNum[i][j] != 370 && gp.tileM.mapTileNum[i][j] != 375 ) return false;

                if (i == 5) return false;
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