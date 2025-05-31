package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_PineTree extends SuperObject {
    public OBJ_PineTree(GamePanel gp, int x, int y) {
        name = "PineTree";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/PineTree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 3;
        height = gp.tileSize * 6;

        solidAreas = new Rectangle[1];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = gp.tileSize;
        solidAreas[0].y = gp.tileSize * 5;
        solidAreas[0].width = gp.tileSize;
        solidAreas[0].height = gp.tileSize;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}