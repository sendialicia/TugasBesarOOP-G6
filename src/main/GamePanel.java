package main;

import entity.Entity;
import entity.Player;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16  ;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread; // to run something that never stops even when the apps is close

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[10];

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int nameInputState = 1;
    public final int genderInputState = 2;
    public final int farmNameInputState = 3;
    public final int playState = 4;
    public final int pauseState = 5;
    public final int dialogueState = 6;
    public final int viewAttributeState = 7;
    public final int viewInventoryState = 8;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of this class (JPanel)
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // GamePanel can be "focused" to receive key input
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        // playMusic(0);
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void update(){
        if(gameState == playState) {

            // PLAYER
            player.update();

            // NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }

        }
        if(gameState == pauseState) {
            // nothing
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long drawStart = 0;
        if(keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }

        // STATE-BASED DRAWING
        if (gameState == titleState) {
            ui.draw(g2); // UI class will handle drawing specific title screen elements
        } else if (gameState == farmNameInputState) {
            ui.draw(g2); // UI class will handle drawing specific name input screen elements
        } else if (gameState == nameInputState) {
            ui.draw(g2); // UI class will handle drawing specific name input screen elements
        } else if (gameState == genderInputState) {
            ui.draw(g2);
        } else { 
            // For other states like playState, pauseState, etc.
            
            // Draw game world elements (e.g., tiles, objects, player)
            // Adjust this condition if some states (other than title/nameInput) shouldn't draw the world
            if (gameState == playState || gameState == pauseState || gameState == dialogueState) { // Example: Draw world if playing or pause

                // TILE
                tileM.draw(g2);
        
                // OBJECT
                for(int i = 0; i < obj.length; i++) {
                    if(obj[i] != null) {
                        obj[i].draw(g2, this);
                    }
                }
        
                // NPC
                for(int i = 0; i < npc.length; i++) {
                    if(npc[i] != null) {
                        npc[i].draw(g2);
                    }
                }

                // PLAYER
                player.draw(g2);
            }
        
            // Draw UI elements for these other states (e.g., HUD, pause menu)
            // The UI.draw() method itself has conditions for what to draw based on gameState
            ui.draw(g2); 
        }


        // DEBUG
        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + passed, 10, 400);
            System.out.println("Draw time: " + passed);
        }

        g2.dispose();
    }

    public void playMusic(int i) {
        
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        
        se.setFile(i);
        se.play();
    }
}
