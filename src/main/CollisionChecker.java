package main;

import entity.Entity;
import java.awt.Rectangle;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                break;
            default:
                return;
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        Rectangle entityArea = new Rectangle(
            entity.worldX + entity.solidArea.x,
            entity.worldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                Rectangle objectArea = new Rectangle(
                    gp.obj[i].worldX + gp.obj[i].solidArea.x,
                    gp.obj[i].worldY + gp.obj[i].solidArea.y,
                    gp.obj[i].solidArea.width,
                    gp.obj[i].solidArea.height
                );

                // Simulate movement
                switch (entity.direction) {
                    case "up": entityArea.y -= entity.speed; break;
                    case "down": entityArea.y += entity.speed; break;
                    case "left": entityArea.x -= entity.speed; break;
                    case "right": entityArea.x += entity.speed; break;
                }

                if (entityArea.intersects(objectArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
        }

        return index;
    }

    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        Rectangle entityArea = new Rectangle(
            entity.worldX + entity.solidArea.x,
            entity.worldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                Rectangle targetArea = new Rectangle(
                    target[i].worldX + target[i].solidArea.x,
                    target[i].worldY + target[i].solidArea.y,
                    target[i].solidArea.width,
                    target[i].solidArea.height
                );

                switch (entity.direction) {
                    case "up": entityArea.y -= entity.speed; break;
                    case "down": entityArea.y += entity.speed; break;
                    case "left": entityArea.x -= entity.speed; break;
                    case "right": entityArea.x += entity.speed; break;
                }

                if (entityArea.intersects(targetArea)) {
                    entity.collisionOn = true;
                    index = i;
                }
            }
        }

        return index;
    }

    public void checkPlayer(Entity entity) {
        Rectangle entityArea = new Rectangle(
            entity.worldX + entity.solidArea.x,
            entity.worldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        Rectangle playerArea = new Rectangle(
            gp.player.worldX + gp.player.solidArea.x,
            gp.player.worldY + gp.player.solidArea.y,
            gp.player.solidArea.width,
            gp.player.solidArea.height
        );

        switch (entity.direction) {
            case "up": entityArea.y -= entity.speed; break;
            case "down": entityArea.y += entity.speed; break;
            case "left": entityArea.x -= entity.speed; break;
            case "right": entityArea.x += entity.speed; break;
        }

        if (entityArea.intersects(playerArea)) {
            entity.collisionOn = true;
        }
    }
}