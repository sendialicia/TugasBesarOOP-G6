package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = true;
    public int worldX, worldY;
    public int width, height;

    public Rectangle[] solidAreas; 
    public int solidAreaDefaultX = 0; 
    public int solidAreaDefaultY = 0; 

    public SuperObject() {
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        
        if (worldX + width > gp.player.worldX - gp.player.screenX - gp.tileSize && 
            worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
            worldY + height > gp.player.worldY - gp.player.screenY - gp.tileSize && 
            worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {

            g2.drawImage(image, screenX, screenY, width, height, null);
        }
    }

    public void draw(Graphics2D g2, GamePanel gp, int sx1, int sy1, int cropWidth, int cropHeight, int displayWidth, int displayHeight) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        if (worldX + width > gp.player.worldX - gp.player.screenX - gp.tileSize * 2 &&
            worldX < gp.player.worldX + gp.player.screenX + gp.tileSize * 2 &&
            worldY + height > gp.player.worldY - gp.player.screenY - gp.tileSize * 2 &&
            worldY < gp.player.worldY + gp.player.screenY + gp.tileSize * 2) {

            int sx2 = sx1 + cropWidth;  
            int sy2 = sy1 + cropHeight; 

            g2.drawImage(image,
                        screenX, screenY, screenX + displayWidth, screenY + displayHeight, 
                        sx1, sy1, sx2, sy2,                                          
                        null);

            g2.setColor(java.awt.Color.RED);
            if (solidAreas != null) {
                for (Rectangle rect : solidAreas) {
                    g2.drawRect(screenX + rect.x, screenY + rect.y, rect.width, rect.height);
                }
            }
        }
    }

}