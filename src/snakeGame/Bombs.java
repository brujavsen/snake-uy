package snakeGame;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

public class Bombs {
	private int width = 600;
	private int height = 600;
	private static final int PIXEL_SIZE = 30;
	private int bombX;
    private int bombY;
    public ImageIcon bombImage;
	private Random random;
	private boolean[][] grid = new boolean[width][height];
	
	public Bombs() {
		bombImage = new ImageIcon(getClass().getResource("caldera.png"));
        random = new Random();
        placeBomb();
    }
	
	public void placeBomb() {
		int x, y;
        do {
            x = random.nextInt(width / PIXEL_SIZE) * PIXEL_SIZE;
            y = random.nextInt(height / PIXEL_SIZE) * PIXEL_SIZE;
        } while (grid[x][y]); // Asegurar que sea una casilla vacía
        bombX = x;
        bombY = y;
	}

    public void drawBomb(Graphics g) {
    	g.drawImage(bombImage.getImage(), bombX, bombY, PIXEL_SIZE, PIXEL_SIZE, null);
    }
    
    public int getBombX() {
        return bombX;
    }

    public int getBombY() {
        return bombY;
    }

    public boolean estaEnPosicion(int x, int y) {
        return bombX == x && bombY == y;
    }
    
    public void updateBomb() {
    	placeBomb();
    }
}
