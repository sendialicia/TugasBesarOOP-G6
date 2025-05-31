package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel; 

public class OBJ_Store extends SuperObject {

    public OBJ_Store(GamePanel gp, int x, int y) {
        name = "Store";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Store.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 12; 
        height = gp.tileSize * 10; 

        solidAreas = new Rectangle[4]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 1; 
        solidAreas[0].y = gp.tileSize * 5; 
        solidAreas[0].width = gp.tileSize * 10; 
        solidAreas[0].height = gp.tileSize * 4; 

        solidAreas[1] = new Rectangle();
        solidAreas[1].x = 0; 
        solidAreas[1].y = gp.tileSize * 9; 
        solidAreas[1].width = gp.tileSize; 
        solidAreas[1].height = gp.tileSize; 

        solidAreas[2] = new Rectangle();
        solidAreas[2].x = gp.tileSize * 11; 
        solidAreas[2].y = gp.tileSize * 9; 
        solidAreas[2].width = gp.tileSize; 
        solidAreas[2].height = gp.tileSize; 

        solidAreas[3] = new Rectangle();
        solidAreas[3].x = gp.tileSize * 7; 
        solidAreas[3].y = gp.tileSize * 9; 
        solidAreas[3].width = gp.tileSize * 3; 
        solidAreas[3].height = gp.tileSize * 2; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}