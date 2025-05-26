package org.example.legacy;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

class Player extends Entity {
    private String name;
    private String gender;
    private int energy = 0; //{Max = 100, Min = -20}
    private String farmName = null;
    private NPC partner = null;
    private int gold = 0; //{Min = 0}
    private Inventory inventory = new Inventory();
    private Point playerLoc = new Point(100, 100);

    GamePanel gamePanel;
    KeyboardHandler keyHandler;

    public final int screenX;
    public final int screenY;

    // Constructor
    public Player(GamePanel gp, KeyboardHandler keyboardHandler, String name, String gender){
        this.gamePanel = gp;
        this.keyHandler = keyboardHandler;
        this.name = name;
        this.gender = gender;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2); 
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2); 

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        this.energy = 100; 
        this.farmName = "My Farm"; 
        this.partner = null; 
        this.gold = 0; 
        this.inventory.clear(); 

        this.playerLoc.setLocation(worldX, worldY); // Starting location
        this.worldX = gamePanel.tileSize * 23; // Set x position
        this.worldY = gamePanel.tileSize * 21; // Set y position
        this.speed = 4; // Default
        this.direction = "down"; 


        this.name = "Player"; 
        this.gender = "Boy"; 
    }

    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/images/player/boy_right_2.png"));
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
                worldY -= speed; 
            }

            if (keyHandler.downPressed) {
                direction = "down";
                worldY += speed; 
            }

            if (keyHandler.leftPressed) {
                direction = "left";
                worldX -= speed; 
            }

            if (keyHandler.rightPressed) {
                direction = "right";
                worldX += speed; 
            }

            spriteCounter++;
            if (spriteCounter > 8) { 
                if (spriteNum == 1) {
                    spriteNum = 2; 
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0; 
            }
        }
        
        if (energy > 0) {
            energy -= 1; // Decrease energy with each update
        }
    }

    public void draw(Graphics2D g2) {
        
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                } else if (spriteNum == 2) {
                    image = up2;
                }
                break;

            case "down":
                if (spriteNum == 1) {
                    image = down1;
                } else if (spriteNum == 2) {
                    image = down2;
                }
                break;

            case "left":   
                if (spriteNum == 1) {
                    image = left1;
                } else if (spriteNum == 2) {
                    image = left2;
                }
                break;
                
            case "right":   
                if (spriteNum == 1) {
                    image = right1;
                } else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null); // Draw the player at its current position
    }

    // Getters
    public String getName(){
        return name;
    }

    public String getGender(){
        return gender;
    }

    public int getEnergy(){
        return energy;
    }

    public String getFarmName(){
        return farmName;
    }

    public NPC getPartner(){
        return partner;
    }

    public int getGold(){
        return gold;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public Point getLocation(){
        return playerLoc;
    }

    // Setters
    public void setName(String newName){
        this.name = newName;
    }

    public void setGender(String newGender){
        this.gender = newGender;
    }

    public void setEnergy(int newEnergy){
        if (newEnergy > 100){
            this.energy = 100;
        } 
        else if (newEnergy < -20){
            this.energy = -20;
        } 
        else{
            this.energy = newEnergy;
        }
    }

    public void setFarmName(String newFarmName){
        this.farmName = newFarmName;
    }

    public void setPartner(NPC partner){
        this.partner = partner;
    }

    public void setGold(int newGold){
        if (newGold >= 0){
            this.gold = newGold;
        }
    }

    public void setInventory(Inventory newInventory){
        this.inventory = newInventory;
    }

    public void setLocation(Point newLocation){
        this.playerLoc = newLocation;
    }
}