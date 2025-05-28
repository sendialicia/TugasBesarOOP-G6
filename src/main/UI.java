package main;

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

import items.Items;
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
    public int slotCol = 0;
    public int slotRow = 0;

    // double playTime;
    // DecimalFormat dFormat = new DecimalFormat("#0.00");

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
        if(gp.gameState == gp.playState) {
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
        }

        // HOUSE INTERACT STATE
        if (gp.gameState == gp.houseInteractState){
            drawHouseScreen();
        }

        // // VIEW INVENTORY STATE
        // if(gp.gameState == gp.viewInventoryState) {
        //     drawViewInventory();
        // }
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
        g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

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
        int x2 = getXforCenteredText(text); // For the ">" symbol
        y += gp.tileSize*3.5;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-gp.tileSize, y); // Can use draw image if want to use image instead of ">"
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x2-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2) {
            g2.drawString(">", x2-gp.tileSize, y);
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
        } else if(commandNum == 1) {
            drawPartialBorderText(g2, text, "Female", x, y, 15, Color.WHITE, 
                                  Color.WHITE, new Color(242, 1, 108, 150));
        }
    }

    public void drawPauseScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
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
        g2.drawImage(gp.player.down1, gp.screenWidth - gp.tileSize * 3 - gp.tileSize/5, frameY + gp.tileSize/3, gp.tileSize*3 - 35, gp.tileSize*3 - 35, null);

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
     

        // WINDOW PLAYER INVENTORY
        frameX = 0;
        frameY = 0;
        frameWidth = gp.screenWidth - (gp.tileSize-3)*6;
        frameHeight = gp.screenHeight/2 + (gp.tileSize+3)*2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        // PLAYER INVENTORY

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
            int quantity = entry.getValue();
            
            int col = (slotX - slotXstart) / slotSize;
            int row = (slotY - slotYstart) / slotSize;
            slotFilled[col][row] = gp.player.getInventory().get(item.getName()).getName();
            g2.drawImage(item.getItemImage(), slotX, slotY, null);

            slotX += slotSize;

            if(slotX >= frameX + frameWidth - gp.tileSize) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //  CURSOR
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.WHITE);
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
        String itemName = slotFilled[slotCol][slotRow];
        if(itemName != null) {
            Items item = gp.player.getInventory().get(itemName);
            String description = item.getDescription() != null ? item.getDescription() : "No description";
            int dX = dFrameX + 20;
            int dY = dFrameY + 40;

            g2.setColor(Color.cyan);
            g2.drawString(item.getName(), dX, dY);
            dY += 40;
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
            g2.setColor(Color.white);
            g2.drawString(description, dX, dY);
            dY += 40;

            if(item.getSellPrice() != null) {
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
                g2.setColor(Color.red);
                g2.drawString("Sell Price: " + item.getSellPrice(), dX, dY);
                dY += 40;
            }
            if(item.getBuyPrice() != null) {
                g2.setColor(Color.green);
                g2.drawString("Buy Price: " + item.getBuyPrice(), dX, dY);
                dY += 40;
            }
        } else {
            g2.drawString("No item selected", dFrameX + 20, dFrameY + 60);
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
        y += gp.tileSize * 2;

        String[] options = {
            "SLEEPING",
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

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
        g2.setColor(Color.WHITE);
        g2.drawString(dateStr, 20, 40);
        g2.drawString(timeStr, 20, 80);
        g2.drawString(weatherStr, 20, 120);
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
