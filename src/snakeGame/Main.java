package snakeGame;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GameFrame frame = new GameFrame();
                frame.setVisible(true); // Haz visible el JFrame
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
