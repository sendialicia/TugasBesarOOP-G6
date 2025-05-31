package main;

import items.Inventory;
import items.ItemFactory;
import farmTile.PlantedTile;
import farmTile.TileLocation;
import items.Edible;
import items.Items;
import items.crops.Crops;
import items.fish.Fish;
import items.food.Food;
import items.seeds.Seeds;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import time.GameClock;


public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica, purisaB;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public StringBuilder playerName = new StringBuilder();
    public StringBuilder farmName = new StringBuilder();
    public String currentDialogue;
    public int commandNum = 0;
    public BufferedImage titleBackground;
    public int slotInventoryCol = 0;
    public int slotInventoryRow = 0;
    public int slotShipBinCol = 0;
    public int slotShipBinRow = 0;

    public int fishingAttempts = 1;
    public int guess = 0;
    public String fishingWarning = null;
    public StringBuilder guessString = new StringBuilder();
    Items selectedItem = null;
    Seeds selectedSeed = null;
    Edible edible = null;
    int sellAmount = 0;
    int buyAmount = 0;
    int tempWorldX, tempWorldY;
    TileLocation tempTileLocation = null;

    public int visitedNPC = 0;


    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            titleBackground = ImageIO.read(getClass().getResourceAsStream("/backgrounds/Title Screen BG.png"));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        
        this.g2 = g2;

        g2.setFont(maruMonica);
        // g2.setFont(purisaB);
        // g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // NAME INPUT STATE
        if (gp.gameState == gp.nameInputState){
            drawInputNameScreen();
        }

        // GENDER INPUT STATE
        if (gp.gameState == gp.genderInputState){
            drawInputGenderScreen();
        }

        // FARM NAME INPUT STATE
        if (gp.gameState == gp.farmNameInputState){
            drawFarmNameScreen();
        }

        // PLAY STATE
        if(gp.gameState == gp.playState || gp.gameState == gp.worldMapState) {
            drawGameTime();
            // Do playState stuff later
        } 
        // PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
        }

        // VIEW ATTRIBUTE STATE
        if(gp.gameState == gp.viewAttributeState) {
            drawAttributeScreen();
            drawPlayerInventory();
        }

        // HOUSE INTERACT STATE
        if (gp.gameState == gp.houseInteractState){
            drawHouseScreen();
        }

        if (gp.gameState == gp.fishingInteractState) drawFishingScreen();
        if (gp.gameState == gp.fishingSucceeded) drawSucceededScreen();
        if (gp.gameState == gp.fishingFailed) drawFailedScreen();

        if (gp.gameState == gp.binInteractState) drawBinScreen();
        
        if (gp.gameState == gp.watchingState) drawWatchScreen();
        if (gp.gameState == gp.binShopState) drawBinShopScreen();
        if (gp.gameState == gp.binAmountState) drawBinAmountScreen();

        if (gp.gameState == gp.plantSeedState) drawPlantSeedScreen();


        if(gp.gameState == gp.moveMapState) drawMoveMapScreen();

        if (gp.gameState == gp.sleepingState) drawSleepScreen();

        if (gp.gameState == gp.interactNPCState) drawNPCScreen();
        if (gp.gameState == gp.rejectedState) drawRejectedScreen();
        if (gp.gameState == gp.tooSoonState) drawTooSoonScreen();
        if (gp.gameState == gp.havePartnerState) drawHavePartnerScreen();
        if (gp.gameState == gp.yourPartnerState) drawYourPartnerScreen();
        if (gp.gameState == gp.acceptedState) drawAcceptedScreen();

        if (gp.gameState == gp.houseNPCInteractState) drawHouseNPCScreen();
        if (gp.gameState == gp.storeInteractState) drawStoreScreen();
        if (gp.gameState == gp.storeShopState) drawStoreShopScreen();
        if (gp.gameState == gp.storeAmountState) drawStoreAmountScreen();

        if (gp.gameState == gp.binFailed) drawBinFailed();
        if (gp.gameState == gp.eatingState) drawEatingScreen();
    }

    public void drawTitleScreen() {

        g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "STECU VALLEY";
        int x = getXforCenteredText(text);
        int y = gp.tileSize*3;

        // SHADOW
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);

        // MAIN COLOR
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // CHARACTER IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.def_avatar, x + 100, y, gp.tileSize*2, gp.tileSize*2, null);
        g2.drawImage(gp.player.def_avatar_female, x - 150, y - 20, gp.tileSize*2 + 30, gp.tileSize*2 + 30, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(195, 335, 350, 200, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(200, 340, 340, 190, 25, 25);

        text = "NEW GAME";
        x = getXforCenteredText(text);
        int x2 = getXforCenteredText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize*2;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x2-gp.tileSize, y - 10);
        }
    }

    public void drawFarmNameScreen() {
        g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setColor(new Color(0,0,0, 170));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "STECU VALLEY";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // INPUT NAME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        text = "Enter your farm name:";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);

        String farmNameDisplay = farmName.toString() + (System.currentTimeMillis() / 500 % 2 == 0 ? "_" : ""); // Blinking cursor
        x = getXforCenteredText(farmNameDisplay);
        y += gp.tileSize + 10;
        g2.drawString(farmNameDisplay, x, y);
    }

    public void drawInputNameScreen() {

        g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setColor(new Color(0,0,0, 170));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "STECU VALLEY";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // INPUT NAME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        text = "Enter your name:";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);

        String nameDisplay = playerName.toString() + (System.currentTimeMillis() / 500 % 2 == 0 ? "_" : ""); // Blinking cursor
        x = getXforCenteredText(nameDisplay);
        y += gp.tileSize + 10;
        g2.drawString(nameDisplay, x, y);

        text = "Choose gender:    Male    /   Female";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
    }

    public void drawInputGenderScreen() {

        g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);

        g2.setColor(new Color(0,0,0, 170));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "STECU VALLEY";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // INPUT NAME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        text = "Enter your name:";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);

        String nameDisplay = playerName.toString();
        x = getXforCenteredText(nameDisplay);
        y += gp.tileSize + 10;
        g2.drawString(nameDisplay, x, y);

        // INPUT GENDER
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 48F));
        text = "Choose gender:    Male    /   Female";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        g2.setStroke(new BasicStroke(3));
        if(commandNum == 0) {
            drawPartialBorderText(g2, text, "Male", x, y, 15, Color.WHITE, 
                                  Color.WHITE, new Color(1, 137, 180, 150));
            g2.drawImage(gp.player.def_avatar, gp.screenWidth/2 - gp.tileSize, y, 
                        gp.tileSize, gp.tileSize, null);
        } else if(commandNum == 1) {
            drawPartialBorderText(g2, text, "Female", x, y, 15, Color.WHITE, 
                                  Color.WHITE, new Color(242, 1, 108, 150));
            g2.drawImage(gp.player.def_avatar_female, gp.screenWidth - gp.tileSize - 35, y - 10, 
                        gp.tileSize+20, gp.tileSize+20, null);
        }
    }

    public void drawPauseScreen() {
        g2.setColor(new Color(0,0,0, 170));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {

        // WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2; 
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawAttributeScreen() {

        // WINDOW PLAYER ATTRIBUTES
        int frameX = 0;
        int frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        int frameWidth = gp.screenWidth;
        int frameHeight = gp.screenHeight - frameY;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // PLAYER AVATAR
        // g2.drawImage(gp.player.down1, gp.screenWidth - gp.tileSize * 3 - gp.tileSize/5, frameY + gp.tileSize/3, gp.tileSize*3 - 35, gp.tileSize*3 - 35, null);

        BufferedImage playerAvatarImage;
        int avatarWidth, avatarHeight;

        if ("Male".equals(gp.player.getGender())) {
            playerAvatarImage = gp.player.def_avatar; 
            avatarWidth = gp.tileSize * 3 - 25;
            avatarHeight = gp.tileSize * 3 - 35;
            g2.drawImage(playerAvatarImage, gp.screenWidth - gp.tileSize * 3 - gp.tileSize/5, frameY + gp.tileSize/3, avatarWidth, avatarHeight, null);
            
        } else { // Female
            playerAvatarImage = gp.player.def_avatar_female; 
            avatarWidth = gp.tileSize * 3 - 20; 
            avatarHeight = gp.tileSize * 3 - 20;
            g2.drawImage(playerAvatarImage, gp.screenWidth - gp.tileSize * 3 - gp.tileSize/5, frameY + gp.tileSize/6, avatarWidth, avatarHeight, null);
        }
        
        // PLAYER ATTRIBUTES
        {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
            int x = frameX + gp.tileSize;
            int y = frameY + gp.tileSize;
    
            String text = "1. Name: ";
            g2.drawString(text, x, y);
            y += 30;
            
            text = gp.player.getName();
            g2.drawString(text, x + 27, y);
            y += 50;
    
            text = "2. Gender: ";
            g2.drawString(text, x, y);
            y += 30;
            
            text = gp.player.getGender();
            g2.drawString(text, x + 27, y);
            y -= 110;
            x += gp.tileSize * 4;
    
            text = "3. Energy: ";
            g2.drawString(text, x, y);
            y += 30;
            
            text = String.valueOf(gp.player.getEnergy());
            g2.drawString(text, x + 27, y);
            y += 50; 
            
            text = "4. Partner: ";
            g2.drawString(text, x, y);
            y += 30;
            
            if(gp.player.getPartner() == null || gp.player.getPartner().getName().isEmpty()) {
                text = "None";
            } else {
                text = gp.player.getPartner().getName();
            }
            g2.drawString(text, x + 27, y);
            y -= 110;
            x += gp.tileSize * 4;
    
            text = "5. Gold: ";
            g2.drawString(text, x, y);
            y += 30;
            
            text = String.valueOf(gp.player.getGold());
            g2.drawString(text, x + 27, y);
            y += 50;
    
            text = "6. Fav Item: ";
            g2.drawString(text, x, y);
            y += 30;
            
            if(gp.player.getFavItem() == null || gp.player.getFavItem().isEmpty()) {
                text = "None";
            } else {
                text = gp.player.getFavItem();
            }
            g2.drawString(text, x + 27, y);
        }
    }
     
    public void drawPlayerInventory(){
        
        // WINDOW PLAYER INVENTORY
        int frameX = 0;
        int frameY = 0;
        int frameWidth = gp.screenWidth - (gp.tileSize-3)*6;
        int frameHeight = gp.screenHeight/2 + (gp.tileSize+3)*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        String[][] slotFilled = new String[9][7];

        // DRAW PLAYER'S ITEMS
        for (Map.Entry<Items, Integer> entry : gp.player.getInventory().getItems().entrySet()) {
            Items item = entry.getKey();

            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;

            boolean found = false;
            for (int colIndex = 0; colIndex < slotFilled.length; colIndex++) {
                for (int rowIndex = 0; rowIndex < slotFilled[0].length; rowIndex++) {
                    if (slotFilled[colIndex][rowIndex] != null && item != null &&
                        slotFilled[colIndex][rowIndex].equals(item.getName())) {
                        col = colIndex;
                        row = rowIndex;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            if (!found && item != null) {
                slotFilled[col][row] = item.getName();
                g2.drawImage(item.getItemImage(), slotX, slotY, null);

                int quantity = gp.player.getInventory().getItemQuantity(item);
                if (quantity > 1) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.WHITE);
                    String quantityText = String.valueOf(quantity);

                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(quantityText);

                    g2.drawString(quantityText,
                        slotX + slotSize - textWidth - 4,
                        slotY + slotSize - 4);
                }

                slotX += slotSize;
            }

            if(slotX >= frameX + frameWidth - gp.tileSize) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }
        if (gp.keyH.enterPressed) {
            gp.keyH.enterPressed = false;
            if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
                slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {
                String selectedName = slotFilled[slotInventoryCol][slotInventoryRow];
                if (selectedName != null) {
                    Items selected = gp.player.getInventory().get(selectedName);
                    if (selected != null) {
                        selectedItem = selected;
                    } else {
                        selectedItem = null;
                    }
                } else {
                    selectedItem = null;
                }
            } else {
                selectedItem = null;
            }
        }
        
        // CURSOR
        int cursorX = slotXstart + (slotSize * slotInventoryCol);
        int cursorY = slotYstart + (slotSize * slotInventoryRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.yellow);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        

        // WINDOW DESCRIPTION
        int dFrameX = gp.screenWidth - (gp.tileSize-3)*6;
        int dFrameY = 0;
        int dFrameWidth = (gp.tileSize-3)*6;
        int dFrameHeight = gp.screenHeight/2 + (gp.tileSize+3)*2;

        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        // ITEM DESCRIPTION
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        String itemName = slotFilled[slotInventoryCol][slotInventoryRow];
        if(itemName != null) {
            Items item = gp.player.getInventory().get(itemName);
            String description = item.getDescription() != null ? item.getDescription() : "No description";
            int dX = dFrameX + 20;
            int dY = dFrameY + 40;

            g2.setColor(Color.cyan);
            if(item.getName().equals("The Legends of Spakbor")) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            }
            g2.drawString(item.getName(), dX, dY);
            dY += 40;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            g2.setColor(Color.white);
            for(String line : description.split("\n")) {
                g2.drawString(line, dX, dY);
                dY += 40;
            }
            if(item.getSellPrice() != null) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
                g2.setColor(Color.red);
                g2.drawString("Sell Price: " + item.getSellPrice() + "g", dX, dY);
                dY += 40;
            }
            if(item.getBuyPrice() != null) {
                g2.setColor(Color.green);
                g2.drawString("Buy Price: " + item.getBuyPrice() + "g", dX, dY);
                dY += 40;
            }
            if(item instanceof Crops) {
                Crops crop = (Crops)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
                if(crop.getHarvestedAmount() > 1) {
                    g2.drawString("Crops/Harvest: " + crop.getHarvestedAmount() + " crops", dX, dY);
                } else {
                    g2.drawString("Crops/Harvest: " + crop.getHarvestedAmount() + " crop", dX, dY);
                }
                dY += 40;
            } 
            if(item instanceof Fish) {
                Fish fish = (Fish)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                g2.drawString("Rarity: " + fish.getRarity(), dX, dY);
                dY += 40;
                g2.drawString("Location: ", dX, dY);
                dY += 40;
                for (String location : fish.getLocations()) {
                    g2.drawString("- " + location, dX + 20, dY);
                    dY += 30;
                }
            }
            if(item instanceof Seeds) {
                Seeds seed = (Seeds)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                if (seed.getHarvestDays() > 1) {
                    g2.drawString("Harvest Days: " + seed.getHarvestDays() + " days", dX, dY);
                } else {
                    g2.drawString("Harvest Days: " + seed.getHarvestDays() + " day", dX, dY);
                }
                dY += 40;
            }

            if(item instanceof Food) {
                Food food = (Food)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                g2.drawString("+" + food.getEnergy() + " Energy", dX, dY);
                dY += 40;
            }
        
        } else {
            g2.drawString("No item selected", dFrameX + 20, dFrameY + 60);
        }
    }

    public void drawPlantSeedScreen() {

        // WINDOW PLAYER INVENTORY
        int x = gp.tileSize/2;
        int y = gp.tileSize/2; 
        int width = gp.tileSize * 5;
        int height = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);

        // SLOT
        final int slotXstart = x + 20;
        final int slotYstart = y + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        String[][] slotFilled = new String[4][4];

        // DRAW PLAYER'S ITEMS
        for (Map.Entry<Items, Integer> entry : gp.player.getInventory().getItems().entrySet()) {
            Items item = entry.getKey();

            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;

            boolean found = false;
            for (int colIndex = 0; colIndex < slotFilled.length; colIndex++) {
                for (int rowIndex = 0; rowIndex < slotFilled[0].length; rowIndex++) {
                    if (slotFilled[colIndex][rowIndex] != null && item != null &&
                        slotFilled[colIndex][rowIndex].equals(item.getName())) {
                        col = colIndex;
                        row = rowIndex;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            if (!found && item != null && item instanceof Seeds) {
                slotFilled[col][row] = item.getName();
                g2.drawImage(item.getItemImage(), slotX, slotY, null);

                int quantity = gp.player.getInventory().getItemQuantity(item);
                if (quantity > 1) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.WHITE);
                    String quantityText = String.valueOf(quantity);

                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(quantityText);

                    g2.drawString(quantityText,
                        slotX + slotSize - textWidth - 4,
                        slotY + slotSize - 4);
                }

                slotX += slotSize;
            }

            if(slotX >= x + width - gp.tileSize) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }
        if (gp.keyH.enterPressed) {
            if (selectedSeed == null) {
                if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
                    slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {
                    String selectedName = slotFilled[slotInventoryCol][slotInventoryRow];
                    if (selectedName != null) {
                        Items selected = gp.player.getInventory().get(selectedName);
                        if (selected instanceof Seeds) {
                            selectedSeed = (Seeds) selected;
                            PlantedTile plantedTile = new PlantedTile(selectedSeed, gp.gameClock.getDate().getOriginDay());
                            plantedTile.location.worldX = tempWorldX;
                            plantedTile.location.worldY = tempWorldY;
                            gp.tiles.put(tempTileLocation, plantedTile);
                            gp.player.planting();
                            gp.player.getInventory().removeItem(selectedSeed, 1);
                            selectedSeed = null;
                            gp.gameState = gp.playState;
                            System.out.println("Planted!");
                        }
                    }
                }
            }
            gp.keyH.enterPressed = false;
        }
        
        // CURSOR
        int cursorX = slotXstart + (slotSize * slotInventoryCol);
        int cursorY = slotYstart + (slotSize * slotInventoryRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.yellow);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        

        // WINDOW DESCRIPTION
        int dFrameX = gp.screenWidth - (gp.tileSize)*6;
        int dFrameY = gp.tileSize/2;
        int dFrameWidth = (gp.tileSize-3)*6;
        int dFrameHeight = gp.screenHeight/2 + (gp.tileSize+3)*2;

        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        // ITEM DESCRIPTION
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        String itemName = slotFilled[slotInventoryCol][slotInventoryRow];
        if(itemName != null) {
            Items item = gp.player.getInventory().get(itemName);
            String description = item.getDescription() != null ? item.getDescription() : "No description";
            int dX = dFrameX + 20;
            int dY = dFrameY + 40;
            g2.drawString(item.getName(), dX, dY);
            dY += 40;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            g2.setColor(Color.white);
            for(String line : description.split("\n")) {
                g2.drawString(line, dX, dY);
                dY += 40;
            }
            if(item.getSellPrice() != null) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
                g2.setColor(Color.red);
                g2.drawString("Sell Price: " + item.getSellPrice() + "g", dX, dY);
                dY += 40;
            }
            if(item.getBuyPrice() != null) {
                g2.setColor(Color.green);
                g2.drawString("Buy Price: " + item.getBuyPrice() + "g", dX, dY);
                dY += 40;
            }
        }
    }

    public void drawHouseScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 6;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "Inside Your House";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        y += gp.tileSize;

        String[] options = {
            "SLEEPING",
            "WATCHING",
            "COOKING",
            "EXIT"
        };

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = getXforCenteredText(text);
            y += gp.tileSize;

            g2.drawString(text, textX, y);
            if (commandNum == i) {
                g2.drawString(">", textX - gp.tileSize, y);
            }
        }
    }

    public void drawSleepScreen() {
        g2.setColor(new Color(0,0,0, 170));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "SLEPT";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    public void drawWatchScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 6;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "Inside Your House";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);
    }

    public void drawFishingScreen(){
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 10;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "Fishing at " + gp.player.fishingLocation;
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,24F));
        String text = "You fished " + gp.fished.getName() + "!";
        x = getXforCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        String range;
        String rarity = gp.fished.getRarity();
        if (rarity.equals("Common")) range = "10";
        else if (rarity.equals("Regular")) range = "100";
        else range = "500";

        text = "You must guess a number between 1 to " + range + " to catch this fish!";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        // INPUT NAME
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "Attempts left: " + (11 - fishingAttempts);
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
        text = "Enter your guess:";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        String guessStringDisplay = guessString.toString() + (System.currentTimeMillis() / 500 % 2 == 0 ? "_" : ""); // Blinking cursor
        x = getXforCenteredText(guessStringDisplay);
        y += gp.tileSize + 10;
        g2.drawString(guessStringDisplay, x, y);

        if (fishingWarning != null){
            x = getXforCenteredText(fishingWarning);
            y += gp.tileSize;
            g2.drawString(fishingWarning, x, y);
        }
    }

    public void drawFailedScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "FAILED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawSucceededScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "SUCCEEDED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawBinScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 6;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "Shipping Bin";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        y += gp.tileSize * 2;

        String[] options = {
            "SELL ITEMS",
            "EXIT"
        };

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = getXforCenteredText(text);
            y += gp.tileSize;

            g2.drawString(text, textX, y);
            if (commandNum == i) {
                g2.drawString(">", textX - gp.tileSize, y);
            }
        }
    }

    public void drawBinShopScreen() {

        drawPlayerInventory();

        // WINDOW AMOUNT
        int frameX = 0;
        int frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        int frameWidth = gp.screenWidth - (gp.tileSize+3) * 9;
        int frameHeight = gp.screenHeight - frameY;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.white);
        g2.drawString("Amount", frameX + 110, frameY + 50);

        // WINDOW BIN SLOT
        frameX = gp.screenWidth - (gp.tileSize+3) * 9;
        frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        frameWidth = (gp.tileSize+3) * 9;
        frameHeight = gp.screenHeight - frameY;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        g2.drawString("Shipping Bin", frameX + 160, frameY + 50);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        String[][] slotFilled = new String[8][2];


        // WINDOW SHIP BIN
        frameX = gp.screenWidth - (gp.tileSize+3) * 9;
        frameWidth = gp.screenWidth - frameX;


        for (Map.Entry<Items, Integer> entry : gp.binShopInventory.getItems().entrySet()) {
            Items item = entry.getKey();
            
            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;

            boolean found = false;
            for (int colIndex = 0; colIndex < slotFilled.length; colIndex++) {
                for (int rowIndex = 0; rowIndex < slotFilled[0].length; rowIndex++) {
                    if (slotFilled[colIndex][rowIndex] != null && 
                        slotFilled[colIndex][rowIndex].equals(item.getName())) {
                        col = colIndex;
                        row = rowIndex;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            if (!found) {
                slotFilled[col][row] = item.getName();
                g2.setColor(new Color(255, 255, 255, 100)); // warna putih transparan
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(slotX, slotY + gp.tileSize, slotSize, slotSize, 10, 10);

                g2.drawImage(item.getItemImage(), slotX, slotY + gp.tileSize, null);

                int quantity = gp.binShopInventory.getItemQuantity(item);
                if (quantity > 1) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.WHITE);
                    String quantityText = String.valueOf(quantity);
                    
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(quantityText);
                    
                    g2.drawString(quantityText,
                        slotX + slotSize - textWidth - 4,
                        slotY + slotSize - 4 + gp.tileSize);
                }

                slotX += slotSize;
            }

            if(slotX >= frameX + frameWidth - gp.tileSize) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }
        if (gp.keyH.enterPressed) {
            gp.keyH.enterPressed = false;
            if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
                slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {
                String selectedName = slotFilled[slotInventoryCol][slotInventoryRow];
                if (selectedName != null) {
                    Items selected = gp.player.getInventory().get(selectedName);
                    if (selected != null) {
                        selectedItem = selected;
                    } else {
                        selectedItem = null;
                    }
                } else {
                    selectedItem = null;
                }
            } else {
                selectedItem = null;
            }
        }
        
    }

    public void drawStoreInventory() {
                
        // WINDOW PLAYER INVENTORY
        int frameX = 0;
        int frameY = 0;
        int frameWidth = gp.screenWidth - (gp.tileSize-3)*6;
        int frameHeight = gp.screenHeight/2 + (gp.tileSize+3)*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        String[][] slotFilled = new String[9][7];

        // DRAW PLAYER'S ITEMS
        for (Map.Entry<Items, Integer> entry : gp.storeShopInventory.getItems().entrySet()) {
            Items item = entry.getKey();

            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;

            boolean found = false;
            for (int colIndex = 0; colIndex < slotFilled.length; colIndex++) {
                for (int rowIndex = 0; rowIndex < slotFilled[0].length; rowIndex++) {
                    if (slotFilled[colIndex][rowIndex] != null && item != null &&
                        slotFilled[colIndex][rowIndex].equals(item.getName())) {
                        col = colIndex;
                        row = rowIndex;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            if (!found && item != null) {
                slotFilled[col][row] = item.getName();
                g2.drawImage(item.getItemImage(), slotX, slotY, null);

                int quantity = gp.storeShopInventory.getItemQuantity(item);
                if (quantity > 1) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.WHITE);
                    String quantityText = String.valueOf(quantity);

                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(quantityText);

                    g2.drawString(quantityText,
                        slotX + slotSize - textWidth - 4,
                        slotY + slotSize - 4);
                }

                slotX += slotSize;
            }

            if(slotX >= frameX + frameWidth - gp.tileSize) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }
        if (gp.keyH.enterPressed) {
            gp.keyH.enterPressed = false;
            if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
                slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {
                String selectedName = slotFilled[slotInventoryCol][slotInventoryRow];
                if (selectedName != null) {
                    Items selected = gp.storeShopInventory.get(selectedName);
                    if (selected != null) {
                        selectedItem = selected;
                    } else {
                        selectedItem = null;
                    }
                } else {
                    selectedItem = null;
                }
            } else {
                selectedItem = null;
            }
        }
        
        // CURSOR
        int cursorX = slotXstart + (slotSize * slotInventoryCol);
        int cursorY = slotYstart + (slotSize * slotInventoryRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.yellow);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        

        // WINDOW DESCRIPTION
        int dFrameX = gp.screenWidth - (gp.tileSize-3)*6;
        int dFrameY = 0;
        int dFrameWidth = (gp.tileSize-3)*6;
        int dFrameHeight = gp.screenHeight/2 + (gp.tileSize+3)*2;

        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        // ITEM DESCRIPTION
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        String itemName = slotFilled[slotInventoryCol][slotInventoryRow];
        if(itemName != null) {
            Items item = gp.storeShopInventory.get(itemName);
            String description = item.getDescription() != null ? item.getDescription() : "No description";
            int dX = dFrameX + 20;
            int dY = dFrameY + 40;

            g2.setColor(Color.cyan);
            if(item.getName().equals("The Legends of Spakbor")) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
            }
            g2.drawString(item.getName(), dX, dY);
            dY += 40;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            g2.setColor(Color.white);
            for(String line : description.split("\n")) {
                g2.drawString(line, dX, dY);
                dY += 40;
            }
            if(item.getSellPrice() != null) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
                g2.setColor(Color.red);
                g2.drawString("Sell Price: " + item.getSellPrice() + "g", dX, dY);
                dY += 40;
            }
            if(item.getBuyPrice() != null) {
                g2.setColor(Color.green);
                g2.drawString("Buy Price: " + item.getBuyPrice() + "g", dX, dY);
                dY += 40;
            }
            if(item instanceof Crops) {
                Crops crop = (Crops)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));
                if(crop.getHarvestedAmount() > 1) {
                    g2.drawString("Crops/Harvest: " + crop.getHarvestedAmount() + " crops", dX, dY);
                } else {
                    g2.drawString("Crops/Harvest: " + crop.getHarvestedAmount() + " crop", dX, dY);
                }
                dY += 40;
            } 
            if(item instanceof Fish) {
                Fish fish = (Fish)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                g2.drawString("Rarity: " + fish.getRarity(), dX, dY);
                dY += 40;
                g2.drawString("Location: ", dX, dY);
                dY += 40;
                for (String location : fish.getLocations()) {
                    g2.drawString("- " + location, dX + 20, dY);
                    dY += 30;
                }
            }
            if(item instanceof Seeds) {
                Seeds seed = (Seeds)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                if (seed.getHarvestDays() > 1) {
                    g2.drawString("Harvest Days: " + seed.getHarvestDays() + " days", dX, dY);
                } else {
                    g2.drawString("Harvest Days: " + seed.getHarvestDays() + " day", dX, dY);
                }
                dY += 40;
            }

            if(item instanceof Food) {
                Food food = (Food)item;
                g2.setColor(Color.yellow);
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
                g2.drawString("+" + food.getEnergy() + " Energy", dX, dY);
                dY += 40;
            }
        
        } else {
            g2.drawString("No item selected", dFrameX + 20, dFrameY + 60);
        }
    }

    public void drawStoreShopScreen() {

        drawStoreInventory();

        // WINDOW AMOUNT
        int frameX = 0;
        int frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        int frameWidth = gp.screenWidth - (gp.tileSize+3) * 9;
        int frameHeight = gp.screenHeight - frameY;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.white);
        g2.drawString("Amount", frameX + 110, frameY + 50);

        // WINDOW INVENTORY SLOT
        frameX = gp.screenWidth - (gp.tileSize+3) * 9;
        frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        frameWidth = (gp.tileSize+3) * 9;
        frameHeight = gp.screenHeight - frameY;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        g2.drawString("Inventory", frameX + 175, frameY + 50);

        // SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        String[][] slotFilled = new String[9][gp.storeShopInventory.count()];


        // WINDOW INVENTORY
        frameX = gp.screenWidth - (gp.tileSize+3) * 9;
        frameWidth = gp.screenWidth - frameX;

        for (Map.Entry<Items, Integer> entry : gp.player.getInventory().getItems().entrySet()) {
            Items item = entry.getKey();
            
            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;

            boolean found = false;
            for (int colIndex = 0; colIndex < slotFilled.length; colIndex++) {
                for (int rowIndex = 0; rowIndex < slotFilled[0].length; rowIndex++) {
                    if (slotFilled[colIndex][rowIndex] != null && 
                        slotFilled[colIndex][rowIndex].equals(item.getName())) {
                        col = colIndex;
                        row = rowIndex;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            if (!found) {
                slotFilled[col][row] = item.getName();
                g2.setColor(new Color(255, 255, 255, 100));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(slotX, slotY + gp.tileSize, slotSize, slotSize, 10, 10);

                g2.drawImage(item.getItemImage(), slotX, slotY + gp.tileSize, null);

                int quantity = gp.player.getInventory().getItemQuantity(item);
                if (quantity > 1) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.WHITE);
                    String quantityText = String.valueOf(quantity);
                    
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(quantityText);
                    
                    g2.drawString(quantityText,
                        slotX + slotSize - textWidth - 4,
                        slotY + slotSize - 4 + gp.tileSize);
                }

                slotX += slotSize;
            }

            if(slotX >= frameX + frameWidth - gp.tileSize) {
                slotX = slotXstart;
                slotY += slotSize;
            }

        }
        if (gp.keyH.enterPressed) {
            gp.keyH.enterPressed = false;
            if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
                slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {
                String selectedName = slotFilled[slotInventoryCol][slotInventoryRow];
                if (selectedName != null) {
                    Items selected = gp.storeShopInventory.get(selectedName);
                    if (selected != null) {
                        selectedItem = selected;
                    } else {
                        selectedItem = null;
                    }
                } else {
                    selectedItem = null;
                }
            } else {
                selectedItem = null;
            }
        }
    }

    public void drawStoreAmountScreen() {

        drawStoreShopScreen();

        int frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        int frameHeight = gp.screenHeight - frameY;
        int minusX = 20;
        int plusX = (gp.screenWidth - (gp.tileSize+3) * 9) - 40 - g2.getFontMetrics().stringWidth("+");
        int buttonY = frameY + frameHeight / 2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 120F));
        g2.setColor(Color.white);
        g2.drawString("-", minusX, buttonY + 30);
        g2.drawString("+", plusX, buttonY + 30);
        if (selectedItem != null) {
            g2.drawImage(selectedItem.getItemImage(), 127, buttonY-20, null);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.drawString("Buy: " + buyAmount, 40, buttonY + 70);
        Integer buyPrice = selectedItem.getBuyPrice();
        if (buyPrice != null) {
            g2.drawString("Price: " + (buyPrice * buyAmount) + " g", 160, buttonY + 70);
        } else {
            g2.drawString("Price: N/A", 160, buttonY + 70);
        }

        if (gp.keyH.minusPressed) {
            if (buyAmount > 1) {
                buyAmount--;
                gp.keyH.minusPressed = false;
            }
        }
        if (gp.keyH.plusPressed) {
            if (buyAmount < gp.storeShopInventory.getItemQuantity(selectedItem)) {
                buyAmount++;
                gp.keyH.plusPressed = false;
            }
        }
        
        if (buyAmount > gp.storeShopInventory.getItemQuantity(selectedItem)) {
            buyAmount = gp.storeShopInventory.getItemQuantity(selectedItem);
        } else if (buyAmount < 1) {
            gp.gameState = gp.storeShopState;
        }
    }

    public void drawBinAmountScreen() {

        drawBinShopScreen();

        int frameY = gp.screenHeight/2 + (gp.tileSize+3)*2;
        int frameHeight = gp.screenHeight - frameY;
        int minusX = 20;
        int plusX = (gp.screenWidth - (gp.tileSize+3) * 9) - 40 - g2.getFontMetrics().stringWidth("+");
        int buttonY = frameY + frameHeight / 2;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 120F));
        g2.setColor(Color.white);
        g2.drawString("-", minusX, buttonY + 30);
        g2.drawString("+", plusX, buttonY + 30);
        if (selectedItem != null) {
            g2.drawImage(selectedItem.getItemImage(), 127, buttonY-20, null);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.drawString("Sell: " + sellAmount, 40, buttonY + 70);
        Integer sellPrice = selectedItem.getSellPrice();
        if (sellPrice != null) {
            g2.drawString("Price: " + (sellPrice * sellAmount) + " g", 160, buttonY + 70);
        } else {
            g2.drawString("Price: N/A", 160, buttonY + 70);
        }

        if (gp.keyH.minusPressed) {
            if (sellAmount > 1) {
                sellAmount--;
                gp.keyH.minusPressed = false;
            }
        }
        if (gp.keyH.plusPressed) {
            if (sellAmount < gp.storeShopInventory.getItemQuantity(selectedItem)) {
                sellAmount++;
                gp.keyH.plusPressed = false;
            }
        }
        
        if (sellAmount > gp.storeShopInventory.getItemQuantity(selectedItem)) {
            sellAmount = gp.storeShopInventory.getItemQuantity(selectedItem);
        } else if (sellAmount < 1) {
            gp.gameState = gp.binShopState;
        }
    }

    public void drawMoveMapScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 6;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "Move Map";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        y += gp.tileSize * 2;

        String[] options = {
            "MOVE",
            "EXIT"
        };

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = getXforCenteredText(text);
            y += gp.tileSize;

            g2.drawString(text, textX, y);
            if (commandNum == i) {
                g2.drawString(">", textX - gp.tileSize, y);
            }
        }
    }

    public void drawNPCScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 7;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "Interacting with " + gp.npc[gp.currentMap][gp.interactedNPC].getName();
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        y += gp.tileSize * 2;

        String[] options = {
            "GIFT",
            "PROPOSE",
            "MARRY",
            "EXIT"
        };

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = getXforCenteredText(text);
            y += gp.tileSize;

            g2.drawString(text, textX, y);
            if (commandNum == i) {
                g2.drawString(">", textX - gp.tileSize, y);
            }
        }
    }

    public void drawAcceptedScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String text = "ACCEPTED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawYourPartnerScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String text = "IT\'S YOUR PARTNER!";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawHavePartnerScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String text = "YOU ALREADY HAVE A PARTNER!";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawTooSoonScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String text = "TOO SOON";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawRejectedScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String text = "REJECTED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        text = "Increase their heartpoints and relationship with you!";
        x = getXforCenteredText(text);
        y = y + gp.tileSize;
        g2.drawString(text, x, y);
    }

    public void drawHouseNPCScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 6;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = gp.npc[1][visitedNPC].getName() + "\'s House";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        y += gp.tileSize * 2;

        String[] options = {
            "Call " + gp.npc[1][visitedNPC].getName(),
            "Exit"
        };

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = getXforCenteredText(text);
            y += gp.tileSize;

            g2.drawString(text, textX, y);
            if (commandNum == i) {
                g2.drawString(">", textX - gp.tileSize, y);
            }
        }
    }

    public void drawStoreScreen() {
        // WINDOW FRAME
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2; 
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 6;

        drawSubWindow(x, y, width, height);

        // TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
        g2.setColor(Color.white);
        String title = "STORE";
        int titleX = getXforCenteredText(title);
        g2.drawString(title, titleX, y + gp.tileSize);

        // MENU OPTIONS
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        y += gp.tileSize * 2;

        String[] options = {
            "View Store",
            "Call Emily",            
            "Exit"
        };

        for (int i = 0; i < options.length; i++) {
            String text = options[i];
            int textX = getXforCenteredText(text);
            y += gp.tileSize;

            g2.drawString(text, textX, y);
            if (commandNum == i) {
                g2.drawString(">", textX - gp.tileSize, y);
            }
        }
    }

    public void drawBinFailed() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        String text = "YOU ALREADY OPENED THE BIN";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);
    }

    public void drawEatingScreen() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2; 
        int width = gp.tileSize * 5;
        int height = gp.tileSize * 5;
        drawSubWindow(x, y, width, height);

        // SLOT
        final int slotXstart = x + 20;
        final int slotYstart = y + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;
        Items[][] slotFilled = new Items[4][4];

        for (Map.Entry<Items, Integer> entry : gp.player.getInventory().getItems().entrySet()) {
            Items item = entry.getKey();

            if (!(item instanceof Edible)) continue;

            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;

            boolean found = false;
            for (int colIndex = 0; colIndex < slotFilled.length; colIndex++) {
                for (int rowIndex = 0; rowIndex < slotFilled[0].length; rowIndex++) {
                    if (slotFilled[colIndex][rowIndex] != null && item != null &&
                        slotFilled[colIndex][rowIndex].equals(item)) {
                        col = colIndex;
                        row = rowIndex;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }

            if (!found && item != null) {
                slotFilled[col][row] = item;
                g2.drawImage(item.getItemImage(), slotX, slotY, null);

                int quantity = gp.player.getInventory().getItemQuantity(item);
                if (quantity > 1) {
                    g2.setFont(g2.getFont().deriveFont(Font.BOLD, 22F));
                    g2.setColor(Color.WHITE);
                    String quantityText = String.valueOf(quantity);

                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(quantityText);

                    g2.drawString(quantityText,
                        slotX + slotSize - textWidth - 4,
                        slotY + slotSize - 4);
                }

                slotX += slotSize;
                if (slotX >= x + width - gp.tileSize) {
                    slotX = slotXstart;
                    slotY += slotSize;
                }
            }
        }

        // HANDLE EATING
        if (gp.keyH.enterPressed) {
            if (edible == null) {
                if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
                    slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {

                    Items selected = slotFilled[slotInventoryCol][slotInventoryRow];
                    if (selected instanceof Edible) {
                        edible = (Edible) selected;
                        gp.player.removeItemFromInventory(selected, 1);
                        if (gp.player.getInventory().getItemQuantity(selected) == 0) {
                            slotFilled[slotInventoryCol][slotInventoryRow] = null;
                        }
                    }
                }
            }
            gp.keyH.enterPressed = false;
        }

        // DRAW CURSOR
        int cursorX = slotXstart + (slotSize * slotInventoryCol);
        int cursorY = slotYstart + (slotSize * slotInventoryRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        g2.setColor(Color.yellow);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESCRIPTION WINDOW
        int dFrameX = gp.screenWidth - (gp.tileSize) * 6;
        int dFrameY = gp.tileSize / 2;
        int dFrameWidth = (gp.tileSize - 3) * 6;
        int dFrameHeight = gp.screenHeight / 2 + (gp.tileSize + 3) * 2;

        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        // ITEM DESCRIPTION
        Items item = null;
        if (slotInventoryCol >= 0 && slotInventoryCol < slotFilled.length &&
            slotInventoryRow >= 0 && slotInventoryRow < slotFilled[0].length) {
            item = slotFilled[slotInventoryCol][slotInventoryRow];
        }

        if (item != null) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
            int dX = dFrameX + 20;
            int dY = dFrameY + 40;

            g2.drawString(item.getName(), dX, dY);
            dY += 40;

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            g2.setColor(Color.white);
            String description = item.getDescription() == null ? "No description" : item.getDescription();
            for (String line : description.split("\n")) {
                g2.drawString(line, dX, dY);
                dY += 40;
            }

            if (item instanceof Edible) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
                g2.setColor(Color.red);
                g2.drawString("Energy: " + ((Edible) item).getEnergy(), dX, dY);
            }
        }
    }


    public void drawSubWindow(int x, int y, int width, int height) {
        
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    private void drawGameTime() {
        String dateStr = GameClock.getInstance().getFormattedDate();
        String timeStr = GameClock.getInstance().getFormattedTime();
        String weatherStr = GameClock.getInstance().getFormattedWeather();
        String locX = "Player location X: " + gp.player.worldX;
        String locY = "Player location Y: " + gp.player.worldY;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString(dateStr, 20, 40);
        g2.drawString(timeStr, 20, 80);
        g2.drawString(weatherStr, 20, 120);
        g2.drawString(locX, 20, 160);
        g2.drawString(locY, 20, 200);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public void drawPartialBorderText(Graphics2D g2, String fullText, String targetText, int x, int y, 
                                      int padding, Color textColor, Color borderColor, Color backgroundColor) {

        FontMetrics fm = g2.getFontMetrics();

        // Measure width up to the target word
        int prefixWidth = fm.stringWidth(fullText.substring(0, fullText.indexOf(targetText)));
        int targetWidth = fm.stringWidth(targetText);
        int textHeight = fm.getAscent();

        // Draw full string
        g2.setColor(Color.WHITE);
        g2.drawString(fullText, x, y);

        // Draw border around target word
        int rectX = x + prefixWidth - padding;
        int rectY = y - textHeight - padding + fm.getDescent();
        int rectWidth = targetWidth + padding * 2;
        int rectHeight = textHeight + padding * 2;

        g2.setColor(backgroundColor); // background
        g2.fillRect(rectX, rectY, rectWidth, rectHeight);

        g2.setColor(borderColor); // border
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(rectX, rectY, rectWidth, rectHeight);

        // Redraw the target word on top
        g2.setColor(textColor); 
        g2.drawString(fullText, x, y);
    }
}
