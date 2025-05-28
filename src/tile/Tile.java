package tile;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

}