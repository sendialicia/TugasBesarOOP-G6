package org.example;

import java.awt.image.BufferedImage;

public class Entity {
    protected int worldX, worldY; 
    protected int speed; 
    protected String name; 

    // public Entity(int x, int y, int speed, String name) {
    //     this.x = x;
    //     this.y = y;
    //     this.speed = speed;
    //     this.name = name;
    // }

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; 

    public String direction;

    public int spriteNum = 1; 
    public int spriteCounter = 0; 
    public int energy = 100; 

    // public void move(int deltaX, int deltaY) {
    //     x += deltaX * speed;
    //     y += deltaY * speed;
    // }

    // public int getX() {
    //     return x;
    // }

    // public int getY() {
    //     return y;
    // }

    // public String getName() {
    //     return name;
    // }   
}
