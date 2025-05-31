package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Bush extends SuperObject {
    public OBJ_Bush(GamePanel gp, int x, int y) {
        name = "Bush";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Bush.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 3;
        height = gp.tileSize * 3;

        solidAreas = new Rectangle[1];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0;
        solidAreas[0].y = gp.tileSize;
        solidAreas[0].width = width;
        solidAreas[0].height = gp.tileSize * 2;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}