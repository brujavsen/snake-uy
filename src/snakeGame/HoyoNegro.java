package snakeGame;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

public class HoyoNegro {
	private int width = 600;
	private int heigth = 600;
	private static final int PIXEL_SIZE = 30;
	private int hoyoX;
    private int hoyoY;
	public ImageIcon hoyo;
	private Random random;
	private boolean[][] grid = new boolean[width][heigth];
	
	public HoyoNegro() {
        // Cargar la imagen del hoyo
		hoyo = new ImageIcon(getClass().getResource("hoyo.png"));
        random = new Random();
        placeHoyo(); // Inicializar la posición del hoyo
    }
	
	public void placeHoyo() {
		int x, y;
        do {
            x = random.nextInt(width / PIXEL_SIZE) * PIXEL_SIZE;
            y = random.nextInt(heigth / PIXEL_SIZE) * PIXEL_SIZE;
        } while (grid[x][y]); // Asegurar que sea una casilla vacía
	    hoyoX = x;
	    hoyoY = y;
	}

    public void drawHoyo(Graphics g) {
        // Dibuja el hoyo en una posición determinada
        g.drawImage(hoyo.getImage(), hoyoX, hoyoY, PIXEL_SIZE, PIXEL_SIZE, null);
    }
    
    public int getHoyoX() {
        return hoyoX;
    }

    public int getHoyoY() {
        return hoyoY;
    }

    public boolean estaEnPosicion(int x, int y) {
        return hoyoX == x && hoyoY == y;
    }
    
    public void updateHoyo() {
        placeHoyo(); // Mueve el hoyo a una nueva ubicación
    }
}
