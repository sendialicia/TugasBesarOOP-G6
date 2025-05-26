package main;

import javax.swing.JFrame;

import entity.PlayerAttribute;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Adventure");
        PlayerAttribute pa = new PlayerAttribute(null, null, null, null);

        GamePanel gamePanel = new GamePanel(pa);
        window.add(gamePanel);

        window.pack(); // cause window to be sized to fit preferred size and layouts of its subcomponents (GamePanel)

        window.setLocationRelativeTo(null); // Display window at center of screen
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
 