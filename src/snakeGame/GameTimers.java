package snakeGame;

import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimers {
    protected Timer gameTimer;
    protected Timer respawnTimer;
    protected Timer impulsoVelTimer;
    protected SnakeMovement snakeMovement;
    protected Mate mateNormal;
    protected HoyoNegro hoyoNegro;
    protected GameBoardSnake gameBoard;
    protected boolean impulsoActivo = false;
    protected MusicPlayer musicPlayer;

    public GameTimers(SnakeMovement snakeMovement, Mate mateNormal, HoyoNegro hoyoNegro, GameBoardSnake gameBoardSnake) {
        this.snakeMovement = snakeMovement;
        this.mateNormal = mateNormal;
        this.hoyoNegro = hoyoNegro;
        this.gameBoard = gameBoardSnake;

        setupTimers();
        musicPlayer = new MusicPlayer();
    }

    protected void setupTimers() {
        gameTimer = new Timer(170, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snakeMovement.isGameOver()) {
                    gameOver();
                    return;
                }

                int tamanio = snakeMovement.tamanioSnake();
                if (tamanio >= 50) {
                    gameTimer.setDelay(80);
                } else if (tamanio >= 20) {
                    gameTimer.setDelay(100);
                } else {
                    gameTimer.setDelay(160);
                }

                snakeMovement.moveSnake(gameBoard, null);

                // Handle the normal mate collection
                if (snakeMovement.getHeadPosition().equals(new Point(mateNormal.getMateX(), mateNormal.getMateY()))) {
                    snakeMovement.grow();
                    mateNormal.updateMate();
                }

                if (snakeMovement.getHeadPosition().equals(new Point(hoyoNegro.getHoyoX(), hoyoNegro.getHoyoY()))) {
                    if (snakeMovement.tamanioSnake() > 1) {
                        snakeMovement.removeSnakeBody();
                    }
                }

                gameBoard.repaint();
            }
        });

        respawnTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hoyoNegro != null && !gameBoard.getJuegoPausado()) {
                    hoyoNegro.updateHoyo();
                }
            }
        });

        respawnTimer.start();
    }

    public void startGameTimer() {
        if (gameTimer != null && !gameTimer.isRunning()) {
            gameTimer.start();
            respawnTimer.start();

            switch (SelectionPj.obtenerPersonajeSeleccionado()) {
                case SERPIENTE:
                    musicPlayer.playMusic("serpiente.WAV");
                    break;
                case CARPINCHO:
                    musicPlayer.playMusic("carpincho.WAV");
                    break;
                case TIBURON:
                    musicPlayer.playMusic("tiburon.WAV");
                    break;
                case GALLINA:
                    musicPlayer.playMusic("gallina.WAV");
                    break;
                case MOSCA:
                    musicPlayer.playMusic("mosca.WAV");
                    break;
                case TERMO:
                    musicPlayer.playMusic("termo.WAV");
                    break;
                default:
                    System.out.println("Error, animal no encontrado");
                    break;
            }
        }
    }

    public void stopGameTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
            musicPlayer.stopMusic();
        }
        if (impulsoVelTimer != null && impulsoVelTimer.isRunning()) {
            impulsoVelTimer.stop();
        }
    }

    public boolean isGameTimerRunning() {
        return gameTimer.isRunning();
    }

    protected void gameOver() {
        stopGameTimer();
        SwingUtilities.invokeLater(() -> {
            CardLayout layout = (CardLayout) gameBoard.getParent().getLayout();
            layout.show(gameBoard.getParent(), "gameOverPanel");

            gameBoard.setVisible(false);
        });
    }

    public void resetTimers() {
        stopGameTimer();
        startGameTimer();
    }
}