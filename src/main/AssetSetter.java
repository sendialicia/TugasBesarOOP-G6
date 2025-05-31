package main;

import entity.NPC_OldMan;
import java.util.Random;
import object.OBJ_Barrel;
import object.OBJ_Bin;
import object.OBJ_Bush;
import object.OBJ_Clock;
import object.OBJ_Fence;
import object.OBJ_GreenTree1;
import object.OBJ_House;
import object.OBJ_PinkTree;
import object.OBJ_Pond;
import object.OBJ_Sign;
import object.OBJ_Well;
import object.SuperObject;

public class AssetSetter {
    GamePanel gp;
    Random random = new Random();

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int mapNum = 0;
        OBJ_House house = new OBJ_House(gp, 0, 0);
        OBJ_Bin bin = new OBJ_Bin(gp, 0, 0);
        OBJ_Pond pond = new OBJ_Pond(gp, 0, 0);

        final int MAX_ATTEMPTS = 1000000;
        OBJ_PinkTree pt1 = new OBJ_PinkTree(gp, 11 * gp.tileSize, 17 * gp.tileSize);
        gp.obj[mapNum][30] = pt1;
        OBJ_Well well = new OBJ_Well(gp, 12 * gp.tileSize, 32 * gp.tileSize);
        gp.obj[mapNum][3] = well;
        OBJ_GreenTree1 gt1 = new OBJ_GreenTree1(gp, 13 * gp.tileSize, 25 * gp.tileSize);
        gp.obj[mapNum][4] = gt1;
        OBJ_Bush bush1 = new OBJ_Bush(gp, 10 * gp.tileSize, 25 * gp.tileSize);
        gp.obj[mapNum][5] = bush1;
        OBJ_GreenTree1 gt2 = new OBJ_GreenTree1(gp, 9 * gp.tileSize, 13 * gp.tileSize);
        gp.obj[mapNum][6] = gt2;
        OBJ_Clock clock = new OBJ_Clock(gp, 20 * gp.tileSize, 20 * gp.tileSize);
        gp.obj[mapNum][7] = clock;
        OBJ_Bush bush2 = new OBJ_Bush(gp, 12 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][8] = bush2;
        OBJ_Bush bush3 = new OBJ_Bush(gp, 15 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][9] = bush3;
        OBJ_Bush bush4 = new OBJ_Bush(gp, 18 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][10] = bush4;
        OBJ_Bush bush5 = new OBJ_Bush(gp, 21 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][11] = bush5;
        
        OBJ_Barrel barrel1 = new OBJ_Barrel(gp, 22 * gp.tileSize, 7 * gp.tileSize);
        gp.obj[mapNum][30] = barrel1;
        OBJ_Sign sign1 = new OBJ_Sign(gp, 29 * gp.tileSize, 8 * gp.tileSize);
        gp.obj[mapNum][31] = sign1;
        OBJ_Fence fence1 = new OBJ_Fence(gp, 15 * gp.tileSize, 15 * gp.tileSize);
        gp.obj[mapNum][33] = fence1;
        OBJ_Fence fence2 = new OBJ_Fence(gp, 29 * gp.tileSize, 15 * gp.tileSize);
        gp.obj[mapNum][34] = fence2;
        OBJ_Barrel barrel2 = new OBJ_Barrel(gp, 40 * gp.tileSize, 13 * gp.tileSize);
        gp.obj[mapNum][35] = barrel2;
        OBJ_PinkTree pt8 = new OBJ_PinkTree(gp, 41 * gp.tileSize, 12 * gp.tileSize);
        gp.obj[mapNum][36] = pt8;
        OBJ_Bush bush15 = new OBJ_Bush(gp, 41 * gp.tileSize, 17 * gp.tileSize);
        gp.obj[mapNum][37] = bush15;

