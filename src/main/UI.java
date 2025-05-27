package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
// import java.awt.RenderingHints;
// import java.text.DecimalFormat;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


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

        // // VIEW ATTRIBUTE STATE
        // if(gp.gameState == gp.viewAttributeState) {
        //     drawViewAttribute();
        // }

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
            g2.setColor(new Color(1, 137, 180, 150));
            g2.fillRect(x+290, y - 40, 150, 50);
            g2.setColor(new Color(255, 255, 255));
            g2.drawString(text, x, y);
            g2.drawRect(x+290, y - 40, 150, 50);
        } else if(commandNum == 1) {
            g2.setColor(new Color(242, 1, 108, 150));
            g2.fillRect(x+505, y - 40, 150, 50);
            g2.setColor(new Color(255, 255, 255));
            g2.drawString(text, x, y);
            g2.drawRect(x+505, y - 40, 150, 50);
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

    public void drawSubWindow(int x, int y, int width, int height) {
        
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

}
