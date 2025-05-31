package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_GreenTree2 extends SuperObject {
    public OBJ_GreenTree2(GamePanel gp, int x, int y) {
        name = "GreenTree2";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/GreenTree2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 4;
        height = gp.tileSize * 7;

        solidAreas = new Rectangle[1];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 1;
        solidAreas[0].y = gp.tileSize * 5;
        solidAreas[0].width = gp.tileSize * 2;
        solidAreas[0].height = gp.tileSize * 2;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}