        OBJ_Bush bush6 = new OBJ_Bush(gp, 29 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][12] = bush6;
        OBJ_Bush bush7 = new OBJ_Bush(gp, 32 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][13] = bush7;
        OBJ_Bush bush8 = new OBJ_Bush(gp, 35 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][14] = bush8;
        OBJ_Bush bush9 = new OBJ_Bush(gp, 38 * gp.tileSize, 9 * gp.tileSize);
        gp.obj[mapNum][15] = bush9;
        OBJ_PinkTree pt2 = new OBJ_PinkTree(gp, 6 * gp.tileSize, 8 * gp.tileSize);
        gp.obj[mapNum][16] = pt2;
        OBJ_PinkTree pt3 = new OBJ_PinkTree(gp, 6 * gp.tileSize, 32 * gp.tileSize);
        gp.obj[mapNum][17] = pt3;
        OBJ_GreenTree1 gt3 = new OBJ_GreenTree1(gp, 7 * gp.tileSize, 40 * gp.tileSize);
        gp.obj[mapNum][18] = gt3;
        OBJ_Bush bush10 = new OBJ_Bush(gp, 12 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][19] = bush10;
        OBJ_Bush bush11 = new OBJ_Bush(gp, 15 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][20] = bush11;
        OBJ_Bush bush12 = new OBJ_Bush(gp, 18 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][21] = bush12;
        OBJ_Bush bush13 = new OBJ_Bush(gp, 21 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][22] = bush13;
        OBJ_PinkTree pt4 = new OBJ_PinkTree(gp, 24 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][23] = pt4;
        OBJ_PinkTree pt5 = new OBJ_PinkTree(gp, 30 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][24] = pt5;
        OBJ_PinkTree pt6 = new OBJ_PinkTree(gp, 36 * gp.tileSize, 41 * gp.tileSize);
        gp.obj[mapNum][25] = pt6;
        OBJ_GreenTree1 gt4 = new OBJ_GreenTree1(gp, 42 * gp.tileSize, 38 * gp.tileSize);
        gp.obj[mapNum][26] = gt4;
        OBJ_Bush bush14 = new OBJ_Bush(gp, 41 * gp.tileSize, 30 * gp.tileSize);
        gp.obj[mapNum][27] = bush14;
        OBJ_PinkTree pt7 = new OBJ_PinkTree(gp, 42 * gp.tileSize, 33 * gp.tileSize);
        gp.obj[mapNum][28] = pt7;
        OBJ_GreenTree1 gt5 = new OBJ_GreenTree1(gp, 42 * gp.tileSize, 22 * gp.tileSize);
        gp.obj[mapNum][29] = gt5;

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
        } while (!checkFitInMap(house) || !checkFitInMap(bin) || checkOverlap(house, gp.obj[mapNum], 0));
        gp.obj[mapNum][0] = house;
        gp.obj[mapNum][1] = bin;

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
        } while (!checkFitInMap(pond) || checkOverlap(pond, gp.obj[mapNum], 2));
        gp.obj[mapNum][2] = pond;
        System.out.println("All objects placed successfully.");
    }

    private boolean checkOverlap(SuperObject object, SuperObject[] others, int index) {
        if (object == null || others == null) return false;

        int objLeft = object.worldX;
        int objRight = object.worldX + object.width;
        int objTop = object.worldY;
        int objBottom = object.worldY + object.height;

        for (int i = 0; i < others.length; i++) {
            if (i == index) continue;
            SuperObject other = others[i];
            if (other == null) continue;

            int otherLeft = other.worldX;
            int otherRight = other.worldX + other.width;
            int otherTop = other.worldY;
            int otherBottom = other.worldY + other.height;

            boolean isOverlapping = !(objRight <= otherLeft || objLeft >= otherRight ||
                                    objBottom <= otherTop || objTop >= otherBottom);
            if (isOverlapping) {
                return true;
            }
        }

        return false;
    }

    public boolean checkFitInMap(SuperObject object){
        int x = object.worldX / gp.tileSize;
        int y = object.worldY / gp.tileSize;

        int width = x + object.width / gp.tileSize;
        int height = y + object.height / gp.tileSize;

        for (int i = x; i < width; i++){
            for (int j = y; j < height; j++){
                if (i < 0 || i >= gp.maxWorldCol || j < 0 || j >= gp.maxWorldRow) return false;
                if (gp.tileM.mapTileNum[gp.currentMap][i][j] != 293 && gp.tileM.mapTileNum[gp.currentMap][i][j] != 294 &&
                    gp.tileM.mapTileNum[gp.currentMap][i][j] != 370 && gp.tileM.mapTileNum[gp.currentMap][i][j] != 375 ) return false;

                if (i == 6) return false;
            }
        }
        return true;
    }

    public void setNPC() {
        int mapNum = 1;
        gp.npc[mapNum][0] = new NPC_OldMan(gp);
        gp.npc[mapNum][0].worldX = gp.tileSize * 21;
        gp.npc[mapNum][0].worldY = gp.tileSize * 21;
    }
}