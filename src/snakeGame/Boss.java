package snakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Boss {
    public int health = 100;
    private Point position;
    private int width = 30;
    private int height = 30;
    private boolean alive;

    public Boss() {
        this.position = new Point(270, 270); // Posición inicial
        this.alive = true;
    }

    public void dibujar(Graphics g) {
        if (alive) {
            g.setColor(Color.RED);
            g.fillRect(position.x, position.y, width, height);
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