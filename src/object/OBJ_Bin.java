package object;

import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel; 

public class OBJ_Bin extends SuperObject {

    public OBJ_Bin(GamePanel gp, int x, int y) { 
        name = "Bin";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/ShippingBin.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        worldX = x;
        worldY = y;

        width = gp.tileSize * 3; 
        height = gp.tileSize * 2; 

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = width;
        solidArea.height = height;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}