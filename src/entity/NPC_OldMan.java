package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {
        super(gp);
        
        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    // public void getImage() {
    //     up1 = setup("/npc/WALIDDD/oldman_up_left");
    //     up2 = setup("/npc/WALIDDD/oldman_up_right");
    //     down1 = setup("/npc/WALIDDD/oldman_down_left");
    //     down2 = setup("/npc/WALIDDD/oldman_down_right");
    //     left1 = setup("/npc/WALIDDD/oldman_left");
    //     left2 = setup("/npc/WALIDDD/oldman_walk_left");
    //     right1 = setup("/npc/WALIDDD/oldman_right");
    //     right2 = setup("/npc/WALIDDD/oldman_walk_right");
    // }

    public void getImage(){
        up1 = setup("/npc/Mayted/images/mayted_6");
        up2 = setup("/npc/Mayted/images/mayted_7");
        down1 = setup("/npc/Mayted/images/mayted_0");
        down2 = setup("/npc/Mayted/images/mayted_1");
        left1 = setup("/npc/Mayted/images/mayted_4");
        left2 = setup("/npc/Mayted/images/mayted_5");
        right1 = setup("/npc/Mayted/images/mayted_2");
        right2 = setup("/npc/Mayted/images/mayted_3");
    }

    public void setDialogue() {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So you've come to this island to \nfind the treasure?";
        dialogues[2] = "I used to be a great wizard but now... \nI'm a bit too old for taking an adventure.";
        dialogues[3] = "Well, good luck on you.";
    }

    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 120) {    
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick a number from 1 to 100
    
            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    public void speak() {

        // Do this character specific stuff

        super.speak();
    }
}