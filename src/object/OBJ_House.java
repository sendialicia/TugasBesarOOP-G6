package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; // Import GamePanel

public class OBJ_House extends SuperObject {

    public OBJ_House(GamePanel gp, int x, int y) { // Added GamePanel gp parameter
        name = "House";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/house.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        // Dimensions in pixels
        width = gp.tileSize * 6; // 6 tiles wide
        height = gp.tileSize * 7; // 7 tiles tall

        // Collision area, relative to the object's top-left corner (worldX, worldY)
        solidArea.x = 0; // Relative to object's worldX
        solidArea.y = 0; // Relative to object's worldY
        solidArea.width = width; // Full width collision
        solidArea.height = height - gp.tileSize; // Collision for the first 6 rows (7th row is not collision)

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}