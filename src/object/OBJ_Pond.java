package object;

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
        height = gp.tileSize * 3; 

        solidArea.x = 0;
        solidArea.y = gp.tileSize;
        solidArea.width = width;
        solidArea.height = height - gp.tileSize;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}