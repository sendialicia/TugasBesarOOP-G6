package org.example.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import javax.swing.JPanel;

import org.example.entity.Player;
import org.example.tile.TileManager;
import org.example.time.GameClock;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16;
    final double scale = 1.5;

    public final int tileSize = (int) (originalTileSize * scale);
    public final int maxCol = 32;
    public final int maxRow = 32;

    final int screenWidth = tileSize * maxCol;
    final int screenHeight = tileSize * maxRow;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    Player player = new Player(this, keyH);

    private Font pixelFont;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);

        loadFont();
    }

    private void loadFont() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/pixelfont.ttf");
            if (is != null) {
                pixelFont = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(pixelFont);
            } else {
                System.out.println("Pixel font not found!");
                pixelFont = new Font("Monospaced", Font.PLAIN, 24); // fallback
            }
        } catch (Exception e) {
            e.printStackTrace();
            pixelFont = new Font("Monospaced", Font.PLAIN, 24); // fallback
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;

        long lastTime = System.nanoTime();
        long currentTime;
        int timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS : " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);
        drawGameTime(g2);

        g2.dispose();
    }

    private void drawGameTime(Graphics2D g2) {
        String timeStr = GameClock.getInstance().getFormattedTime();
        g2.setFont(pixelFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Time: " + timeStr, 20, 40);
    }
}
