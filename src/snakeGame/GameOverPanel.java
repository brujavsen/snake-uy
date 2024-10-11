package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameOverPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel gameOverLabel;
    private JLabel puntajeLabel;
    private JButton restartButton;
    
    public GameOverPanel(CardLayout cardLayout, JPanel contentPane) {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        
        gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setFont(new Font("Power Red and Blue", Font.BOLD, 50));
        
        JPanel centerPanel = new JPanel();
        centerPanel.setForeground(Color.WHITE);
        centerPanel.setBackground(Color.BLACK);
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.add(gameOverLabel);
        

    	puntajeLabel = new JLabel("", SwingConstants.CENTER);
        puntajeLabel.setForeground(Color.WHITE);
        puntajeLabel.setFont(new Font("Power Red and Blue", Font.BOLD, 30));
        centerPanel.add(puntajeLabel);

        add(centerPanel, BorderLayout.CENTER);
        
        restartButton = new JButton("Regresar al Menú");
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(Color.RED);
        restartButton.setFont(new Font("Power Red and Blue", Font.PLAIN, 30));
        restartButton.addActionListener(e -> {
            CardLayout layout = (CardLayout) contentPane.getLayout();
            layout.show(contentPane, "mainMenu");

            GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1);
            gameBoard.reiniciarJuego();
        });

        add(restartButton, BorderLayout.SOUTH);
    }
    
    public void actualizarPuntaje(int puntaje) {
        puntajeLabel.setText("Puntaje: " + puntaje);
    }
    
}
