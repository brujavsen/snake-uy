package snakeGame;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimers {
    private Timer gameTimer;
    private Timer respawnTimer;
    private Timer impulsoVelTimer;
    private SnakeMovement snakeMovement;
    private Mate mateNormal;
    private Mate mateArg;
    private HoyoNegro hoyoNegro;
    private GameBoardSnake gameBoard;
    private boolean impulsoActivo = false;
    private MusicPlayer musicPlayer;
    
    public GameTimers(SnakeMovement snakeMovement, Mate mateNormal, Mate mateArg, HoyoNegro hoyoNegro, GameBoardSnake gameBoardSnake) {
        this.snakeMovement = snakeMovement;
        this.mateNormal = mateNormal;
        this.mateArg = mateArg;
        this.hoyoNegro = hoyoNegro;
        this.gameBoard = gameBoardSnake;
        
        setupTimers();
    	musicPlayer = new MusicPlayer();
    }
    
    private void setupTimers() {
        gameTimer = new Timer(110, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (snakeMovement.isGameOver()) {
                    gameOver();
                    return;
                }

                snakeMovement.moveSnake(gameBoard, null);
                
                if (snakeMovement.getHeadPosition().equals(new Point(mateNormal.getMateX(), mateNormal.getMateY()))) {
                    snakeMovement.grow();
                    mateNormal.updateMate();
                }
                if (snakeMovement.getHeadPosition().equals(new Point(mateArg.getMateX(), mateArg.getMateY()))) {
                    if (!impulsoActivo) {
                        mateArg.setActivo(false); // Marca el mateArg como no activo
                        activarImpulsoVelocidad();
                    }
                }
                if(snakeMovement.getHeadPosition().equals(new Point(hoyoNegro.getHoyoX(), hoyoNegro.getHoyoY()))) {
                	if(snakeMovement.tamanioSnake() != 1) {
                		snakeMovement.removeSnakeBody();
                	}
                }
                gameBoard.repaint();
            }
        });

        respawnTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (hoyoNegro != null && hoyoNegro.hoyo != null) {
                    if (!gameBoard.getJuegoPausado()) {
                        hoyoNegro.updateHoyo();
                    }
                }
                
            	if (mateArg != null) { // Solo actualiza la posición si no ha sido recolectado
                    mateArg.updateMate();
                    desactivarImpulsoVelocidad(); // Desactivar el impulso
                }
            }
        });
    }
    
    public void startGameTimer() {
        if (gameTimer != null && !gameTimer.isRunning()) {
            gameTimer.start();
            respawnTimer.start();
            
            switch(SelectionPj.obtenerPersonajeSeleccionado()) {
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
    
    private void activarImpulsoVelocidad() {
        if (!impulsoActivo) {
            impulsoActivo = true;
            gameTimer.setDelay(40); // Incrementa la velocidad a 40ms
        }
    }
    
    private void desactivarImpulsoVelocidad() {
        gameTimer.setDelay(100); // Vuelve a la velocidad normal
        impulsoActivo = false;
    }
    
    private void gameOver() {
        stopGameTimer();
        SwingUtilities.invokeLater(() -> {
            CardLayout layout = (CardLayout) gameBoard.getParent().getLayout();
            layout.show(gameBoard.getParent(), "gameOverPanel");
            
            //Limpiar el panel del juego para evitar redibujado mientras está oculto
            gameBoard.setVisible(false);
        });
    }
    
    public void resetTimers() {
        stopGameTimer();
        startGameTimer();
        mateArg.isActivo(); // Reinicia el estado del mateArg
    }
    
}