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
    protected Timer boostTimer;
    protected SnakeMovement snakeMovement;
    protected Mate mateNormal;
    protected Mate mateArg;
    protected HoyoNegro hoyoNegro;
    protected GameBoardSnake gameBoard;
    protected MusicPlayer musicPlayer;

    public GameTimers(SnakeMovement snakeMovement, Mate mateNormal, Mate mateArg, HoyoNegro hoyoNegro, GameBoardSnake gameBoardSnake, MusicPlayer musicPlayer) {
        this.snakeMovement = snakeMovement;
        this.mateNormal = mateNormal;
        this.mateArg = mateArg;
        this.hoyoNegro = hoyoNegro;
        this.gameBoard = gameBoardSnake;
        this.musicPlayer = musicPlayer;
        
        setupTimers();
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

                if (snakeMovement.getHeadPosition().equals(new Point(mateNormal.getMateX(), mateNormal.getMateY()))) {
                    snakeMovement.grow();
                    mateNormal.updateMate();
                    musicPlayer.playOneTime("mateSound.wav");
                }
                
                if (snakeMovement.getHeadPosition().equals(new Point(mateArg.getMateX(), mateArg.getMateY()))) {
                    if (mateArg.isDisponible()) {
                        mateArg.recolectado(snakeMovement);
                        musicPlayer.playOneTime("mateSound.wav");
                    }
                } else {
                    if (!mateArg.isDisponible()) {
                        mateArg.setActivo(false);
                    }
                }

                if (snakeMovement.getHeadPosition().equals(new Point(hoyoNegro.getHoyoX(), hoyoNegro.getHoyoY()))) {
                    if (snakeMovement.tamanioSnake() > 1) {
                        snakeMovement.removeSnakeBody();
                    }
                    musicPlayer.playOneTime("hoyoSound.wav");
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
                
                if (mateArg != null && !gameBoard.getJuegoPausado()) {
                    mateArg.updateMate();
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