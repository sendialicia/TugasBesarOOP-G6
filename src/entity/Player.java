package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
    
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    // STATUS
    private String name;
    private String gender;
    private int energy;
    private String farmName;
    private String partner;
    private int gold;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 10;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 38;
        solidArea.height = 46;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 3;
        worldY = gp.tileSize * 4;
        speed = 4;
        direction = "down";
        energy = 10000;
        gold = 0;
    }

    
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setEnergy(int energy) { this.energy = energy; }
    public void setFarmName(String farmName) { this.farmName = farmName; }
    public void setPartner(String partner) { this.partner = partner; }
    public void setGold(int gold) { this.gold = gold; }
    
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getEnergy() { return energy; }
    public String getFarmName() { return farmName; }
    public String getPartner() { return partner; }
    public int getGold() { return gold; }

    public void addEnergy(int energy){ this.energy += energy; }

    public void getPlayerImage() {
        up1 = setup("/player/mc_up_left");
        up2 = setup("/player/mc_up_right");
        down1 = setup("/player/mc_down_left");
        down2 = setup("/player/mc_down_right");
        left1 = setup("/player/mc_left");
        left2 = setup("/player/mc_walk_left");
        right1 = setup("/player/mc_right");
        right2 = setup("/player/mc_walk_right");
    }

    public void update() {
        collisionOn = false;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // CHECK TILE COLLISION
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        } else {
            standCounter++;
            if (standCounter > 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if (keyH.enterPressed) {
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            gp.keyH.enterPressed = false;
        }
    }

    public void pickUpObject(int i) {
        if(i != 999) {
            // Your object pickup logic here
        }
    }

    public void interactNPC(int i) {
        if(i != 999) {
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }
        g2.drawImage(image, screenX, screenY, null);

        // FOR DEBUGGING
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}