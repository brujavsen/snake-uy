package snakeGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Boss {
    public int health = 100;
    private Point position;
    private int width = 50;
    private int height = 50;
    private boolean alive;
    private ImageIcon bossImg;

    public Boss() {
        this.position = new Point(270, 270); // Posición inicial
        this.alive = true;
        bossImg = new ImageIcon(getClass().getResource("termoabajo.png"));
    }

    public void dibujar(Graphics g) {
        if (alive) {
        	g.drawImage(bossImg.getImage(), position.x, position.y, width, height, null);
        }
    }

    public void recibirDanio() {
        health -= 10;
        if (health <= 0) {
            alive = false;
        }
        //System.out.println(health);
    }

    public int vidaBoss() {
    	return health;
    }

    public boolean estaVivo() {
        return alive;
    }
    
    public void resetVida() {
        health = 100;
        alive = true;
    }

    public Rectangle obtenerRectangulo() {
        return new Rectangle(position.x, position.y, width, height);
    }

}