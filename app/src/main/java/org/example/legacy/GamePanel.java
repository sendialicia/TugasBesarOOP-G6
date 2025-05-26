package org.example.legacy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {

    public final int originalTileSize = 16; 
    public final int scale = 3; 
    public final int tileSize = originalTileSize * scale; 
    public final int maxScreenCol = 16; 
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; 
    public final int screenHeight = tileSize * maxScreenRow; 

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    int fps = 60; 

    TileManager tileManager = new TileManager(this);
    KeyboardHandler keyHandler = new KeyboardHandler();
    Thread gameThread;
    Player player = new Player(this, keyHandler, getName(), getName());

    // Player's initial state   
    int playerX = 100; 
    int playerY = 100; 
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.addKeyListener(keyHandler);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps; 
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; 
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
    
}
