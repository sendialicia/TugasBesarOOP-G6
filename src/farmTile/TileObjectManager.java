package farmTile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class TileObjectManager {
    GamePanel gp;
    Map<String, BufferedImage> images = new HashMap<>();
    public TileObject[] tileObjects;

    public TileObjectManager(GamePanel gp){
        this.gp = gp;
        tileObjects = new TileObject[3];
        getTileObjectImages();
    }

    public void getTileObjectImages(){
        UtilityTool uTool = new UtilityTool();
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream("/tiles/farm/Tilled.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            images.put("Tilled", image);

            image = ImageIO.read(getClass().getResourceAsStream("/tiles/farm/Planted.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            images.put("Planted", image);
    
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/farm/Harvestable.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            images.put("Harvestable", image);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        UtilityTool uTool = new UtilityTool();
        for (TileObject tileObject : gp.tiles.values()) {
            if (tileObject == null) continue;

            BufferedImage image = images.get(tileObject.type);

            if (tileObject instanceof PlantedTile plantedTile){
                if (plantedTile.isWateredToday(gp.gameClock.getDate().getOriginDay(), gp.gameClock.getWeather().getWeatherToday().equals("Rainy"))){
                    try {
                        image = ImageIO.read(getClass().getResourceAsStream("/tiles/farm/Watered.png"));
                        image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (image != null) {
                int screenX = tileObject.location.worldX - gp.player.worldX + gp.player.screenX;
                int screenY = tileObject.location.worldY - gp.player.worldY + gp.player.screenY;

                g2.drawImage(image, screenX, screenY, null);
            }
        }
    }

}
