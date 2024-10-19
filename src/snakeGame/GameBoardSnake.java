package snakeGame;

import javax.swing.*;

import snakeGame.SnakeMovement.AnimalCharacter;

import java.awt.*;
import java.awt.event.KeyEvent;

public class GameBoardSnake extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected SnakeMovement snakeMovement;
	protected Mate mateNormal;
	protected HoyoNegro hoyoNegro;
	protected GameTimers gameTimers;
    
    protected ImageIcon[] bgImages;
    
    protected int width = 600;
	protected int height = 600;
    //private int cellSize = 30;
    
    protected boolean juegoPausado = false;
    
    protected JPanel contentPane;
    protected CardLayout cardLayout;
    protected int opcionIniciar;
    
    public GameBoardSnake(CardLayout cardLayout, JPanel contentPane) {
    	this.contentPane = contentPane;
    	this.cardLayout = cardLayout;

    	initializeComponents();
        setupKeyListener();
        setPreferredSize(new Dimension(width, height));
    }
   
    protected void initializeComponents() {
        // Verificar si hay un personaje seleccionado antes de crear SnakeMovement
        if (!SelectionPj.personajeSeleccionado()) {
            SelectionPj.setPersonajeSeleccionado(AnimalCharacter.CARPINCHO); // Establecer la serpiente como personaje predeterminado
        }

        // Crear SnakeMovement con el personaje seleccionado
        snakeMovement = new SnakeMovement(SelectionPj.obtenerPersonajeSeleccionado());
        hoyoNegro = new HoyoNegro();
        mateNormal = new Mate("mt-mate.png");
        gameTimers = new GameTimers(snakeMovement, mateNormal, hoyoNegro, this);
        
        // Cargar imágenes de ayuda
        bgImages = new ImageIcon[]{
            new ImageIcon(getClass().getResource("bg-snake.png")),
            new ImageIcon(getClass().getResource("bg-tiburon.png")),
            new ImageIcon(getClass().getResource("bg-gallina.png")),
            new ImageIcon(getClass().getResource("bg-mosca.png")),
            new ImageIcon(getClass().getResource("bg-carpincho.png")),
            new ImageIcon(getClass().getResource("bg-termo.png"))
        };
    }
    
    protected void setupKeyListener() {
        addKeyListener(new SnakeKeyListener(snakeMovement){
        	public void keyPressed(KeyEvent e) {
        		super.keyPressed(e);
        		if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_P) {
        			stopTimer();
        			juegoPausado = true;
        		} else {
        			startTimer();
        			juegoPausado = false;
        		}
        		repaint();
        	}
        });
        setFocusable(true); // foco en el panel para recibir eventos de teclado
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    	drawBoard(g); // Dibuja el fondo
    	mateNormal.drawMate(g); // Dibuja el mate que hace crecer la serpiente
    	if (snakeMovement.tamanioSnake() > 1) {
            hoyoNegro.drawHoyo(g);
        }
    	snakeMovement.drawSnake(g);
    	if(juegoPausado) {
    		pausarJuego(g);
    	}
    	puntajeJuego(g);
        //drawGrid(g);
    }
    
    protected void drawBoard(Graphics g) {
		if(SelectionPj.obtenerPersonajeSeleccionado() == AnimalCharacter.SERPIENTE) {    			
			g.drawImage(bgImages[0].getImage(), 0, 0, getWidth(), getHeight(), null);
		} else if(SelectionPj.obtenerPersonajeSeleccionado() == AnimalCharacter.TIBURON) {
			g.drawImage(bgImages[1].getImage(), 0, 0, getWidth(), getHeight(), null);
		} else if(SelectionPj.obtenerPersonajeSeleccionado() == AnimalCharacter.GALLINA) {
			g.drawImage(bgImages[2].getImage(), 0, 0, getWidth(), getHeight(), null);
		} else if(SelectionPj.obtenerPersonajeSeleccionado() == AnimalCharacter.MOSCA) {
			g.drawImage(bgImages[3].getImage(), 0, 0, getWidth(), getHeight(), null);
		} else if(SelectionPj.obtenerPersonajeSeleccionado() == AnimalCharacter.CARPINCHO) {
			g.drawImage(bgImages[4].getImage(), 0, 0, getWidth(), getHeight(), null);
		} else if(SelectionPj.obtenerPersonajeSeleccionado() == AnimalCharacter.TERMO) {
			g.drawImage(bgImages[5].getImage(), 0, 0, getWidth(), getHeight(), null);
		}
    }
    
    protected void puntajeJuego(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Score: " + snakeMovement.tamanioSnake(), 24 - 16, 24);
    }
    
    protected void pausarJuego(Graphics g) {
        g.setColor(Color.blue);
        g.setFont(new Font("Power Red and Blue", Font.BOLD, 25));
        g.drawString("Juego Pausado.", 200, 300);
        g.drawString("Pulse cualquier tecla para continuar", 100, 330);
    }
    
    public void reiniciarJuego() {
        stopTimer(); // Detén el temporizador actual
        // Verificar si hay un personaje seleccionado antes de crear SnakeMovement
        if (!SelectionPj.personajeSeleccionado()) {
            SelectionPj.setPersonajeSeleccionado(AnimalCharacter.CARPINCHO); // Establecer la serpiente como personaje predeterminado
        }
        snakeMovement = new SnakeMovement(SelectionPj.obtenerPersonajeSeleccionado());
        mateNormal = new Mate("mt-mate.png");
        hoyoNegro = new HoyoNegro();
        gameTimers = new GameTimers(snakeMovement, mateNormal, hoyoNegro, this); // Reinicia los temporizadores
        setupKeyListener(); // Asegúrate de que el panel tenga el KeyListener
        repaint(); // Redibuja el tablero para mostrar el estado inicial
    }

    public void startTimer() {
        gameTimers.startGameTimer();
    }

    public void stopTimer() {
        gameTimers.stopGameTimer();
    }

    public boolean isTimerRunning() {
        return gameTimers.isGameTimerRunning();
    }
    
    public void actualizarPuntaje(int puntaje) {
        GameOverPanel gameOverPanel = (GameOverPanel) contentPane.getComponent(2);
        gameOverPanel.actualizarPuntaje(puntaje);
    }
    
    public boolean getJuegoPausado() {
    	return juegoPausado;
    }

    
    /*public void drawGrid(Graphics g) {
        for (int i = 0; i <= width / cellSize; i++) {
            g.drawLine(i * cellSize, 0, i * cellSize, height); // Ajusta para que respete la altura
        }
        for (int i = 0; i <= height / cellSize; i++) {
            g.drawLine(0, i * cellSize, width, i * cellSize); // Ajusta para que respete el ancho
        }
    }*/

}