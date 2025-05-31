package tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];

    public TileManager(GamePanel gp, int worldCol, int worldRow) {

        this.gp = gp;
    
        tile = new Tile[1000];
        mapTileNum = new int[gp.maxMap][worldCol][worldRow];

        getTileImage();
        if(gp.currentMap == 0) {
            loadMap("/maps/farm.txt", 0);
        } else if(gp.currentMap == 1) {
            loadMap("/maps/world.txt", 1);
        }
    }

    public void getTileImage() {

        // MAP TILES
        setup(101, "#101", true);
        setup(102, "#102", true);
        setup(103, "#103", true);
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
        setup(533, "#533", false);
        setup(534, "#534", false);
        setup(535, "#535", false);
        setup(574, "#574", false);
        setup(575, "#575", false);
        setup(576, "#576", false);
        setup(615, "#615", false);
        setup(616, "#616", false);
        setup(617, "#617", false);
        setup(671, "#671", false);
        setup(700, "#700", true);
        setup(738, "#738", true);
        setup(739, "#739", true);
        setup(740, "#740", true);
        setup(741, "#741", true);
        setup(742, "#742", true);
        setup(743, "#743", true);
        setup(744, "#744", true);
        setup(745, "#745", true);
        setup(746, "#746", true);
        setup(747, "#747", true);
        setup(748, "#748", true);
        setup(749, "#749", true);
        setup(750, "#750", true);
        setup(751, "#751", true);
        setup(752, "#752", true);
        setup(754, "#754", true);
        setup(755, "#755", true);
        setup(756, "#756", true);
        setup(796, "#796", true);
        setup(797, "#797", true);
        setup(837, "#837", true);
        setup(838, "#838", true);
        setup(866, "#866", false);
        setup(867, "#867", false);
        setup(868, "#868", false);
        setup(907, "#907", false);
        setup(908, "#908", false);
        setup(909, "#909", false);
        setup(948, "#948", false);
        setup(949, "#949", false);
        setup(950, "#950", false);
        setup(951, "#951", false);
        setup(998, "#998", false);
        setup(999, "#999", false);
        setup(1, "#1", false);
        setup(2, "#2", false);
        setup(3, "#3", false);
        setup(4, "#4", false);
        setup(5, "#5", false);
        setup(6, "#6", false);
        setup(7, "#7", false);
        setup(8, "#8", false);
        setup(9, "#9", false);
        setup(10, "#10", false);
        setup(11, "#11", false);
        setup(12, "#12", false);
        setup(13, "#13", false);
        setup(14, "#14", false);
        setup(15, "#15", false);
        setup(16, "#16", false);
        setup(36, "#36", false);
        setup(39, "#39", false);
        setup(64, "#64", false);
        setup(65, "#65", false);
        setup(66, "#66", false);
        setup(67, "#67", false);
        setup(72, "#72", false);
        setup(96, "#96", false);
        setup(98, "#98", false);
        setup(99, "#99", false);
        setup(128, "#128", false);
        setup(131, "#131", false);
        setup(135, "#135", false);
        setup(199, "#199", false);
        setup(247, "#247", false);
        setup(291, "#291", false);
        setup(328, "#328", false);
        setup(329, "#329", false);
        setup(330, "#330", false);
        setup(331, "#331", false);
        setup(369, "#369", false);
        setup(372, "#372", false);
        setup(373, "#373", false);
        setup(374, "#374", false);
        setup(401, "#401", false);
        setup(402, "#402", false);
        setup(403, "#403", false);
        setup(410, "#410", false);
        setup(411, "#411", false);
        setup(412, "#412", false);
        setup(413, "#413", false);
        setup(497, "#497", false);
        setup(504, "#504", false);
        setup(505, "#505", false);
        setup(506, "#506", false);
        setup(546, "#546", false);
        setup(547, "#547", false);
        setup(593, "#593", true);
        setup(672, "#672", true);
        setup(673, "#673", true);
        setup(674, "#674", true);
        setup(675, "#675", true);
        setup(676, "#676", true);
        setup(677, "#677", true);
        setup(678, "#678", true);
        setup(679, "#679", true);
        setup(680, "#680", true);
        setup(681, "#681", true);
        setup(682, "#682", true);
        setup(683, "#683", true);
        setup(684, "#684", true);
        setup(685, "#685", true);
        setup(686, "#686", true);
        setup(687, "#687", true);
        setup(688, "#688", true);
        setup(689, "#689", true);
        setup(690, "#690", true);
        setup(691, "#691", true);
        setup(692, "#692", true);
        setup(693, "#693", true);
        setup(694, "#694", true);
        setup(695, "#695", true);
        setup(696, "#696", true);
        setup(697, "#697", true);
        setup(698, "#698", true);
        setup(699, "#699", true);
        setup(700, "#700", true);
        setup(701, "#701", true);
        setup(703, "#703", true);
        setup(704, "#704", true);
        setup(705, "#705", true);
        setup(706, "#706", true);
        setup(707, "#707", true);
        setup(708, "#708", true);
        setup(709, "#709", true);
        setup(710, "#710", true);
        setup(711, "#711", true);
        setup(712, "#712", true);
        setup(713, "#713", true);
        setup(714, "#714", true);
        setup(715, "#715", true);
        setup(716, "#716", true);
        setup(717, "#717", true);
        setup(718, "#718", true);
        setup(719, "#719", true);
        setup(720, "#720", true);
        setup(721, "#721", true);
        setup(722, "#722", true);
        setup(724, "#724", true);
        setup(725, "#725", true);
        setup(726, "#726", true);
        setup(727, "#727", true);
        setup(728, "#728", true);
        setup(729, "#729", true);
        setup(730, "#730", true);
        setup(731, "#731", true);
        setup(732, "#732", true);
        setup(733, "#733", true);
        setup(734, "#734", true);
        setup(735, "#735", true);
        setup(736, "#736", true);
        setup(737, "#737", true);
    }
    
    public void setup(int index, String imageName, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = new UtilityTool().scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

            if (collision) {
                tile[index].solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
            }

        } catch (IOException e) {
            System.err.println("Gagal load tile: " + imageName);
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath, int map) {
        System.out.println("Loading map: " + filePath);
        System.out.println("Expecting " + gp.maxWorldCol + " columns and " + gp.maxWorldRow + " rows");

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.maxWorldRow; row++) {
                String line = br.readLine();

                if (line == null) {
                    System.out.println("Baris kosong/tidak cukup pada row " + row);
                    break;
                }

                String[] numbers = line.trim().split("\\s+");

                if (numbers.length != gp.maxWorldCol) {
                    System.err.println("Invalid column count at row " + row + ": expected " + gp.maxWorldCol + ", got " + numbers.length);
                    continue;
                }

                for (int col = 0; col < gp.maxWorldCol; col++) {
                    try {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[map][col][row] = num;
                    } catch (NumberFormatException e) {
                        System.err.println("Angka tidak valid di row " + row + ", col " + col + ": '" + numbers[col] + "'");
                        mapTileNum[map][col][row] = 0;
                    }
                }
            }

            br.close();

        } catch (Exception e) {
            System.err.println("Gagal membaca file map: " + filePath);
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        int tileSize = gp.tileSize;

        for (int row = 0; row < gp.maxWorldRow; row++) {
            for (int col = 0; col < gp.maxWorldCol; col++) {
                int tileNum = mapTileNum[gp.currentMap][col][row];

                int worldX = col * tileSize;
                int worldY = row * tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                if (worldX + tileSize > gp.player.worldX - gp.player.screenX &&
                    worldX - tileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + tileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - tileSize < gp.player.worldY + gp.player.screenY &&
                    tile[tileNum] != null) {

                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            }
        }
    }
}