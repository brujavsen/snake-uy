package snakeGame;

import java.awt.CardLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GameBoardWithBoss extends GameBoardSnake {

    private static final long serialVersionUID = 1L;

    protected HoyoNegro hoyoNegro2;
    protected Bombs bomb;
    protected Boss boss;
    protected GameTimersBoss gameTimersBoss;
    protected GameBoardBoss gameBoardBoss;
    private int matesJefeFinal = 100;

    public GameBoardWithBoss(CardLayout cardLayout, JPanel contentPane) {
        super(cardLayout, contentPane);
        super.initializeComponents();
        super.setupKeyListener();
    }

    @Override
    protected void initializeComponents() {
    	snakeMovement = new SnakeMovement(SelectionPj.obtenerPersonajeSeleccionado());
        gameBoardBoss = new GameBoardBoss(cardLayout, contentPane);
        hoyoNegro2 = new HoyoNegro();
        mateNormal = new Mate("mt-mate.png");
        bomb = new Bombs();
        boss = new Boss();
        gameTimersBoss = new GameTimersBoss(snakeMovement, hoyoNegro, hoyoNegro2, bomb, gameBoardBoss, cardLayout, contentPane);
        
        bgImages = new ImageIcon[]{
                new ImageIcon(getClass().getResource("bg-snake.png")),
                new ImageIcon(getClass().getResource("bg-tiburon.png")),
                new ImageIcon(getClass().getResource("bg-gallina.png")),
                new ImageIcon(getClass().getResource("bg-mosca.png")),
                new ImageIcon(getClass().getResource("bg-carpincho.png")),
                new ImageIcon(getClass().getResource("bg-termo.png"))
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
        if (boss.estaVivo() && snakeMovement.tamanioSnake() == matesJefeFinal) {
            gameTimersBoss.cambiarAPanelDeJefe();
            gameTimers.stopGameTimer();
        }
    }
}