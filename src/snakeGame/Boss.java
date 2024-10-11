package snakeGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class Boss {
    private int health;
    private Point position;
    private int width = 50; // Boss width
    private int height = 50; // Boss height
    private boolean alive;

    public Boss() {
        this.health = 100; // Initial health
        this.position = new Point(300, 300); // Initial position
        this.alive = true;
    }

    public void dibujar(Graphics g) {
        // Example: Draw a simple rectangle for the boss
        if (alive) {
            g.setColor(Color.RED);
            g.fillRect(position.x, position.y, width, height);
        }
    }

    public void recibirDanio() {
        health -= 10; // Reduce health by 10
        if (health <= 0) {
            alive = false; // Boss is defeated
        }
    }

    public boolean estaVivo() {
        return alive;
    }

    public Rectangle obtenerRectangulo() {
        return new Rectangle(position.x, position.y, width, height);
    }

    // Add methods for boss attacks and movement
}