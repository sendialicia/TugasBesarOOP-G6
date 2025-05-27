package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; // Import GamePanel

public class OBJ_Bin extends SuperObject {

    public OBJ_Bin(GamePanel gp, int x, int y) { // Added GamePanel gp parameter
        name = "Bin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/shippingbin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        // Dimensions in pixels
        width = gp.tileSize * 3; // 3 tiles wide
        height = gp.tileSize * 2; // 2 tiles tall

        // Collision area, relative to the object's top-left corner (worldX, worldY)
        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = width;
        solidArea.height = height;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}