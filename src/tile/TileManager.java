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
    public String mapName;

    public TileManager(GamePanel gp, String mapName, int worldCol, int worldRow) {

        this.gp = gp;
        this.mapName = mapName;
    
        tile = new Tile[1000];
        mapTileNum = new int[worldCol][worldRow];

        getTileImage();
        loadMap("/maps/" + mapName + ".txt");
    }

    public void getTileImage() {

        // MAP TILES
        if(mapName.equals("farmMap")) {
            setup(287, mapName, "#287", false);
            setup(293, mapName, "#293", false);
            setup(294, mapName, "#294", false);
            setup(333, mapName, "#333", false);
            setup(370, mapName, "#370", false);
            setup(375, mapName, "#375", false);
            setup(433, mapName, "#433", false);
            setup(434, mapName, "#434", false);
            setup(435, mapName, "#435", false);
            setup(465, mapName, "#465", false);
            setup(466, mapName, "#466", false);
            setup(467, mapName, "#467", false);
            setup(574, mapName, "#574", false);
            setup(615, mapName, "#615", false);
            setup(616, mapName, "#616", false);
            setup(617, mapName, "#617", false);
            setup(738, mapName, "#738", false);
            setup(739, mapName, "#739", false);
            setup(740, mapName, "#740", false);
            setup(741, mapName, "#741", false);
            setup(742, mapName, "#742", false);
            setup(743, mapName, "#743", false);
            setup(744, mapName, "#744", false);
            setup(745, mapName, "#745", false);
            setup(746, mapName, "#746", false);
            setup(747, mapName, "#747", false);
            setup(748, mapName, "#748", false);
            setup(749, mapName, "#749", false);
            setup(750, mapName, "#750", false);
            setup(751, mapName, "#751", false);
            setup(752, mapName, "#752", false);
            setup(754, mapName, "#754", false);
            setup(755, mapName, "#755", false);
            setup(756, mapName, "#756", false);
            setup(796, mapName, "#796", false);
            setup(797, mapName, "#797", false);
            setup(837, mapName, "#837", false);
            setup(838, mapName, "#838", false);
            setup(866, mapName, "#866", false);
            setup(867, mapName, "#867", false);
            setup(868, mapName, "#868", false);
            setup(907, mapName, "#907", false);
            setup(908, mapName, "#908", false);
            setup(909, mapName, "#909", false);
            setup(948, mapName, "#948", false);
            setup(949, mapName, "#949", false);
            setup(950, mapName, "#950", false);
        } else if(mapName.equals("worldMap")) {
            setup(1, mapName, "#1", false);
            setup(2, mapName, "#2", false);
            setup(3, mapName, "#3", false);
            setup(4, mapName, "#4", false);
            setup(5, mapName, "#5", false);
            setup(6, mapName, "#6", false);
            setup(7, mapName, "#7", false);
            setup(8, mapName, "#8", false);
            setup(9, mapName, "#9", false);
            setup(10, mapName, "#10", false);
            setup(11, mapName, "#11", false);
            setup(12, mapName, "#12", false);
            setup(13, mapName, "#13", false);
            setup(14, mapName, "#14", false);
            setup(15, mapName, "#15", false);
            setup(16, mapName, "#16", false);
            setup(36, mapName, "#36", false);
            setup(39, mapName, "#39", false);
            setup(64, mapName, "#64", false);
            setup(65, mapName, "#65", false);
            setup(66, mapName, "#66", false);
            setup(67, mapName, "#67", false);
            setup(72, mapName, "#72", false);
            setup(96, mapName, "#96", false);
            setup(98, mapName, "#98", false);
            setup(99, mapName, "#99", false);
            setup(128, mapName, "#128", false);
            setup(131, mapName, "#131", false);
            setup(135, mapName, "#135", false);
            setup(199, mapName, "#199", false);
            setup(247, mapName, "#247", false);
            setup(287, mapName, "#287", false);
            setup(291, mapName, "#291", false);
            setup(328, mapName, "#328", false);
            setup(329, mapName, "#329", false);
            setup(331, mapName, "#331", false);
            setup(333, mapName, "#333", false);
            setup(369, mapName, "#369", false);
            setup(370, mapName, "#370", false);
            setup(372, mapName, "#372", false);
            setup(373, mapName, "#373", false);
            setup(374, mapName, "#374", false);
            setup(401, mapName, "#401", false);
            setup(402, mapName, "#402", false);
            setup(403, mapName, "#403", false);
            setup(410, mapName, "#410", false);
            setup(411, mapName, "#411", false);
            setup(412, mapName, "#412", false);
            setup(433, mapName, "#433", false);
            setup(434, mapName, "#434", false);
            setup(435, mapName, "#435", false);
            setup(465, mapName, "#465", false);
            setup(466, mapName, "#466", false);
            setup(467, mapName, "#467", false);
            setup(497, mapName, "#497", false);
            setup(504, mapName, "#504", false);
            setup(505, mapName, "#505", false);
            setup(506, mapName, "#506", false);
            setup(533, mapName, "#533", false);
            setup(534, mapName, "#534", false);
            setup(535, mapName, "#535", false);
            setup(546, mapName, "#546", false);
            setup(547, mapName, "#547", false);
            setup(574, mapName, "#574", false);
            setup(575, mapName, "#575", false);
            setup(576, mapName, "#576", false);
            setup(593, mapName, "#593", false);
            setup(615, mapName, "#615", false);
            setup(616, mapName, "#616", false);
            setup(617, mapName, "#617", false);
            setup(672, mapName, "#672", false);
            setup(673, mapName, "#673", false);
            setup(674, mapName, "#674", false);
            setup(675, mapName, "#675", false);
            setup(676, mapName, "#676", false);
            setup(677, mapName, "#677", false);
            setup(678, mapName, "#678", false);
            setup(679, mapName, "#679", false);
            setup(680, mapName, "#680", false);
            setup(681, mapName, "#681", false);
            setup(682, mapName, "#682", false);
            setup(683, mapName, "#683", false);
            setup(684, mapName, "#684", false);
            setup(685, mapName, "#685", false);
            setup(686, mapName, "#686", false);
            setup(687, mapName, "#687", false);
            setup(688, mapName, "#688", false);
            setup(689, mapName, "#689", false);
            setup(690, mapName, "#690", false);
            setup(691, mapName, "#691", false);
            setup(692, mapName, "#692", false);
            setup(693, mapName, "#693", false);
            setup(694, mapName, "#694", false);
            setup(695, mapName, "#695", false);
            setup(696, mapName, "#696", false);
            setup(697, mapName, "#697", false);
            setup(698, mapName, "#698", false);
            setup(699, mapName, "#699", false);
            setup(700, mapName, "#700", false);
            setup(701, mapName, "#701", false);
            setup(703, mapName, "#703", false);
            setup(704, mapName, "#704", false);
            setup(705, mapName, "#705", false);
            setup(706, mapName, "#706", false);
            setup(707, mapName, "#707", false);
            setup(708, mapName, "#708", false);
            setup(709, mapName, "#709", false);
            setup(710, mapName, "#710", false);
            setup(711, mapName, "#711", false);
            setup(712, mapName, "#712", false);
            setup(713, mapName, "#713", false);
            setup(714, mapName, "#714", false);
            setup(715, mapName, "#715", false);
            setup(716, mapName, "#716", false);
            setup(717, mapName, "#717", false);
            setup(718, mapName, "#718", false);
            setup(719, mapName, "#719", false);
            setup(720, mapName, "#720", false);
            setup(721, mapName, "#721", false);
            setup(722, mapName, "#722", false);
            setup(724, mapName, "#724", false);
            setup(725, mapName, "#725", false);
            setup(726, mapName, "#726", false);
            setup(727, mapName, "#727", false);
            setup(728, mapName, "#728", false);
            setup(729, mapName, "#729", false);
            setup(730, mapName, "#730", false);
            setup(731, mapName, "#731", false);
            setup(732, mapName, "#732", false);
            setup(733, mapName, "#733", false);
            setup(734, mapName, "#734", false);
            setup(735, mapName, "#735", false);
            setup(736, mapName, "#736", false);
            setup(737, mapName, "#737", false);
        }
    }
    
    public void setup(int index, String mapName, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + mapName + "/" + imageName + ".png"));
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
