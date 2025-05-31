package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; 

public class OBJ_House1 extends SuperObject {

    public OBJ_House1(GamePanel gp, int x, int y) {
        name = "House1";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/House1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 8; 
        height = gp.tileSize * 11; 

        solidAreas = new Rectangle[1]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = gp.tileSize * 7; 
        solidAreas[0].width = width; 
        solidAreas[0].height = gp.tileSize * 4; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}