package org.example.tile;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.example.main.GamePanel;

public class TileManager {
    GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;

    public int houseTopLeftCol = -1;
    public int houseTopLeftRow = -1;

    public int pondTopLeftCol = -1;
    public int pondTopLeftRow = -1;
    
    public int shippingBinTopLeftCol = -1;
    public int shippingBinTopLeftRow = -1;

    private Random random = new Random();
    
    private static final int LAND = 0;
    private static final int TILLED = 1;
    private static final int PLANTED = 2;
    private static final int HARVESTED = 3;

    private static final int HOUSE = 4;
    private static final int HOUSE_W = 6;
    private static final int HOUSE_H = 6;

    private static final int SHIPPING_BIN = HOUSE + HOUSE_W * HOUSE_H;
    private static final int SHIPPING_BIN_W = 3;
    private static final int SHIPPING_BIN_H = 2;

    private static final int POND = SHIPPING_BIN + SHIPPING_BIN_W * SHIPPING_BIN_H;
    private static final int POND_W = 4;
    private static final int POND_H = 3;

    private static final int TOTAL = POND + POND_W * POND_H;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tiles = new Tile[TOTAL];
        mapTileNum = new int[gp.maxCol][gp.maxRow];
        getTileImage();
        generateMap();
    }

    public void getTileImage(){
        try {
            tiles[LAND] = new Tile();
            tiles[LAND].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/farm/land.png"));
            tiles[LAND].collision = false;

            tiles[TILLED] = new Tile();
            tiles[TILLED].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/farm/tilled.png"));
            tiles[TILLED].collision = false;

            tiles[PLANTED] = new Tile();
            tiles[PLANTED].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/farm/planted.png"));
            tiles[PLANTED].collision = false;

            tiles[HARVESTED] = new Tile();
            tiles[HARVESTED].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/farm/harvestable.png"));
            tiles[HARVESTED].collision = false;

            for (int i = 0; i < HOUSE_W * HOUSE_H; i++) {
                int tileIndex = HOUSE + i;
                tiles[tileIndex] = new Tile();
                tiles[tileIndex].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/house/house.png"));
                tiles[tileIndex].collision = true;
            }

            for (int i = 0; i < SHIPPING_BIN_W * SHIPPING_BIN_H; i++) {
                int tileIndex = SHIPPING_BIN + i;
                tiles[tileIndex] = new Tile();
                tiles[tileIndex].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/shippingbin/shippingbin" + (i + 1) + ".png"));
                tiles[tileIndex].collision = true;
            }

            for (int i = 0; i < POND_W * POND_H; i++) {
                int tileIndex = POND + i;
                tiles[tileIndex] = new Tile();
                tiles[tileIndex].image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/pond/pond" + (i + 1) + ".png"));
                tiles[tileIndex].collision = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkAreaAvailability(int col, int row, int width, int height){
        if (col < 0 || row < 0 || col + width > gp.maxCol || row + height > gp.maxRow) return false;

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (mapTileNum[col + j][row + i] != LAND) return false;
            }
        }
        return true;
    }

    private void fillArea(int col, int row, int width, int height, int index){
        int current = 0;
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                mapTileNum[col + j][row + i] = index + current++;
            }
        }
    }

    public void generateMap(){
        for (int i = 0; i < gp.maxRow; i++){
            for (int j = 0; j < gp.maxCol; j++){
                mapTileNum[j][i] = LAND;
            }
        }

        boolean housePlaced = false;
        while (!housePlaced){
            int houseCol = random.nextInt(gp.maxCol - HOUSE_W + 1);
            int houseRow = random.nextInt(gp.maxRow - HOUSE_H + 1);

            if (checkAreaAvailability(houseCol, houseRow, HOUSE_W, HOUSE_H)){
                fillArea(houseCol, houseRow, HOUSE_W, HOUSE_H, HOUSE);
                this.houseTopLeftCol = houseCol;
                this.houseTopLeftRow = houseRow;
                housePlaced = true;
            }
        }

        boolean binPlaced = false;
        final int gap = 1;
        List<Point> binCoordinates = new ArrayList<>();

        int binRight = this.houseTopLeftCol + HOUSE_W + gap;
        for (int i = this.houseTopLeftRow; i <= this.houseTopLeftRow + HOUSE_H - SHIPPING_BIN_H; i++) {
            binCoordinates.add(new Point(binRight, i));
        }

        int binLeft = this.houseTopLeftCol - SHIPPING_BIN_W - gap;
        for (int i = this.houseTopLeftRow; i <= this.houseTopLeftRow + HOUSE_H - SHIPPING_BIN_H; i++) {
            binCoordinates.add(new Point(binLeft, i));
        }

        int binAbove = this.houseTopLeftRow - SHIPPING_BIN_H - gap;
        for (int j = this.houseTopLeftCol; j <= this.houseTopLeftCol + HOUSE_W - SHIPPING_BIN_W; j++) {
            binCoordinates.add(new Point(j, binAbove));
        }

        int binBelow = this.houseTopLeftRow + HOUSE_H + gap;
        for (int j = this.houseTopLeftCol; j <= this.houseTopLeftCol + HOUSE_W - SHIPPING_BIN_W; j++) {
            binCoordinates.add(new Point(j, binBelow));
        }

        Collections.shuffle(binCoordinates);

        for (Point point : binCoordinates){
            if (checkAreaAvailability(point.x, point.y, SHIPPING_BIN_W, SHIPPING_BIN_H)) {
                fillArea(point.x, point.y, SHIPPING_BIN_W, SHIPPING_BIN_H, SHIPPING_BIN);
                this.shippingBinTopLeftCol = point.x;
                this.shippingBinTopLeftRow = point.y;
                binPlaced = true;
                break;
            }
        }

        boolean pondPlaced = false;
        while (!pondPlaced){
            int pondCol = random.nextInt(gp.maxCol - POND_W + 1);
            int pondRow = random.nextInt(gp.maxRow - POND_H + 1);

            if (checkAreaAvailability(pondCol, pondRow, POND_W, POND_H)){
                fillArea(pondCol, pondRow, POND_W, POND_H, POND);
                this.pondTopLeftCol = pondCol;
                this.pondTopLeftRow = pondRow;
                pondPlaced = true;
            }
        }
    }

    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxCol && row < gp.maxRow){
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tiles[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxCol){
                col = 0;
                x = 0;
                y += gp.tileSize;
                row++;
            }
        }

    }
}
