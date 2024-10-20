package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Mate {
	private int width = 600;
	private int height = 600;
    private static final int PIXEL_SIZE = 30;
    protected ImageIcon mateImage;
    private int mateX;
    private int mateY;
    private Random random;
    private boolean activo; // Estado del mate
    private boolean[][] grid = new boolean[width][height];
    public boolean disponible = true;
    private Timer respawnTimer;

    public Mate(String tipoMate) {
        // Cargar la imagen del mate
        mateImage = new ImageIcon(getClass().getResource(tipoMate)); // Imagen del mate
        random = new Random();
        activo = true; // El mate inicia como activo
        placeMate(); // Inicializar la posición del mate
    }

    public void placeMate() {
        // Forma aleatoria el mate en una posición x, y en la cuadrícula
        int x, y;
        do {
            x = random.nextInt(width / PIXEL_SIZE) * PIXEL_SIZE;
            y = random.nextInt(height / PIXEL_SIZE) * PIXEL_SIZE;
        } while (grid[x][y]); // Asegurar que sea una casilla vacía
        
        mateX = x;
        mateY = y;
        activo = true; // Reestablecer el estado a activo al colocar el mate
    }

    public void drawMate(Graphics g) {
        if (activo && mateImage != null) {
            // Dibuja el mate en una posición determinada solo si está activo
            g.drawImage(mateImage.getImage(), mateX, mateY, PIXEL_SIZE, PIXEL_SIZE, null);
        }
    }
    
    public int getMateX() {
        return mateX;
    }

    public int getMateY() {
        return mateY;
    }

    public boolean estaEnPosicion(int x, int y) {
        return mateX == x && mateY == y;
    }
    
    public void updateMate() {
        placeMate(); // Mueve el mate a una nueva ubicación
        activo = true; // Asegurar de que el mate esté activo al actualizar
    }

    public void setActivo(boolean estado) {
        activo = estado; // Cambia el estado del mate
    }

    //Recolectar mate argentino da invulnerabilidad
    public void recolectado(SnakeMovement snakeMovement) {
        if (disponible) {
            disponible = false;
            snakeMovement.activateInvulnerability();
            startRespawnTimer();
        }
    }

    private void startRespawnTimer() {
        if (respawnTimer != null && respawnTimer.isRunning()) {
            respawnTimer.stop();
        }
        respawnTimer = new Timer(10000, e -> {
            disponible = true;
            placeMate();
            respawnTimer.stop();
        });
        respawnTimer.setRepeats(false);
        respawnTimer.start();
    }

    public boolean isDisponible() {
        return disponible;
    }
    
    public boolean isActivo() {
        return activo; // Devuelve el estado del mate
    }
}