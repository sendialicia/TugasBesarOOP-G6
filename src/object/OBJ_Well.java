package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Well extends SuperObject {
    public OBJ_Well(GamePanel gp, int x, int y) {
        name = "Well";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Well.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 4;
        height = gp.tileSize * 5;

        solidAreas = new Rectangle[1];

        solidAreas[0] = new Rectangle();
        solidAreas[0].x = 0;
        solidAreas[0].y = gp.tileSize * 2;
        solidAreas[0].width = width - gp.tileSize / 3;
        solidAreas[0].height = gp.tileSize * 3;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}