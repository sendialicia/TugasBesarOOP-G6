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
    String name;
    String farmName;
    String gender;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 0;
        solidArea.y = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 48;
        solidArea.height = 48;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void getPlayerImage() {
        up1 = setup("player", "mc_up_left");
        up2 = setup("player", "mc_up_right");
        down1 = setup("player", "mc_down_left");
        down2 = setup("player", "mc_down_right");
        left1 = setup("player", "mc_left");
        left2 = setup("player", "mc_walk_left");
        right1 = setup("player", "mc_right");
        right2 = setup("player", "mc_walk_right");
    }

    public void update() {

        if(moving == false) {
            if(keyH.upPressed == true || keyH.downPressed == true || 
               keyH.leftPressed == true || keyH.rightPressed == true) {
                 
                if(keyH.upPressed == true && keyH.enterPressed == false) {
                    direction = "up";
                } else if(keyH.downPressed == true && keyH.enterPressed == false) {
                    direction = "down";
                } else if(keyH.leftPressed == true && keyH.enterPressed == false) {
                    direction = "left";
                } else if(keyH.rightPressed == true && keyH.enterPressed == false) {
                    direction = "right";
                }

                moving = true;

                // CHECK TILE COLLISION
                collisionOn = false;
                gp.cChecker.checkTile(this);

                // CHECK OBJECT COLLISION
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);

                // CHECK NPC COLLISION
                int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

            } else {
                standCounter++;

                if(standCounter > 20) {
                    spriteNum = 1;
                    standCounter = 0; 
                }
            }
        }

        if (keyH.enterPressed) {
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
        }

        if(moving == true) {

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if(collisionOn == false && keyH.enterPressed == false) {
                switch(direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            gp.keyH.enterPressed = false;
    
            spriteCounter++;
            if(spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if(spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

            pixelCounter += speed;

            if(pixelCounter == 48) {
                moving = false;
                pixelCounter = 0;
            }
        }     
    }

    public void pickUpObject(int i) {

        if(i != 999) {
                
        }
    }

    public void interactNPC(int i) {

        if(i != 999) {
            
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
    }

    public void draw(Graphics2D g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;
        
        switch(direction) {
        case "up":
            if(spriteNum == 1) {
                image = up1;
            }
            if(spriteNum == 2) {
                image = up2;
            }
            break;
        case "down":
            if(spriteNum == 1) {
                image = down1;
            }
            if(spriteNum == 2) {
                image = down2;
            }
            break;
        case "left":
            if(spriteNum == 1) {
                image = left1;
            }
            if(spriteNum == 2) {
                image = left2;
            }
            break;
        case "right":
            if(spriteNum == 1) {
                image = right1;
            }
            if(spriteNum == 2) {
                image = right2;
            }
            break;
        }
        g2.drawImage(image, screenX, screenY, null);
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }
}
