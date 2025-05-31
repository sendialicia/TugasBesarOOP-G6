package main;

import entity.Entity;
import java.awt.Rectangle;
import tile.Tile;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        entity.collisionOn = false;

        Rectangle entityArea = new Rectangle(
            entity.worldX + entity.solidArea.x,
            entity.worldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        switch (entity.direction) {
            case "up":
                entityArea.y -= entity.speed;
                break;
            case "down":
                entityArea.y += entity.speed;
                break;
            case "left":
                entityArea.x -= entity.speed;
                break;
            case "right":
                entityArea.x += entity.speed;
                break;
        }

        int leftCol = entityArea.x / gp.tileSize;
        int rightCol = (entityArea.x + entityArea.width) / gp.tileSize;
        int topRow = entityArea.y / gp.tileSize;
        int bottomRow = (entityArea.y + entityArea.height) / gp.tileSize;

        for (int col = leftCol; col <= rightCol; col++) {
            for (int row = topRow; row <= bottomRow; row++) {
                if (col < 0 || row < 0 || col >= gp.maxWorldCol || row >= gp.maxWorldRow) continue;

                int tileNum = gp.tileM.mapTileNum[gp.currentMap][col][row];
                Tile tile = gp.tileM.tile[tileNum];

                if (tile != null && tile.collision) {
                    Rectangle tileRect = new Rectangle(
                        col * gp.tileSize + tile.solidArea.x,
                        row * gp.tileSize + tile.solidArea.y,
                        tile.solidArea.width,
                        tile.solidArea.height
                    );

                    if (entityArea.intersects(tileRect)) {
                        entity.collisionOn = true;
                    }
                }
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        int originalWorldX = entity.worldX;
        int originalWorldY = entity.worldY;

        switch (entity.direction) {
            case "up": originalWorldY -= entity.speed; break;
            case "down": originalWorldY += entity.speed; break;
            case "left": originalWorldX -= entity.speed; break;
            case "right": originalWorldX += entity.speed; break;
        }

        Rectangle entitySolidAreaSimulated = new Rectangle(
            originalWorldX + entity.solidArea.x,
            originalWorldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        for (int i = 0; i < gp.obj[1].length; i++) {
            if (gp.obj[gp.currentMap][i] != null) {
                if (gp.obj[gp.currentMap][i].collision && gp.obj[gp.currentMap][i].solidAreas != null) { 
                    for (Rectangle objectSolidRect : gp.obj[gp.currentMap][i].solidAreas) {
                        Rectangle objectArea = new Rectangle(
                            gp.obj[gp.currentMap][i].worldX + objectSolidRect.x,
                            gp.obj[gp.currentMap][i].worldY + objectSolidRect.y,
                            objectSolidRect.width,
                            objectSolidRect.height
                        );

                        if (entitySolidAreaSimulated.intersects(objectArea)) {
                            entity.collisionOn = true;
                            if (player) {
                                index = i;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return index;
    }

    public int checkEntity(Entity entity, Entity[][] target) {
        int index = 999;

        Rectangle entityArea = new Rectangle(
            entity.worldX + entity.solidArea.x,
            entity.worldY + entity.solidArea.y,
            entity.solidArea.width,
            entity.solidArea.height
        );

        for (int i = 0; i < target[1].length; i++) {
            if (target[gp.currentMap][i] != null) {
                Rectangle targetArea = new Rectangle(
                    target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x,
                    target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y,
                    target[gp.currentMap][i].solidArea.width,
                    target[gp.currentMap][i].solidArea.height
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