package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel; 

public class OBJ_WaterFountain extends SuperObject {

    public OBJ_WaterFountain(GamePanel gp, int x, int y) {
        name = "WaterFountain";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/WaterFountain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 5; 
        height = gp.tileSize * 5; 

        solidAreas = new Rectangle[1]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = 0; 
        solidAreas[0].width = width; 
        solidAreas[0].height = height; 


        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}