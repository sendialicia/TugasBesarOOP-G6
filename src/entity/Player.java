package entity;

import entity.npc.NPC;
import farmTile.HarvestableTile;
import farmTile.PlantedTile;
import items.Edible;
import items.Inventory;
import items.ItemFactory;
import items.Items;
import items.crops.Crops;
import items.equipments.Equipments;
import items.fish.Fish;
import items.food.Food;
import items.miscellaneous.Miscellaneous;
import items.seeds.Seeds;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;
import time.GameClock;
import time.GameClockSnapshot;

public class Player extends Entity{
    
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;
    public String fishingLocation;

    // STATUS
    private String name;
    private String gender;
    private int energy;
    private String farmName;
    private NPC partner;
    private int gold;
    private Inventory inventory;
    
    private int hasFished = 0;
    private int hasSlept = -1;

    private static final int MAX_ENERGY = 100;
    private static final int MIN_ENERGY = -20;

    private final GameClock gameClock = GameClock.getInstance();

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
        worldX = gp.tileSize * 26;
        worldY = gp.tileSize * 15;

        speed = 4;
        direction = "down";
        energy = 100;
        gold = 10000;
        inventory = new Inventory(true);
        gender = "Female";
    }

    public void teleportHome() {
        gp.currentMap = 0;
        gp.maxWorldCol = 51;
        gp.maxWorldRow = 51;

        gp.tileM = new TileManager(gp, gp.maxWorldCol, gp.maxWorldRow);
        gp.tileM.loadMap("/maps/farm.txt", 0);
        gp.gameState = gp.playState;

        worldX = gp.obj[0][0].worldX + 38;
        worldY = gp.obj[0][0].worldY + 7 * 48 - 8;
        direction = "down";
    }


    public void teleport(){
        if (gp.currentMap == gp.playState){
            worldX = gp.tileSize * 31;
            worldY = gp.tileSize * 21;
        }
        else{
            worldX =  26 * gp.tileSize;
            worldY =  15 * gp.tileSize;
        }
        direction = "down";
    }

    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { 
        this.gender = gender; 
        if (gender.equals("Female")) {
            solidArea.x = 15;
            solidArea.y = 24;
        } 
        getPlayerImage();
    }
    public void setFarmName(String farmName) { this.farmName = farmName; }
    public void setPartner(NPC partner) { this.partner = partner; }
    public void setGold(int gold) { this.gold = Math.max(0, gold); }
    public void setPosition(int x, int y) { this.worldX = x; this.worldY = y; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public void setEnergy(int energy) {
        if (energy > MAX_ENERGY) this.energy = MAX_ENERGY;
        else if (energy < MIN_ENERGY) this.energy = MIN_ENERGY;
        else this.energy = energy;
    }

    public void addEnergy(int energy) { setEnergy(this.energy + energy); }
    public void subtractEnergy(int energy) { setEnergy(this.energy - energy); }
    
    public String getName() { return name; }
    public String getGender() { 
        return gender;
        
    }
    public int getEnergy() { return energy; }
    public String getFarmName() { return farmName; }
    public NPC getPartner() { return partner; }
    public int getGold() { return gold; }
    public Inventory getInventory() { return inventory; }
    public String getFavItem() { return inventory.getMostItem(); }

    public void addItemToInventory(Items item, int quantity) { inventory.addItem(item, quantity); }
    public void removeItemFromInventory(Items item, int quantity) { inventory.removeItem(item, quantity); }
    public int getItemQuantity(Items item) { return inventory.getItemQuantity(item); }
    public void clearInventory() { inventory.clear(); }
    public boolean isInventoryEmpty() { return inventory.isEmpty(); }
    public Items getItemFromInventory(String itemName) { return inventory.get(itemName); }
    public void removeGold(int amount) {
        setGold(gold - amount);
    }
    public void getPlayerImage() {

        if ("Female".equals(getGender())) this.useDefault = false; 
        else this.useDefault = true;

        up1 = setup("/player/" + getGender() + "/mc_up_left");
        up2 = setup("/player/" + getGender() + "/mc_up_right");
        down1 = setup("/player/" + getGender() + "/mc_down_left");
        down2 = setup("/player/" + getGender() + "/mc_down_right");
        left1 = setup("/player/" + getGender() + "/mc_left");
        left2 = setup("/player/" + getGender() + "/mc_walk_left");
        right1 = setup("/player/" + getGender() + "/mc_right");
        right2 = setup("/player/" + getGender() + "/mc_walk_right");
    }

    public BufferedImage def_avatar = setup("/player/Male/mc_down_left");

    public BufferedImage def_avatar_female = setup("/player/Female/mc_down_left");

    public void update() {
        if (energy <= -20){
            sleeping();
            teleportHome();
        }
        collisionOn = false;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            // CHECK TILE COLLISION
            gp.cChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // CHECK NPC COLLISION
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            chatNPC(npcIndex);

            if (!collisionOn && !keyH.enterPressed) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
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
            chatNPC(npcIndex);

            int tileSize = 48;

            int interactX = worldX;
            int interactY = worldY;

            switch (direction) {
                case "up": interactY -= tileSize; break;
                case "down": interactY += tileSize; break;
                case "left": interactX -= tileSize; break;
                case "right": interactX += tileSize; break;
            }

            int playerFeetX = worldX + tileSize / 2;
            int playerFeetY = worldY + tileSize;

            int toleranceX = 48;
            int toleranceY = 48;
            
            if (gp.currentMap == 0){
                int moveMapToWorldX1 =  25 * gp.tileSize;
                int moveMapToWorldX2 =  26 * gp.tileSize;
                int moveMapToWorldX3 = 27 * gp.tileSize;
                int moveMapToWorldY =  6 * gp.tileSize;

                int targetHouseX =  gp.obj[0][0].worldX + tileSize;
                int targetHouseY = gp.obj[0][0].worldY + 7 * tileSize;

                int targetPondX1 =  gp.obj[0][2].worldX + tileSize;
                int targetPondX2 =  targetPondX1 + tileSize;
                int targetPondY = gp.obj[0][2].worldY;

                int targetBinX1 = gp.obj[0][1].worldX;
                int targetBinX2 = targetBinX1 + tileSize;
                int targetBinX3 = targetBinX2 + tileSize;
                int targetBinY = gp.obj[0][1].worldY + tileSize + tileSize/2;
                
                if (Math.abs(playerFeetX - (targetHouseX + tileSize / 2)) <= toleranceX &&
                    Math.abs(playerFeetY - (targetHouseY + tileSize / 2)) <= toleranceY && 
                    keyH.enterPressed && direction.equals("up")) 
                    interactHouse();

                if ((Math.abs(playerFeetX - (targetPondX1 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetPondX2 + tileSize / 2)) <= toleranceX) && 
                    Math.abs(playerFeetY - (targetPondY + tileSize / 2)) <= toleranceY && 
                    keyH.enterPressed && direction.equals("down")) {
                    fishingLocation = "Pond";
                    interactFishing();
                }

                if ((Math.abs(playerFeetX - (targetBinX1 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetBinX2 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetBinX3 + tileSize / 2)) <= toleranceX) && 
                    Math.abs(playerFeetY - (targetBinY + tileSize / 2)) <= toleranceY && 
                    keyH.enterPressed && direction.equals("up")) {
                    interactBin();
                }

                if ((Math.abs(playerFeetX - (moveMapToWorldX1 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (moveMapToWorldX2 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (moveMapToWorldX3 + tileSize / 2)) <= toleranceX) && 
                    Math.abs(playerFeetY - (moveMapToWorldY + tileSize / 2)) <= toleranceY) {
                    interactMoveMap();
                }
            } 
            else {
                int targetXHouse1 = gp.obj[1][4].worldX + 3 * gp.tileSize;
                int targetYHouse1 = gp.obj[1][4].worldY + 11 * gp.tileSize;

                int targetXHouse2 = gp.obj[1][5].worldX + 5 * gp.tileSize;
                int target2House2 = gp.obj[1][5].worldY + 9 * gp.tileSize;

                int targetXHouse3 = gp.obj[1][6].worldX + 3 * gp.tileSize;
                int targetYHouse3 = gp.obj[1][6].worldY + 7 * gp.tileSize;

                int targetXHouse4 = gp.obj[1][7].worldX + 2 * gp.tileSize;
                int targetYHouse4 = gp.obj[1][7].worldY + 8 * gp.tileSize;
                
                int targetXHouse5 = gp.obj[1][8].worldX + 3 * gp.tileSize;
                int targetYHouse5 = gp.obj[1][8].worldY + 8 * gp.tileSize;

                int targetXShop1 = gp.obj[1][0].worldX + 5 * gp.tileSize;
                int targetXShop2 = gp.obj[1][0].worldX + 6 * gp.tileSize;
                int targetYShop = gp.obj[1][0].worldY + 9 * gp.tileSize;

                int targetForestRiverX1 =  2092 + tileSize;
                int targetForestRiverX2 =  targetForestRiverX1 + tileSize;
                int targetForestRiverX3 =  targetForestRiverX2 + tileSize;
                int targetForestRiverX4 =  targetForestRiverX3 + tileSize;
                int targetForestRiverX5 =  targetForestRiverX4 + tileSize;
                int targetForestRiverY = 1192;

                int targetMountainLakeX = 2068 + tileSize;
                int targetMountainLakeY =  2356 + tileSize;
                int targetMountainLakeY2 = 2300 + tileSize;
                int targetMountainLakeY3 = 2250 + tileSize;

                int targetOceanX1 =  640 + tileSize;
                int targetOceanX2 =  788 + tileSize;
                int targetOceanX3 =  548 + tileSize;
                int targetOceanY = 2776;


                if ((Math.abs(playerFeetX - (targetXHouse1 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetYHouse1 + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactHouseNPC(0);
                }

                if ((Math.abs(playerFeetX - (targetXHouse2 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (target2House2 + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactHouseNPC(1);
                }

                if ((Math.abs(playerFeetX - (targetXHouse3 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetYHouse3 + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactHouseNPC(2);
                }

                if ((Math.abs(playerFeetX - (targetXHouse4 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetYHouse4 + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactHouseNPC(3);
                }

                if ((Math.abs(playerFeetX - (targetXHouse5 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetYHouse5 + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactHouseNPC(4);
                }

                if ((Math.abs(playerFeetX - (targetXShop1 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetXShop2 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetYShop + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactStore();
                }

                if ((Math.abs(playerFeetX - (targetForestRiverX1 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetForestRiverX2 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetForestRiverX3 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetForestRiverX4 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetForestRiverY + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactFishing();
                }

                if ((Math.abs(playerFeetX - (targetMountainLakeY + tileSize / 2)) <= toleranceY ||
                    Math.abs(playerFeetX - (targetMountainLakeY2 + tileSize / 2)) <= toleranceY ||
                    Math.abs(playerFeetX - (targetMountainLakeY3 + tileSize / 2)) <= toleranceY) &&
                    Math.abs(playerFeetY - (targetMountainLakeX + tileSize / 2)) <= toleranceX &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactFishing();
                }
                if ((Math.abs(playerFeetX - (targetOceanX1 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetOceanX2 + tileSize / 2)) <= toleranceX ||
                    Math.abs(playerFeetX - (targetOceanX3 + tileSize / 2)) <= toleranceX) &&
                    Math.abs(playerFeetY - (targetOceanY + tileSize / 2)) <= toleranceY &&
                    keyH.enterPressed && direction.equals("up")) {
                    interactFishing();
                }
            }
            keyH.enterPressed = false;
        }

        else if (keyH.iPressed){
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            if (npcIndex != 999){
                gp.interactedNPC = npcIndex;
                gp.gameState = gp.interactNPCState;
            }
            keyH.iPressed = false;
        }
    }

    public void pickUpObject(int i) { if(i != 999) {} }

    public void chatNPC(int i) {
        if(i != 999) {
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[gp.currentMap][i].speak();
                gp.npc[gp.currentMap][i].addHeartPoints(10);
                chatting();
            }
        }
    }

    public void interactHouse(){ gp.gameState = gp.houseInteractState; }

    public void interactHouseNPC(int i){ 
        gp.gameState = gp.houseNPCInteractState;
        gp.ui.visitedNPC = i;
    }

    public void interactStore(){ gp.gameState = gp.storeInteractState; }

    public void interactFishing(){
        gp.gameState = gp.fishingInteractState;

        ItemFactory.loadFish();

        List<Fish> availableFish = new ArrayList<>();

        for (Items item : ItemFactory.getAllItems().values()) {
            if (item instanceof Fish fish) {
                if (fish.isAvailable(gameClock.getDate().getSeasonString(), gameClock.getWeather().getWeatherToday(), fishingLocation, gameClock.getTime().getHour())) {
                    availableFish.add(fish);
                }
            }
        }

        if (availableFish.isEmpty()) return;

        Random random = new Random();
        int fishIndex = random.nextInt(availableFish.size());
        Fish fish = availableFish.get(fishIndex);

        int range;
        if (fish.getRarity().equals("Common")) range = 10;
        else if (fish.getRarity().equals("Regular")) range = 100;
        else range = 500;

        int number = random.nextInt(range) + 1;
        gp.fished = fish;
        gp.luckyNumber = number;

        fishing();
    }

    public void interactBin(){
        gp.gameState = gp.binInteractState;
    }

    public void interactMoveMap() { gp.gameState = gp.moveMapState; }
    

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }
        
        if (useDefault) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        } else {
            g2.drawImage(image, screenX, screenY, gp.tileSize + 20, gp.tileSize + 20, null);
        }
    }

    public void tilling() {
        this.energy -= 5;
        gameClock.advanceTime(5);
    }

    public void recoverLand() {
        this.energy -= 5;
        gameClock.advanceTime(5);
    }

    public void planting() {
        this.energy -= 5;
        gameClock.advanceTime(5);
    }

    public void watering(PlantedTile tile){
        this.energy -= 5;
        gameClock.advanceTime(5);
    }

    public void harvesting(HarvestableTile tile){
        this.energy -= 5;
        gameClock.advanceTime(5);
    }

    public void eating(Items item) {
        if (item != null && getItemQuantity(item) > 0) {
            if (item instanceof Edible edibleItem) {
                edibleItem.onEat(this);
                gameClock.advanceTime(5);
                System.out.println("Time skips five minutes.");
                removeItemFromInventory(item, 1);
            } else System.out.println("This item is not edible.");
        } else System.out.println("You do not have this item in your inventory.");
    }

    public void sleeping() {
        gp.gameState = gp.sleepingState;
        synchronized (gameClock) {
            int currentHour = gameClock.getTime().getHour();
            int currentMinute = gameClock.getTime().getMinute();

            int minutesUntilMidnight = (24 - currentHour) * 60 - currentMinute;
            int minutesAfterMidnightToSix = 6 * 60;

            int totalMinutesToSkip = minutesUntilMidnight + minutesAfterMidnightToSix;

            gameClock.advanceTime(totalMinutesToSkip);
        }

        if (energy == 0) energy = 10;
        else if (energy < MAX_ENERGY / 10) energy = MAX_ENERGY / 2;
        else if (energy > MAX_ENERGY / 10) energy = MAX_ENERGY;
    }

    public void cooking() {}

    public void fishing() {
        gameClock.stopClock();
        this.energy -= 5;
        gameClock.advanceTime(15);
    }

    public void proposing() {
        if (partner == null){
            if (gp.npc[gp.currentMap][gp.interactedNPC].getHeartPoints() < 150){
                gp.gameState = gp.rejectedState;
                energy -= 20;
            }
            else {
                partner = gp.npc[gp.currentMap][gp.interactedNPC];
                partner.setRelationshipStatus("Fiance");
                energy -= 10;
                gp.gameState = gp.acceptedState;
            }
            gameClock.advanceTime(60);
        }
        else if (partner.equals(gp.npc[gp.currentMap][gp.interactedNPC])) gp.gameState = gp.yourPartnerState;
        else gp.gameState = gp.havePartnerState;
    }

    public void marry() {
        if (partner == null) gp.gameState = gp.rejectedState;
        else if (!partner.equals(gp.npc[gp.currentMap][gp.interactedNPC])) gp.gameState = gp.havePartnerState;
        else if (partner.getRelationshipStatus().equals("Married")) gp.gameState = gp.yourPartnerState;
        else {
            GameClockSnapshot lastProposalTime = partner.getChangeLog().get("Fiance");
            System.out.println("Minutes since proposal: " + gameClock.getMinutesSince(lastProposalTime));
            System.out.println("Proposal snapshot: " + lastProposalTime);
            System.out.println("Current time: " + gameClock.getTime());

            if (lastProposalTime != null) {
                if (gameClock.getMinutesSince(lastProposalTime) < 1440) gp.gameState = gp.tooSoonState;
                else {
                    partner.setRelationshipStatus("Spouse");
                    this.energy -= 80;
                    gp.gameState = gp.acceptedState;

                    synchronized (gameClock) {
                        int currentHour = gameClock.getTime().getHour();
                        int currentMinute = gameClock.getTime().getMinute();

                        int targetHour = 22;
                        int totalCurrentMinutes = currentHour * 60 + currentMinute;
                        int totalTargetMinutes = targetHour * 60;

                        int minutesToAdvance;
                        if (totalCurrentMinutes < totalTargetMinutes) minutesToAdvance = totalTargetMinutes - totalCurrentMinutes;
                        else minutesToAdvance = (24 * 60 - totalCurrentMinutes) + totalTargetMinutes;

                        gameClock.advanceTime(minutesToAdvance);
                    }

                }
            }
        }
    }

    public void watching() {
        this.energy -= 5;
        gameClock.advanceTime(15);
    }

    public void visiting() {
        this.energy -= 10;
        gameClock.advanceTime(15);
    }

    public void chatting() {
        this.energy -= 10;
        gameClock.advanceTime(10);
    }

    // public void gifting(NPC npc) {
    //     List<Items> giftableItems = new ArrayList<>();
    //     for (Items item : inventory.getItems().keySet()) {
    //         if (!(item instanceof Equipments)) {
    //             giftableItems.add(item);
    //         }
    //     }

    //     if (giftableItems.isEmpty()) {
    //         System.out.println("You have no giftable items in your inventory.");
    //         return;
    //     }

    //     System.out.println("Choose an item to gift to " + npc.getName() + ":");
    //     for (int i = 0; i < giftableItems.size(); i++) {
    //         Items item = giftableItems.get(i);
    //         System.out.println((i + 1) + ". " + item.getName() + " (x" + getItemQuantity(item) + ")");
    //     }
    //     System.out.print("Enter the number of the item: ");
    //     int choice;
    //     try {
    //         choice = Integer.parseInt(scanner.nextLine());
    //     } catch (NumberFormatException e) {
    //         System.out.println("Invalid input.");
    //         return;
    //     }

    //     if (choice < 1 || choice > giftableItems.size()) {
    //         System.out.println("Invalid choice.");
    //         return;
    //     }

    //     Items item = giftableItems.get(choice - 1);
    //     int maxAmount = getItemQuantity(item);
    //     System.out.println("You have " + maxAmount + " of this item. Enter the amount you want to gift:");
    //     int amount;
    //     try {
    //         amount = Integer.parseInt(scanner.nextLine());
    //     } catch (NumberFormatException e) {
    //         System.out.println("Invalid amount.");
    //         return;
    //     }

    //     if (amount <= 0 || amount > maxAmount) {
    //         System.out.println("Invalid amount.");
    //         return;
    //     }

    //     removeItemFromInventory(item, amount);

    //     int heartPointsChange = 0;
    //     String message = npc.getName() + " felt neutral about your gift.";
    //     if (Arrays.asList(npc.getLovedItems()).contains(item)) {
    //         heartPointsChange = 25 * amount;
    //         message = npc.getName() + " loved your gift! (+" + (25 * amount) + " heart points)";
    //     } else if (Arrays.asList(npc.getLikedItems()).contains(item)) {
    //         heartPointsChange = 20 * amount;
    //         message = npc.getName() + " liked your gift! (+" + (20 * amount) + " heart points)";
    //     } else if (Arrays.asList(npc.getHatedItems()).contains(item)) {
    //         heartPointsChange = -25 * amount;
    //         message = npc.getName() + " hated your gift! (" + (-25 * amount) + " heart points)";
    //     }
    //     npc.addHeartPoints(heartPointsChange);
    //     System.out.println(message);

    //     this.energy -= 5 * amount;
    //     gameClock.advanceTime(10 * amount);
    //     System.out.println("Time skips " + (10 * amount) + " minutes.");
    // }

    public void moving(int x, int y) {
        setPosition(x, y);
    }

    public void showTime(GameClock gameClock) {
        System.out.println("Current time: " + gameClock.getFormattedTime());
        System.out.println("Current date: " + gameClock.getFormattedDate());
        System.out.println("Current weather: " + gameClock.getFormattedWeather());
    }

    public void showPosition() { 
        System.out.println("You are at " + worldX + ", " + worldY); 
    }

    // public void selling(OBJ_Bin shippingBin, boolean isAround) {
    //     if (isAround){
    //         shippingBin.interact(this);    
    //     }
    //     System.out.println("You must be near Shipping Bin to perform this action!");

    
    // }
}