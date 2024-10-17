package snakeGame;

import java.awt.CardLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameTimersBoss {

	private Timer gameTimer;
    private Timer respawnTimer;
    private SnakeMovement snakeMovement;
    private GameBoardBoss gameBoardBoss;
    private MusicPlayer musicPlayer;
    private HoyoNegro hoyoNegro;
    private HoyoNegro hoyoNegro2;
    private Bombs bomb;
    private Boss boss;
    
    private CardLayout cardLayout;
    private JPanel contentPane;
    
    public GameTimersBoss(SnakeMovement snakeMovement, HoyoNegro hoyoNegro, HoyoNegro hoyoNegro2, Bombs bomb, GameBoardBoss gameBoardBoss, CardLayout cardLayout, JPanel contentPane) {
        this.snakeMovement = snakeMovement;
        this.hoyoNegro = hoyoNegro;
        this.hoyoNegro2 = hoyoNegro2;
        this.bomb = bomb;
        this.gameBoardBoss = gameBoardBoss;
        this.cardLayout = cardLayout;
        this.contentPane = contentPane;
        
        setupTimers();
        
        this.boss = gameBoardBoss.getBoss();
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
                
                if(snakeMovement.tamanioSnake() < snakeMovement.getLives()) {
                	snakeMovement.grow();
                }
                if(snakeMovement.getHeadPosition().equals(new Point(bomb.getBombX(), bomb.getBombY()))) {
                	boss.recibirDanio();
                	bomb.updateBomb();
                	gameBoardBoss.repaint();
                }
                if(snakeMovement.getHeadPosition().equals(new Point(hoyoNegro.getHoyoX(), hoyoNegro.getHoyoY())) || snakeMovement.getHeadPosition().equals(new Point(hoyoNegro2.getHoyoX(), hoyoNegro2.getHoyoY()))) {
                	if(snakeMovement.tamanioSnake() != 1) {
                		snakeMovement.removeSnakeBody();
                	}
                	snakeMovement.perderUnaVida();
                }
                if(boss.health < 60) {
                	incremVelMitadVidaJefe();
                }
                if(snakeMovement.getLives() == 1) {
                	gameOver();
                	return;
                }
                if(!boss.estaVivo()) {
                	jefeDerrotado();
                	return;
                }

                snakeMovement.moveSnake(null, gameBoardBoss);
                gameBoardBoss.repaint();
            }
        });

        respawnTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gameBoardBoss.getJuegoPausado()) {
                    hoyoNegro.updateHoyo();
                    hoyoNegro2.updateHoyo();
                }

                if (!gameBoardBoss.getJuegoPausado()) {
                    bomb.updateBomb();
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
            		musicPlayer.playMusic("finalboss.WAV");
            		break;
				case CARPINCHO:
					musicPlayer.playMusic("finalboss.WAV");
	            	break;
	            case TIBURON:
	            	musicPlayer.playMusic("finalboss.WAV");
	            	break;
	            case GALLINA:
	            	musicPlayer.playMusic("finalboss.WAV");;
	            	break;
	            case MOSCA:
	            	musicPlayer.playMusic("finalboss.WAV");
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
            respawnTimer.stop();
        }
    }
    
    public boolean isGameTimerRunning() {
        return gameTimer.isRunning();
    }
    
    private void incremVelMitadVidaJefe() {
        if (gameTimer.getDelay() != 50) {
            gameTimer.setDelay(50);
            respawnTimer.setDelay(900);
        }
    }
    
    private void gameOver() {
        stopGameTimer();
        boss.resetVida();
        SwingUtilities.invokeLater(() -> {
            CardLayout layout = (CardLayout) gameBoardBoss.getParent().getLayout();
            layout.show(gameBoardBoss.getParent(), "gameOverPanel");
            
            //Limpiar el panel del juego para evitar redibujado mientras está oculto
            gameBoardBoss.setVisible(false);
        });
    }
    
    public void cambiarAPanelDeJefe() {
        cardLayout.show(contentPane, "gameBoardBoss");
        gameBoardBoss = (GameBoardBoss) contentPane.getComponent(5);
        
        boss.resetVida();
        
        gameBoardBoss.requestFocusInWindow();
        gameBoardBoss.setVisible(true);
        gameBoardBoss.iniciarCombate(); // Inicia el combate al cambiar al panel de jefe
    }
    
    public void jefeDerrotado() {
    	cardLayout.show(contentPane, "finalPanel");
    	FinalPanel finalPanel = (FinalPanel) contentPane.getComponent(6);
        finalPanel.requestFocusInWindow();
        finalPanel.setVisible(true);
        stopGameTimer();
    }

}
