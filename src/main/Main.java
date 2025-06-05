package main;

import javax.swing.JFrame;

import items.ItemFactory;
import time.GameClock;

public class Main {
    public static void main(String[] args) {
        
        ItemFactory.loadAll();
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); // cause window to be sized to fit preferred size and layouts of its subcomponents (GamePanel)

        window.setLocationRelativeTo(null); // Display window at center of screen
        window.setVisible(true);

        new Thread(GameClock.getInstance()).start();

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
 