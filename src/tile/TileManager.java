package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Rectangle;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[50];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/farmMap.txt");
    }

    public void getTileImage() {

        // MAP TILES
        setup(10, "Tillable", false);
        setup(11, "DeepWater", true);
        setup(12, "Water", true);
        setup(13, "Brick", false);
        setup(14, "BridgeCoverBrick", true);
        setup(15, "RightEdgeWater", true);
        setup(16, "FrontEdge", false);
        setup(17, "Ground", true);
        setup(18, "Bridge", false);
        setup(19, "LeftEdgeWater", true);
        setup(20, "RightEdgeDeepWater", true);
        setup(21, "LeftEdgeDeepWater", true);
        setup(22, "RightEdgeGround", true);
        setup(23, "LeftEdgeGround", true);
    }

    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
            if(collision) {
                tile[index].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is= getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                
                String line = br.readLine();

                while(col < gp.maxWorldCol) {

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {}
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize; 
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && 
               worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
               worldY > gp.player.worldY - gp.player.screenY - gp.tileSize && 
               worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }

            worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
