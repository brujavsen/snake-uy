package snakeGame;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import snakeGame.SnakeMovement.AnimalCharacter;

public class GameBoardBoss extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private SnakeMovement snakeMovement;
    private GameTimersBoss gameTimers;
    private HoyoNegro hoyoNegro;
    private HoyoNegro hoyoNegro2;
    private Bombs bomb;
    private Boss boss;
    private ImageIcon bgBoss;
    private MusicPlayer musicPlayer;
    
    private int width = 600;
    private int height = 600;
    
    private boolean juegoPausado = false;
    public boolean enCombate = false;
    
    private CardLayout cardLayout;
    private JPanel contentPane;
    
    public GameBoardBoss(CardLayout cardLayout, JPanel contentPane, MusicPlayer musicPlayer) {
        this.contentPane = contentPane;
        this.cardLayout = cardLayout;
        this.musicPlayer = musicPlayer;
        
        boss = new Boss();
        hoyoNegro = new HoyoNegro();
        hoyoNegro2 = new HoyoNegro();
        bomb = new Bombs();
        bgBoss = new ImageIcon(getClass().getResource("bg-boss.png"));
        setPreferredSize(new Dimension(width, height));
        initializeComponents();
        setupKeyListener();
    }
    
    private void initializeComponents() {
    	serpienteSeleccionada();
    	snakeMoveYGameTimers();
    }
    
    private void serpienteSeleccionada() {
        if (!SelectionPj.personajeSeleccionado() || SelectionPj.obtenerPersonajeSeleccionado() != AnimalCharacter.CARPINCHO) {
            SelectionPj.setPersonajeSeleccionado(AnimalCharacter.CARPINCHO);
        }
    }
    
    private void snakeMoveYGameTimers() {
        snakeMovement = new SnakeMovement(SelectionPj.obtenerPersonajeSeleccionado());
        gameTimers = new GameTimersBoss(snakeMovement, hoyoNegro, hoyoNegro2, bomb, this, cardLayout, contentPane, musicPlayer);
    }

    private void setupKeyListener() {
        addKeyListener(new SnakeKeyListener(snakeMovement) {
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
                    stopTimer();
                    juegoPausado = true;
                } else {
                    startTimer();
                    juegoPausado = false;
                }
                if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                	enCombate = true;
                }
                repaint();
            }
        });
        this.requestFocusInWindow();
        this.setFocusable(true);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        if (enCombate) {
            snakeMovement.drawSnake(g);
            if (juegoPausado) {
                pausarJuego(g);
            }
            
            snakeMovement.drawSnake(g);
            bomb.drawBomb(g);
            boss.dibujar(g);
            vida(g);
            if (snakeMovement.tamanioSnake() > 1) {
                hoyoNegro.drawHoyo(g);
                hoyoNegro2.drawHoyo(g);
            }
            repaint();
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Lato", Font.BOLD, 20));
            g.drawString("Jefe Final. Presiona cualquier tecla para iniciar.", 50, height / 5);
        }
    }
    
    public void drawBoard(Graphics g) {
    	g.drawImage(bgBoss.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
    
    public void vida(Graphics g) {
    	g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("VIDA JEFE: " + boss.vidaBoss(), 24 - 16, 24);
    }
    
    public void iniciarCombate() {
    	if(enCombate) {    		
    		serpienteSeleccionada();
    		snakeMoveYGameTimers();
    		setupKeyListener();
    		juegoPausado = false;
    		startTimer();
    	}

        repaint();
    }
    
    private void pausarJuego(Graphics g) {
        g.setColor(Color.blue);
        g.setFont(new Font("Power Red and Blue", Font.BOLD, 25));
        g.drawString("Juego Pausado.", 200, 300);
        g.drawString("Pulse cualquier tecla para continuar", 100, 330);
    }
    
    public void startTimer() {
        gameTimers.startGameTimer();
    }

    public void stopTimer() {
        gameTimers.stopGameTimer();
        enCombate = false;
    }
    
    public void gameOver() {
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, "gameOverPanel");
    }
    
    public boolean getJuegoPausado() {
    	return juegoPausado;
    }
    
    public Boss getBoss() {
    	return boss;
    }
    
    public void jefeDerrotado() {
        boss.setVivo(false);
    }

}