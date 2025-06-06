package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Water extends SuperObject {
    public OBJ_Water(GamePanel gp, int x, int y) {
        name = "Water";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Water.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 2;
        height = gp.tileSize * 2;

        solidAreas = new Rectangle[1];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0;
        solidAreas[0].y = gp.tileSize;
        solidAreas[0].width = width;
        solidAreas[0].height = gp.tileSize;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}