package snakeGame;

import java.awt.CardLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import snakeGame.SnakeMovement.AnimalCharacter;

public class GameBoardWithBoss extends GameBoardSnake {

    private static final long serialVersionUID = 1L;

    protected HoyoNegro hoyoNegro2;
    protected Bombs bomb;
    protected Boss boss;
    protected GameTimersBoss gameTimersBoss;
    protected GameBoardBoss gameBoardBoss;
    protected ImageIcon[] bgImages;
    private int matesJefeFinal = 100;
    private MusicPlayer musicPlayer;

    public GameBoardWithBoss(CardLayout cardLayout, JPanel contentPane, MusicPlayer musicPlayer) {
        super(cardLayout, contentPane, musicPlayer);
        super.initializeComponents();
        super.setupKeyListener();
    }

    @Override
    protected void initializeComponents() {
        if (!SelectionPj.personajeSeleccionado()) {
            SelectionPj.setPersonajeSeleccionado(AnimalCharacter.CARPINCHO);
        }
        snakeMovement = new SnakeMovement(SelectionPj.obtenerPersonajeSeleccionado());
        gameBoardBoss = new GameBoardBoss(cardLayout, contentPane, musicPlayer);
        hoyoNegro2 = new HoyoNegro();
        mateNormal = new Mate("mt-mate.png");
        bomb = new Bombs();
        boss = new Boss();
        gameTimers = null;
        gameTimersBoss = new GameTimersBoss(snakeMovement, hoyoNegro, hoyoNegro2, bomb, gameBoardBoss, cardLayout, contentPane, musicPlayer);
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	snakeMovement.drawSnake(g);
        if (boss.estaVivo() && snakeMovement.tamanioSnake() == matesJefeFinal) {
            gameTimersBoss.cambiarAPanelDeJefe();
            gameTimers.stopGameTimer();
        }
    }
}