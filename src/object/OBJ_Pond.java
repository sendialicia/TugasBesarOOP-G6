package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Pond extends SuperObject {
    public OBJ_Pond(GamePanel gp, int x, int y) {
        name = "Pond";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Pond.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 4;
        height = gp.tileSize * 4;

        solidAreas = new Rectangle[3];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0;
        solidAreas[0].y = 0;
        solidAreas[0].width = gp.tileSize;
        solidAreas[0].height = gp.tileSize;

        solidAreas[1] = new Rectangle();
        solidAreas[1].x = gp.tileSize * 3;
        solidAreas[1].y = 0;
        solidAreas[1].width = gp.tileSize;
        solidAreas[1].height = gp.tileSize;

        solidAreas[2] = new Rectangle();
        solidAreas[2].x = 0;
        solidAreas[2].y = gp.tileSize;
        solidAreas[2].width = width;
        solidAreas[2].height = gp.tileSize * 3;
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}