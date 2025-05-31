package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; 

public class OBJ_House3 extends SuperObject {

    public OBJ_House3(GamePanel gp, int x, int y) {
        name = "House3";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/House3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 5; 
        height = gp.tileSize * 7; 

        solidAreas = new Rectangle[1]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = gp.tileSize * 4; 
        solidAreas[0].width = width; 
        solidAreas[0].height = gp.tileSize * 3; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}