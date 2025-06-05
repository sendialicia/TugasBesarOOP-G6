package object;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class OBJ_Clock extends SuperObject {
    public OBJ_Clock(GamePanel gp, int x, int y) {
        name = "Clock";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/Clock.png"));
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
        solidAreas[0].x = gp.tileSize / 2;
        solidAreas[0].y = gp.tileSize * 3;
        solidAreas[0].width = gp.tileSize * 3 + gp.tileSize / 4;
        solidAreas[0].height = gp.tileSize * 2;

        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}