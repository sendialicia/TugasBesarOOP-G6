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

        tile = new Tile[1000];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/farmMap.txt");
    }

    public void getTileImage() {

        // MAP TILES
        setup(287, "#287", false);
        setup(293, "#293", false);
        setup(294, "#294", false);
        setup(333, "#333", false);
        setup(370, "#370", false);
        setup(375, "#375", false);
        setup(433, "#433", false);
        setup(434, "#434", false);
        setup(435, "#435", false);
        setup(465, "#465", false);
        setup(466, "#466", false);
        setup(467, "#467", false);
        setup(574, "#574", false);
        setup(615, "#615", false);
        setup(616, "#616", false);
        setup(617, "#617", false);
        setup(738, "#738", false);
        setup(739, "#739", false);
        setup(740, "#740", false);
        setup(741, "#741", false);
        setup(742, "#742", false);
        setup(743, "#743", false);
        setup(744, "#744", false);
        setup(745, "#745", false);
        setup(746, "#746", false);
        setup(747, "#747", false);
        setup(748, "#748", false);
        setup(749, "#749", false);
        setup(750, "#750", false);
        setup(751, "#751", false);
        setup(752, "#752", false);
        setup(754, "#754", false);
        setup(755, "#755", false);
        setup(756, "#756", false);
        setup(796, "#796", false);
        setup(797, "#797", false);
        setup(837, "#837", false);
        setup(838, "#838", false);
        setup(866, "#866", false);
        setup(867, "#867", false);
        setup(868, "#868", false);
        setup(907, "#907", false);
        setup(908, "#908", false);
        setup(909, "#909", false);
        setup(948, "#948", false);
        setup(949, "#949", false);
        setup(950, "#950", false);
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
