package entity;

import entity.npc.NPC;
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
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import main.GamePanel;
import main.KeyHandler;
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
        worldX = gp.tileSize * 10;
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "down";
        energy = 100;
        gold = 10000;
        inventory = new Inventory();
        gender = "Female";
    }

    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { 
        this.gender = gender; 
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

    public void getPlayerImage() {
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
            interactNPC(npcIndex);

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
            interactNPC(npcIndex);

            int tileSize = 48;

            int interactX = worldX;
            int interactY = worldY;

            switch (direction) {
                case "up": interactY -= tileSize; break;
                case "down": interactY += tileSize; break;
                case "left": interactX -= tileSize; break;
                case "right": interactX += tileSize; break;
            }
            
            int targetHouseX =  gp.obj[0].worldX + 5 * tileSize;
            int targetHouseY = gp.obj[0].worldY + 7 * tileSize;

            int targetPondX1 =  gp.obj[2].worldX + tileSize;
            int targetPondX2 =  targetPondX1 + tileSize;
            int targetPondY = gp.obj[2].worldY;

            int targetBinX1 = gp.obj[1].worldX;
            int targetBinX2 = targetBinX1 + tileSize;
            int targetBinX3 = targetBinX2 + tileSize;
            int targetBinY = gp.obj[1].worldY + tileSize + tileSize/2;

            int playerFeetX = worldX + tileSize / 2;
            int playerFeetY = worldY + tileSize;

            int toleranceX = 48;
            int toleranceY = 48;

            System.out.println("Feet: " + playerFeetX + "," + playerFeetY);
            System.out.println("Target center: " + (targetBinX1 + tileSize/2) + "," + (targetBinY + tileSize/2));
            System.out.println("Distance: " + Math.abs(playerFeetX - (targetBinX1 + tileSize / 2)) + "," + Math.abs(playerFeetY - (targetBinY + tileSize / 2)));


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

            keyH.enterPressed = false;
        }
    }


    public void pickUpObject(int i) {
        if(i != 999) {}
    }

    public void interactNPC(int i) {
        if(i != 999) {
            if(gp.keyH.enterPressed == true) {
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
    }

    public void interactHouse(){
        gp.gameState = gp.houseInteractState;
    }

    public void interactFishing(){
        gp.gameState = gp.fishingInteractState;

        ItemFactory itemFactory = new ItemFactory();
        itemFactory.loadFish();

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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch(direction) {
            case "up": image = (spriteNum == 1) ? up1 : up2; break;
            case "down": image = (spriteNum == 1) ? down1 : down2; break;
            case "left": image = (spriteNum == 1) ? left1 : left2; break;
            case "right": image = (spriteNum == 1) ? right1 : right2; break;
        }
        
        g2.drawImage(image, screenX, screenY, null);

        // FOR DEBUGGING
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }

    // Actions
    // public void tilling(Tile tile) {
    //     if (tile.isTillable()) {
    //         Equipments hoe = (Equipments) (inventory.get("Hoe"));
    //         hoe.onUse(this);
    //         tile.setDeployedObjectChar('t');
    //         System.out.println("You have tilled the land.");
    //         this.energy -= 5;
    //         gameClock.advanceTime(5);
    //         System.out.println("Time skips five minutes.");
    //     } else {
    //         System.out.println("This land is not tillable.");
    //     }
    // }

    // public void recoverLand(Tile tile) {
    //     if (tile.isTilled()) {
    //         Equipments pickaxe = (Equipments) (inventory.get("Hoe"));
    //         pickaxe.onUse(this);
    //         tile.setDeployedObjectChar('.');
    //         System.out.println("You have recovered the land.");
    //         this.energy -= 5;
    //         gameClock.advanceTime(5);
    //         System.out.println("Time skips five minutes.");
    //     } else {
    //         System.out.println("This land is not recoverable.");
    //     }
    // }

    // public Tile planting(Tile tile) {
    //     if (!tile.isTilled()) {
    //         System.out.println("This land is not tillable or the seed is invalid.");
    //         return tile;
    //     }

    //     List<Seeds> seedsList = new ArrayList<>();
    //     List<Integer> amounts = new ArrayList<>();
    //     for (Items item : inventory.getItems().keySet()) {
    //         if (item instanceof Seeds) {
    //             seedsList.add((Seeds) item);
    //             amounts.add(getItemQuantity(item));
    //         }
    //     }

    //     if (seedsList.isEmpty()) {
    //         System.out.println("You have no seeds to plant.");
    //         return tile;
    //     }

    //     System.out.println("Available seeds:");
    //     for (int i = 0; i < seedsList.size(); i++) {
    //         System.out.println((i + 1) + ". " + seedsList.get(i).getName() + " (x" + amounts.get(i) + ")");
    //     }

    //     System.out.print("Enter the number of the seed you want to plant: ");
    //     int choice;
    //     try {
    //         choice = Integer.parseInt(scanner.nextLine());
    //     } catch (NumberFormatException e) {
    //         System.out.println("Invalid input.");
    //         return tile;
    //     }

    //     if (choice < 1 || choice > seedsList.size()) {
    //         System.out.println("Invalid choice.");
    //         return tile;
    //     }

    //     Seeds selectedSeed = seedsList.get(choice - 1);

    //     tile = new PlantedTile(selectedSeed, gameClock.getDate().getDay());
    //     removeItemFromInventory(selectedSeed, 1);
    //     System.out.println("You have planted " + selectedSeed.getName() + ".");
    //     this.energy -= 5;
    //     gameClock.advanceTime(5);
    //     System.out.println("Time skips five minutes.");
    //     return tile;
    // }

    // public void watering(Tile tile) {
    //     if (tile.isPlanted()){
    //         PlantedTile plantedTile = (PlantedTile) tile;
    //         Equipments wateringCan = (Equipments) inventory.get("Watering Can");
    //         wateringCan.onUse(this);
    //         plantedTile.water(gameClock.getDate().getDay());
    //         this.energy -= 5;
    //         gameClock.advanceTime(5);
    //         System.out.println("Time skips five minutes.");
    //     } else System.out.println("You can't water this tile");
    // }

    // public void harvesting(Tile tile) {
    //     if (tile.isHarvestable()){
    //         PlantedTile plantedTile = (PlantedTile) tile;
    //         if (plantedTile.isReadyToHarvest()){
    //             String seedName = plantedTile.getSeed().getName();
    //             String cropName;

    //             if (seedName != null && seedName.endsWith(" Seeds")) cropName = seedName.substring(0, seedName.length() - " Seeds".length());
    //             else cropName = seedName;

    //             ItemFactory cropsFactory = new ItemFactory();
    //             cropsFactory.loadCrops();

    //             Items cropItem = cropsFactory.get(cropName);
    //             Crops crop = (Crops) cropItem;
    //             inventory.addItem(crop, crop.getHarvestedAmount());

    //             tile = new Tile();
    //             this.energy -= 5;
    //             gameClock.advanceTime(5);
    //             System.out.println("Time skips five minutes.");
    //         } else System.out.println("You can't harvest this tile yet");
    //     } else System.out.println("You can't harvest this tile");
    // }

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
        System.out.println("You slept. Time skipped to 6:00 next day.");
    }

    public void cooking() {}

    public void fishing() {
        gameClock.stopClock();
        this.energy -= 5;
        gameClock.advanceTime(15);
    }

    public void proposing(NPC npc) {
        if (npc.getHeartPoints() < 150){
            System.out.println("You are rejected. You need to increase your heart points.");
            this.energy -= 20;
        } else {
            System.out.println("You are now engaged to " + npc.getName() + ".");
            this.partner = npc;
            this.partner.setRelationshipStatus("Fiance");
            this.energy -= 10;
        }
        gameClock.advanceTime(60);
        System.out.println("Time skips one hour.");
    }

    public void marry(NPC partner) {
        if (partner.getRelationshipStatus().equals("Married")) System.out.println("You are already married to " + partner.getName() + ".");
        else {
            GameClockSnapshot lastProposalTime = partner.getChangeLog().get("Fiance");
            if (lastProposalTime != null) {
                if (gameClock.getMinutesSince(lastProposalTime) < 1440) System.out.println("You cannot be married so soon.");
                else {
                    partner.setRelationshipStatus("Spouse");
                    System.out.println("You are now married to " + partner.getName() + ".");
                    partner.setRelationshipStatus("Spouse");
                    this.energy -= 80;

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

                    System.out.println("Time skips to 22:00.");
                }
            } else System.out.println("You must make this NPC a fiance first!");
        }
    }

    public void watching() {
        System.out.println("You are now watching television.");
        this.energy -= 5;
        gameClock.advanceTime(15);
        System.out.println("Time skips fifteen minutes.");
    }

    // public void visiting() {
    //     System.out.println("----" + "VISITING" + "----");
    //     System.out.println("Where do you want to visit?");

    //     worldMap.showLocations();

    //     System.out.print("Enter your choice : ");

    //     int choice;
    //     try {
    //         choice = Integer.parseInt(scanner.nextLine());
    //     } catch (NumberFormatException e) {
    //         System.out.println("Invalid number. Going back to farm...");
            
    //         return;
    //     }

    //     if (choice >= 1 && choice <= 9) worldMap.visitLocation(choice, this);
    //     else {
    //         System.out.println("Invalid number. Going back to farm...");
    //         return;  
    //     }

    //     this.energy -= 10;
    //     gameClock.advanceTime(15);
    //     System.out.println("Time skips fifteen minutes.");
    // }

    public void chatting(NPC npc) {
        npc.setHeartPoints(npc.getHeartPoints() + 10);
        this.energy -= 10;
        System.out.println("You chatted with " + npc.getName() + ". (+10 heart points)");
        gameClock.advanceTime(10);
        System.out.println("Time skips ten minutes.");
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

    public void openInventory() {
        System.out.println("Your Inventory:");
        System.out.println("+----------------------+--------+");
        System.out.println("| Item                 | Amount |");
        System.out.println("+----------------------+--------+");

        List<Class<?>> subclasses = Arrays.asList(Equipments.class, Seeds.class, Crops.class, Fish.class, Food.class, Miscellaneous.class);

        for (Class<?> subclass : subclasses) {
            boolean hasSubclassItems = false;
            for (Items item : inventory.getItems().keySet()) {
                if (subclass.isInstance(item)) {
                    hasSubclassItems = true;
                    break;
                }
            }

            if (!hasSubclassItems) continue;
            String subclassName = subclass.getSimpleName();
            System.out.printf("| %-20s |   --   |\n", subclassName + ":");
            for (Items item : inventory.getItems().keySet()) {
                if (subclass.isInstance(item)) {
                    if (item instanceof Equipments) {
                        System.out.printf("|   %-18s |   --   |\n", item.getName());
                    } else {
                        System.out.printf("|   %-18s | %6d |\n", item.getName(), getItemQuantity(item));
                    }
                }
            }
        }
        System.out.println("+----------------------+--------+");
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