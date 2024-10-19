package snakeGame;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel gameOverLabel;
    private JLabel puntajeLabel;
    private JButton restartButton;
    private JPanel contentPane;
    private JButton menuButton;
    private JPanel buttonPanel;

    public GameOverPanel(CardLayout cardLayout, JPanel contentPane, GameFrame gameFrame) {
        setBackground(new Color(0, 0, 0, 0));
        this.contentPane = contentPane;
        setSize(600, 600);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
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

        add(centerPanel);
        
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 0, 0));
        add(buttonPanel);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        menuButton = new JButton("Volver a jugar");
        menuButton.setForeground(new Color(255, 255, 255));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(menuButton);
        menuButton.setBackground(new Color(0, 128, 192));
        menuButton.setFont(new Font("Power Red and Blue", Font.PLAIN, 25));
        menuButton.addActionListener(e -> restartGame(gameFrame));
        
        restartButton = new JButton("Regresar al Menú");
        buttonPanel.add(restartButton);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(128, 0, 0));
        restartButton.setFont(new Font("Power Red and Blue", Font.PLAIN, 25));
        restartButton.addActionListener(e -> returnToMenu());

    }
    
    public void actualizarPuntaje(int puntaje) {
        puntajeLabel.setText("Puntaje: " + puntaje);
    }
    
    private void restartGame(GameFrame gameFrame) {
        GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1); // Accede a GameBoardSnake
        GameBoardWithBoss gameBoss = (GameBoardWithBoss) contentPane.getComponent(7); // Accede a GameBoardWithBoss
        
        if (gameFrame.opcionIniciar == 1) {
            CardLayout layout = (CardLayout) contentPane.getLayout();
            layout.show(contentPane, "gameBoard"); // Cambia a la pantalla de GameBoardSnake
            
            gameBoard.reiniciarJuego(); 
            gameBoard.requestFocusInWindow(); 
            gameBoard.setFocusable(true);
            if (gameBoard.isTimerRunning()) {
                gameBoard.stopTimer();
            }
            gameBoard.startTimer();
        } else if (gameFrame.opcionIniciar == 2) {
            CardLayout layout2 = (CardLayout) contentPane.getLayout();
            layout2.show(contentPane, "gameBoardWithBoss"); // Cambia a la pantalla de GameBoardWithBoss
            
            gameBoss.reiniciarJuego();
            gameBoss.requestFocusInWindow();
            gameBoss.setFocusable(true);
            if (gameBoss.isTimerRunning()) {
                gameBoss.stopTimer();
            }
            gameBoss.startTimer();
        }
    }

    private void returnToMenu() {
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, "mainMenu");
        GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1);
        gameBoard.reiniciarJuego();
        GameBoardWithBoss gameBoss = (GameBoardWithBoss) contentPane.getComponent(7);
        gameBoss.reiniciarJuego();
    }

}