package main;

import entity.Player;
import entity.PlayerAttribute;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
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
    KeyHandler keyH = new KeyHandler(this) ;
    PlayerAttribute pa;
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread; // to run something that never stops even when the apps is close

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH, pa);
    public SuperObject obj[] = new SuperObject[10];

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int nameInputState = 1;
    public final int playState = 2;
    public final int pauseState = 3;

    public GamePanel(PlayerAttribute pa){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // set the size of this class (JPanel)
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better rendering performance
        this.addKeyListener(keyH);
        this.setFocusable(true); // GamePanel can be "focused" to receive key input
        this.pa = pa;
    }

    public void setupGame() {
        aSetter.setObject();
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    /*
    public void run(){

        double drawInterval = 1000000000/FPS; // 0.016667 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update(); // update information such as character position

            repaint(); // draw the screen with updated information

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
     */
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
            player.update();
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
        } else if (gameState == nameInputState) {
            ui.draw(g2); // UI class will handle drawing specific name input screen elements
        } else { 
            // For other states like playState, pauseState, etc.
            
            // Draw game world elements (e.g., tiles, objects, player)
            // Adjust this condition if some states (other than title/nameInput) shouldn't draw the world
            if (gameState == playState || gameState == pauseState) { // Example: Draw world if playing or paused
                tileM.draw(g2);
        
                for(int i = 0; i < obj.length; i++) { // Keep original loop if preferred
                    if(obj[i] != null) {
                        obj[i].draw(g2, this);
                    }
                }
        
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
