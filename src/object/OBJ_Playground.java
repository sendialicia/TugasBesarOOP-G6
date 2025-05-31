package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel; 

public class OBJ_Playground extends SuperObject {

    public OBJ_Playground(GamePanel gp, int x, int y) {
        name = "Playground";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Playground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true; 
        worldX = x; 
        worldY = y; 

        width = gp.tileSize * 10; 
        height = gp.tileSize * 5; 

        solidAreas = new Rectangle[4]; 

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0; 
        solidAreas[0].y = gp.tileSize * 4; 
        solidAreas[0].width = gp.tileSize; 
        solidAreas[0].height = gp.tileSize; 

        solidAreas[1] = new Rectangle();
        solidAreas[1].x = 1; 
        solidAreas[1].y = gp.tileSize * 3; 
        solidAreas[1].width = gp.tileSize; 
        solidAreas[1].height = gp.tileSize; 

        solidAreas[2] = new Rectangle();
        solidAreas[2].x = 3; 
        solidAreas[2].y = gp.tileSize * 3; 
        solidAreas[2].width = gp.tileSize; 
        solidAreas[2].height = gp.tileSize; 

        solidAreas[3] = new Rectangle();
        solidAreas[3].x = gp.tileSize * 5; 
        solidAreas[3].y = gp.tileSize * 0; 
        solidAreas[3].width = gp.tileSize * 5; 
        solidAreas[3].height = gp.tileSize * 5; 

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}