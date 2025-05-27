package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    // BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public StringBuilder playerName = new StringBuilder();
    private boolean nameEntered = false;

    public int commandNum = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        // OBJ_Key key = new OBJ_Key(gp);
        // keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        // TITLE STATE
        if(gp.gameState == gp.titleState)
        {
            drawTitleScreen();
        }

        if (gp.gameState == gp.nameInputState){
            drawInputNameScreen();
        }

        if(gp.gameState == gp.playState) {
            // Do player stuff later
        }
        if(gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawTitleScreen(){
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "SPAKBOR HILLS";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;
    
        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text, x+5, y+5);

        // COLOR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // ICON IMAGE
        x = gp.screenWidth/2 - (gp.tileSize*2)/2;
        y += gp.tileSize*2;

        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);
        if (commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    public void drawInputNameScreen() {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0, 0 ,gp.screenWidth, gp.screenHeight);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "SPAKBOR HILLS";
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
        y += gp.tileSize * 2;
        g2.drawString(nameDisplay, x, y);

        if (nameEntered) {
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 24F));
            g2.drawString("Press ENTER to continue", getXforCenteredText("Press ENTER to continue"), y + gp.tileSize * 2);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public int getXforCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
}
