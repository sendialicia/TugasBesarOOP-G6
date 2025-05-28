package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import java.awt.Rectangle; 

public class OBJ_House extends SuperObject {

    public OBJ_House(GamePanel gp, int x, int y) {
        name = "House";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/House.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 6; 
        height = gp.tileSize * 8; 

        solidAreas = new Rectangle[3]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = 0; 
        solidAreas[0].width = gp.tileSize; 
        solidAreas[0].height = gp.tileSize; 

        solidAreas[1] = new Rectangle();
        solidAreas[1].x = 0; 
        solidAreas[1].y = gp.tileSize; 
        solidAreas[1].width = width; 
        solidAreas[1].height = gp.tileSize * 6; 

        solidAreas[2] = new Rectangle();
        solidAreas[2].x = 0; 
        solidAreas[2].y = gp.tileSize * 7; 
        solidAreas[2].width = gp.tileSize; 
        solidAreas[2].height = gp.tileSize; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}