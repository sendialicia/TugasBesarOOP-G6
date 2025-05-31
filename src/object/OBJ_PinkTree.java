package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_PinkTree extends SuperObject {
    public OBJ_PinkTree(GamePanel gp, int x, int y) {
        name = "PinkTree";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/PinkTree.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 6;
        height = gp.tileSize * 7;

        solidAreas = new Rectangle[1];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = gp.tileSize * 2;
        solidAreas[0].y = gp.tileSize * 5;
        solidAreas[0].width = gp.tileSize * 2;
        solidAreas[0].height = gp.tileSize * 2;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}