package snakeGame;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Boss {
    public int vida;
    private Point position;
    private int width = 50;
    private int height = 50;
    private boolean vivo;
    private ImageIcon bossImg;

    public Boss() {
    	this.vida = 100;
    	this.vivo = true;
        this.position = new Point(270, 270); // Posición inicial
        bossImg = new ImageIcon(getClass().getResource("termoabajo.png"));
    }

    public void dibujar(Graphics g) {
        if (vivo) {
        	g.drawImage(bossImg.getImage(), position.x, position.y, width, height, null);
        }
    }

    public void recibirDanio() {
    	vida -= 10;
        if (vida <= 0) {
        	setVivo(false);
        }
        //System.out.println(health);
    }

    public int vidaBoss() {
    	return vida;
    }

    public boolean estaVivo() {
        return vivo;
    }
    
    public void resetVida() {
    	vida = 100;
        vivo = true;
    }
    
    public void setVivo(boolean estado) {
        this.vivo = estado;
    }

    public Rectangle obtenerRectangulo() {
        return new Rectangle(position.x, position.y, width, height);
    }

}