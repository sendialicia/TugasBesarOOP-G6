package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_House extends SuperObject {

    public OBJ_House(GamePanel gp, int x, int y) {
        name = "House";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/House.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 6;
        height = gp.tileSize * 7;
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = width;
        solidArea.height = height - gp.tileSize;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}