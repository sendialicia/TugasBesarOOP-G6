package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel; 

public class OBJ_House4 extends SuperObject {

    public OBJ_House4(GamePanel gp, int x, int y) {
        name = "House4";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/House4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 9; 
        height = gp.tileSize * 8; 

        solidAreas = new Rectangle[1]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = gp.tileSize * 5; 
        solidAreas[0].width = width; 
        solidAreas[0].height = gp.tileSize * 3; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}