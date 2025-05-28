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
        int houseTileWidth = 6;
        int houseTileHeight = 8;

        int maxHouseX = 25 - houseTileWidth;
        int houseCandidateX = 1 + random.nextInt(maxHouseX); // So starts from 1 to 19
        int houseCandidateY = 2 + random.nextInt(27 - houseTileHeight);


        gp.obj[0] = new OBJ_House(gp, houseCandidateX * gp.tileSize, houseCandidateY * gp.tileSize);

        int binTileWidth = 3;
        int binTileHeight = 2;

        int binCandidateX = houseCandidateX + houseTileWidth + 1;
        if (binCandidateX + binTileWidth > 30) {
            binCandidateX = 30 - binTileWidth;
            if (binCandidateX <= 0) binCandidateX = 1;
        }

        int binMinY = houseCandidateY;
        int binMaxY = houseCandidateY + houseTileHeight - binTileHeight;

        binMinY = Math.max(binMinY, 2);
        binMaxY = Math.min(binMaxY, 27 - binTileHeight);

        int binCandidateY;
        if (binMaxY < binMinY) binCandidateY = binMinY;
        else binCandidateY = binMinY + random.nextInt(binMaxY - binMinY + 1);

        gp.obj[1] = new OBJ_Bin(gp, binCandidateX * gp.tileSize, binCandidateY * gp.tileSize);

        int pondTileWidth = 4;
        int pondTileHeight = 4;
        int pondCandidateX, pondCandidateY;
        boolean pondCanBePlaced;
        int attempts = 0;

        do {
            pondCandidateX = 1 + random.nextInt(30 - pondTileWidth);
            pondCandidateY = 2 + random.nextInt(28 - pondTileHeight);

            pondCanBePlaced = true;

            if (checkOverlap(pondCandidateX, pondCandidateY, pondTileWidth, pondTileHeight, gp.obj[0])) pondCanBePlaced = false;
            if (pondCanBePlaced && checkOverlap(pondCandidateX, pondCandidateY, pondTileWidth, pondTileHeight, gp.obj[1])) pondCanBePlaced = false;

            attempts++;
        } while (!pondCanBePlaced && attempts < 100);

        if (pondCanBePlaced) gp.obj[2] = new OBJ_Pond(gp, pondCandidateX * gp.tileSize, pondCandidateY * gp.tileSize);
        else System.out.println("Could not place pond without overlap after " + attempts + " attempts.");
    }


    private boolean checkOverlap(int newObjTileX, int newObjTileY, int newObjTileWidth, int newObjTileHeight, SuperObject existingObj) {
        if (existingObj == null) return false;

        int existingObjTileX = existingObj.worldX / gp.tileSize;
        int existingObjTileY = existingObj.worldY / gp.tileSize;

        int existingObjTileWidth = existingObj.width / gp.tileSize;
        int existingObjTileHeight = existingObj.height / gp.tileSize;

        boolean overlapX = newObjTileX < existingObjTileX + existingObjTileWidth &&
                           newObjTileX + newObjTileWidth > existingObjTileX;
        boolean overlapY = newObjTileY < existingObjTileY + existingObjTileHeight &&
                           newObjTileY + newObjTileHeight > existingObjTileY;

        return overlapX && overlapY;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }
}