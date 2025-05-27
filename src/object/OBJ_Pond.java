package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; // Import GamePanel

public class OBJ_Pond extends SuperObject {
    public OBJ_Pond(GamePanel gp, int x, int y) { // Added GamePanel gp parameter
        name = "Pond"; // Corrected name
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/pond.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        // Dimensions in pixels
        width = gp.tileSize * 4; // 4 tiles wide
        height = gp.tileSize * 3; // 3 tiles tall (image dimension)

        // Collision area, relative to the object's top-left corner (worldX, worldY)
        solidArea.x = 0;
        solidArea.y = gp.tileSize; // Collision starts from the second tile row (first row is not collision)
        solidArea.width = width;
        solidArea.height = height - gp.tileSize; // Collidable height is 2 tiles (effectively a 4x2 collision box)

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}