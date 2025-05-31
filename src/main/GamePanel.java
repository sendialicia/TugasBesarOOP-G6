package main;

import entity.Player;
import entity.npc.NPC;
import farmTile.HarvestableTile;
import farmTile.PlantedTile;
import farmTile.TileLocation;
import farmTile.TileObject;
import farmTile.TileObjectManager;
import items.fish.Fish;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

import items.Inventory;
import object.SuperObject;
import tile.TileManager;
import time.GameClock;

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
    public int maxWorldCol = 51;
    public int maxWorldRow = 51;
    public final int maxMap = 10;
    public int currentMap = 0;


    // FPS
    int FPS = 60;

    // SYSTEM
    public GameClock gameClock = GameClock.getInstance();
    public TileManager tileM = new TileManager(this, maxWorldCol, maxWorldRow);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[][] = new SuperObject[maxMap][40];
    public NPC npc[][] = new NPC[maxMap][10];
    public TileObjectManager tileOM = new TileObjectManager(this);
    public Map<TileLocation, TileObject> tiles = new HashMap<>();
    public Fish fished = null;
    public Integer luckyNumber = null;
    public Inventory binShopInventory = new Inventory(false);
    public Integer interactedNPC = null;

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
    public final int binShopState = 9;
    public final int plantSeedState = 10;
    
    
    public final int binAmountState = 12;

    public final int houseInteractState = 20;
    public final int fishingInteractState = 21;
    public final int fishingSucceeded = 22;
    public final int fishingFailed = 23;
    public final int binInteractState = 24;

    public final int watchingState = 25;

    public final int worldMapState = 26;
    public final int sleepingState = 27;
    public final int moveMapState = 28;

    public final int interactNPCState = 29;
    public final int rejectedState = 30;
    public final int tooSoonState = 31;
    public final int havePartnerState = 32;
    public final int yourPartnerState = 33;
    public final int acceptedState = 34;


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

        while(gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if(timer >= 1000000000) {
                timer = 0;
            }

        }
    }

    public void update(){
        if(gameState == playState || gameState == worldMapState) {

            // PLAYER
            player.update();

            // NPC
            for(int i = 0; i < npc[1].length; i++) {
                if(npc[currentMap][i] != null) {
                    npc[currentMap] [i].update();
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
            ui.draw(g2);
        } else if (gameState == farmNameInputState) {
            ui.draw(g2);
        } else if (gameState == nameInputState) {
            ui.draw(g2);
        } else if (gameState == genderInputState) {
            ui.draw(g2);
        } else { 
            if (gameState == playState || gameState == pauseState || gameState == dialogueState || gameState == viewAttributeState || gameState == houseInteractState || 
                gameState == fishingInteractState || gameState == binInteractState || gameState == watchingState || gameState == worldMapState || gameState == binShopState ||
                gameState == binAmountState || gameState == interactNPCState || gameState == rejectedState || gameState == tooSoonState || gameState == havePartnerState || 
                gameState == yourPartnerState || gameState == acceptedState | gameState == plantSeedState) { 
                
                // TILE
                tileM.draw(g2);

                refreshLand();
                tileOM.draw(g2);

                // OBJECT
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }
        
                // NPC
                for(int i = 0; i < npc[1].length; i++) {
                    if(npc[currentMap][i] != null) {
                        npc[currentMap][i].draw(g2);
                    }
                }

                // PLAYER
                player.draw(g2);

                // HOUSE FRONT LAYER
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null && obj[currentMap][i].name.equals("House")) {
                        obj[currentMap][i].draw(g2, this, 0, 0, 96, this.tileSize, this.tileSize * 6, this.tileSize * 3);
                    }
                }
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null && obj[currentMap][i].name.equals("PinkTree")) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null && obj[currentMap][i].name.equals("Well")) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null && obj[currentMap][i].name.equals("Bush")) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null && obj[currentMap][i].name.equals("GreenTree1")) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }
                for(int i = 0; i < obj[1].length; i++) {
                    if(obj[currentMap][i] != null && obj[currentMap][i].name.equals("Clock")) {
                        obj[currentMap][i].draw(g2, this);
                    }
                }
            }
            

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

    public void refreshLand() {
        Map<TileLocation, TileObject> updatedTiles = new HashMap<>();
        List<TileLocation> tilesToRemove = new ArrayList<>();

        int currentDay = gameClock.getDate().getOriginDay();
        boolean isRainy = gameClock.getWeather().getWeatherToday().equals("Rainy");

        for (Map.Entry<TileLocation, TileObject> entry : tiles.entrySet()) {
            TileLocation location = entry.getKey();
            TileObject tileObject = entry.getValue();

            if (tileObject instanceof PlantedTile plantedTile) {
                plantedTile.update(currentDay, isRainy);

                if (plantedTile.isNeglected(currentDay, isRainy)) {
                    tilesToRemove.add(location);
                    continue;
                }

                if (plantedTile.isReadyToHarvest()) updatedTiles.put(location, new HarvestableTile(plantedTile));
            }
        }

        for (TileLocation location : tilesToRemove) tiles.remove(location);
        for (Map.Entry<TileLocation, TileObject> entry : updatedTiles.entrySet()) tiles.put(entry.getKey(), entry.getValue());
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
