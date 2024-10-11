package snakeGame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SnakeKeyListener implements KeyListener {
    private SnakeMovement snakeMovement;
    
    public SnakeKeyListener(SnakeMovement snakeMovement) {
        this.snakeMovement = snakeMovement;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        SnakeMovement.Direction currentDirection = snakeMovement.getDireccionActual();

        if (KeyEvent.VK_LEFT == e.getKeyCode() || KeyEvent.VK_A == e.getKeyCode()) {
            if (currentDirection != SnakeMovement.Direction.RIGHT) {
                snakeMovement.setDirection(SnakeMovement.Direction.LEFT);
            }
        } else if (KeyEvent.VK_RIGHT == e.getKeyCode() || KeyEvent.VK_D == e.getKeyCode()) {
            if (currentDirection != SnakeMovement.Direction.LEFT) {
                snakeMovement.setDirection(SnakeMovement.Direction.RIGHT);
            }
        } else if (KeyEvent.VK_DOWN == e.getKeyCode() || KeyEvent.VK_S == e.getKeyCode()) {
            if (currentDirection != SnakeMovement.Direction.UP) {
                snakeMovement.setDirection(SnakeMovement.Direction.DOWN);
            }
        } else if (KeyEvent.VK_UP == e.getKeyCode() || KeyEvent.VK_W == e.getKeyCode()) {
            if (currentDirection != SnakeMovement.Direction.DOWN) {
                snakeMovement.setDirection(SnakeMovement.Direction.UP);
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
