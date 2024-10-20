package snakeGame;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class SnakeMovement {
	private int cellSize = 30; // Tamaño de la celda (en píxeles)
	private int width = 600;
	private int height = 600;
	private List<Point> snakeBody;
	private Direction direccionActual;
	
    private ImageIcon imgDerecha;
    private ImageIcon imgAbajo;
    private ImageIcon imgArriba;
    private ImageIcon imgIzquierda;
    private ImageIcon cuerpo;
    private ImageIcon actualDireccionImagen;
    
    private int vidas = 4;
    
    public boolean invulnerable;
    private long invulnerableStartTime;
    private static final int INVULNERABLE_DURATION = 5000;
    
    private boolean growNext = false;
    private boolean gameOver = false; // Nuevo estado para verificar si el juego ha terminado
    private boolean hasMoved = true; //Cooldown de movimiento
    
    //Personajes
    public enum AnimalCharacter {
        SERPIENTE,
        CARPINCHO,
        TIBURON,
        GALLINA,
        MOSCA,
        TERMO
    }
    
    public enum Direction {
        LEFT, RIGHT, DOWN, UP
    }

    public SnakeMovement(AnimalCharacter personajeSeleccionado) {
        //System.out.println(personajeSeleccionado);
    	if (personajeSeleccionado == null) {
            throw new IllegalStateException("No hay un personaje seleccionado.");
        }
    	
    	this.invulnerable = false;
    	
    	switch (personajeSeleccionado) {
	        case CARPINCHO:
	            imgDerecha = new ImageIcon(getClass().getResource("carpinchoder.png"));
	            imgAbajo = new ImageIcon(getClass().getResource("carpinchoabajo.png"));
	            imgArriba = new ImageIcon(getClass().getResource("carpinchoarriba.png"));
	            imgIzquierda = new ImageIcon(getClass().getResource("carpinchoizq.png"));
	            cuerpo = new ImageIcon(getClass().getResource("ricardito.png"));
	            break;
	        case SERPIENTE:
	            imgDerecha = new ImageIcon(getClass().getResource("serpientederecha.png"));
	            imgAbajo = new ImageIcon(getClass().getResource("serpientearriba.png"));
	            imgArriba = new ImageIcon(getClass().getResource("serpienteabajo.png"));
	            imgIzquierda = new ImageIcon(getClass().getResource("serpienteizquierda.png"));
	            cuerpo = new ImageIcon(getClass().getResource("serpientecuerpo.png"));
	            break;
	        case TIBURON:
	            imgDerecha = new ImageIcon(getClass().getResource("tiburonder.png"));
	            imgAbajo = new ImageIcon(getClass().getResource("tiburonarriba.png"));
	            imgArriba = new ImageIcon(getClass().getResource("tiburonabajo.png"));
	            imgIzquierda = new ImageIcon(getClass().getResource("tiburonizq.png"));
	            cuerpo = new ImageIcon(getClass().getResource("pezmuerto.gif"));
	            break;
	        case GALLINA:
	            imgDerecha = new ImageIcon(getClass().getResource("gallinader.png"));
	            imgAbajo = new ImageIcon(getClass().getResource("gallinaabajo.png"));
	            imgArriba = new ImageIcon(getClass().getResource("gallinaarriba.png"));
	            imgIzquierda = new ImageIcon(getClass().getResource("gallinaizq.png"));
	            cuerpo = new ImageIcon(getClass().getResource("huevo.png"));
	            break;
	        case MOSCA:
	            imgDerecha = new ImageIcon(getClass().getResource("moskader.png"));
	            imgAbajo = new ImageIcon(getClass().getResource("moskaabajo.png"));
	            imgArriba = new ImageIcon(getClass().getResource("moskaarriba.png"));
	            imgIzquierda = new ImageIcon(getClass().getResource("moskaizq.png"));
	            cuerpo = new ImageIcon(getClass().getResource("vomito.png"));
	            break;
	        case TERMO:
	            imgDerecha = new ImageIcon(getClass().getResource("termoder.png"));
	            imgAbajo = new ImageIcon(getClass().getResource("termoabajo.png"));
	            imgArriba = new ImageIcon(getClass().getResource("termoarriba.png"));
	            imgIzquierda = new ImageIcon(getClass().getResource("termoizq.png"));
	            cuerpo = new ImageIcon(getClass().getResource("caldera.png"));
	            break;
	         default:
	        	 System.out.print("No existe");
	        	 break;
    	}
        
        direccionActual = Direction.RIGHT;
        actualDireccionImagen = imgDerecha;

        snakeBody = new ArrayList<>();
        snakeBody.add(new Point(0, 0));
    }
    
    public void setDirection(Direction direction) {
        if (hasMoved) {
            direccionActual = direction;
            hasMoved = false;  // Solo permitir cambiar dirección tras haber avanzado
            switch (direction) {
                case LEFT:
                    actualDireccionImagen = imgIzquierda;
                    break;
                case RIGHT:
                    actualDireccionImagen = imgDerecha;
                    break;
                case DOWN:
                    actualDireccionImagen = imgAbajo;
                    break;
                case UP:
                    actualDireccionImagen = imgArriba;
                    break;
            }
        }
    }

    public Direction getDireccionActual() {
        return direccionActual;
    }

    public void moveSnake(GameBoardSnake gameBoard, GameBoardBoss gameBoardBoss) {
        if (gameOver) return;

        Point head = snakeBody.get(0);
        Point newHead = (Point) head.clone();
        switch (direccionActual) {
	        case RIGHT:
	            newHead.x += cellSize;
	            if (newHead.x >= width) {
	                newHead.x = 0;
	            }
	            break;
	        case LEFT:
	            newHead.x -= cellSize;
	            if (newHead.x < 0) {
	                newHead.x = width - cellSize;
	            }
	            break;
	        case DOWN:
	            newHead.y += cellSize;
	            if (newHead.y >= height) {
	                newHead.y = 0;
	            }
	            break;
	        case UP:
	            newHead.y -= cellSize;
	            if (newHead.y < 0) {
	                newHead.y = height - cellSize;
	            }
	            break;
        }
        snakeBody.add(0, newHead);
        if (growNext) {
            growNext = false;
        } else {
            snakeBody.remove(snakeBody.size() - 1);
        }
        
        hasMoved = true;
        
        if (checkCollisions()) {
            gameOver = true;
        }
        if (gameBoard instanceof GameBoardSnake gameBoardSnake) {
        	gameBoardSnake.actualizarPuntaje(tamanioSnake());
        }
    }

    public void drawSnake(Graphics g) {
        Point head = snakeBody.get(0);
        g.drawImage(actualDireccionImagen.getImage(), head.x, head.y, cellSize, cellSize, null);

        for (int i = 1; i < snakeBody.size(); i++) {
            Point segment = snakeBody.get(i);
            g.drawImage(cuerpo.getImage(), segment.x, segment.y, cellSize, cellSize, null);
        }
    }
    
    public void activateInvulnerability() {
        this.invulnerable = true;
        this.invulnerableStartTime = System.currentTimeMillis();
    }

    public boolean isInvulnerable() {
        // Si el tiempo actual - tiempo de inicio de invulnerabilidad es menor que la duración, sigue siendo invulnerable
        if (invulnerable && (System.currentTimeMillis() - invulnerableStartTime < INVULNERABLE_DURATION)) {
            return true;
        } else {
            invulnerable = false; // Desactivar invulnerabilidad si ha pasado el tiempo
            return false;
        }
    }

    public Point getHeadPosition() {
        return snakeBody.get(0);
    }

    public List<Point> getSnakeBody() {
        return snakeBody;
    }
    
    public Point removeSnakeBody() {
    	return snakeBody.remove(snakeBody.size() - 1);
    }

    public void grow() {
        growNext = true;
    }

    public ImageIcon snakeActualImg() {
        return actualDireccionImagen;
    }

    public ImageIcon snakeCuerpoImg() {
        return cuerpo;
    }

    private boolean checkCollisions() {
        return isSelfCollision();
    }

    private boolean isSelfCollision() {
        Point head = snakeBody.get(0);
        return !isInvulnerable() && snakeBody.subList(1, snakeBody.size()).contains(head);
    }

    public int tamanioSnake() {
        return snakeBody.size();
    }

    public boolean isGameOver() {
        return gameOver;
    }
    
    public void perderVidas() {
        if (vidas > 0) {
            vidas--;
            if (vidas > 0 && !snakeBody.isEmpty()) {
                removeSnakeBody(); // Quitar una parte del cuerpo
            }
            if (vidas == 0) {
                gameOver = true; // Game over si ya no quedan vidas
            }
        }
    }

    public int perderUnaVida() {
    	return --vidas;
    }
    
    public int getLives() {
        return vidas;
    }
    
    public int getHeadX() {
        return snakeBody.get(0).x;
    }

    public int getHeadY() {
        return snakeBody.get(0).y;
    }

}