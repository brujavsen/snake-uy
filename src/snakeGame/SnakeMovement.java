package snakeGame;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
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
    
    private boolean growNext = false;
    private boolean gameOver = false; // Nuevo estado para verificar si el juego ha terminado
    
    private Boss jefe;
    
    //Personajes
    public enum AnimalCharacter {
        SERPIENTE,
        CARPINCHO,
        TIBURON,
        GALLINA,
        MOSCA
    }
    
    public enum Direction {
        LEFT, RIGHT, DOWN, UP
    }

    public SnakeMovement(AnimalCharacter personajeSeleccionado) {
    	jefe = new Boss();
        //System.out.println(personajeSeleccionado);
    	if (personajeSeleccionado == null) {
            throw new IllegalStateException("No hay un personaje seleccionado.");
        }
    	
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
	            cuerpo = new ImageIcon(getClass().getResource("ricardito.png"));
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
	            cuerpo = new ImageIcon(getClass().getResource("ricardito.png"));
	            break;
	         default:
	        	 System.out.print("No existe");
	        	 break;
    	}
        
        direccionActual = Direction.RIGHT;
        actualDireccionImagen = imgDerecha;

        snakeBody = new ArrayList<>();
        snakeBody.add(new Point(0, 0));
        
        GameOverPanel gameOverPanel = new GameOverPanel(null, null);
    	
        new HoyoNegro();
    }

    public void setDirection(Direction direction) {
        direccionActual = direction;
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

    public Direction getDireccionActual() {
        return direccionActual;
    }

    public void moveSnake(GameBoardSnake gameBoard) {
        if (gameOver) return; // No mover la serpiente si el juego ha terminado

        Point head = snakeBody.get(0);
        Point newHead = (Point) head.clone();
        switch (direccionActual) {
            case RIGHT:
                newHead.x += cellSize;
                break;
            case LEFT:
                newHead.x -= cellSize;
                break;
            case DOWN:
                newHead.y += cellSize;
                break;
            case UP:
                newHead.y -= cellSize;
                break;
        }
        snakeBody.add(0, newHead);
        if (growNext) {
            growNext = false;
        } else {
            snakeBody.remove(snakeBody.size() - 1); //para que no se repita el cuerpo varias veces hasta que haya tomado un mate
        }
        
        if (checkCollisions()) {
        	gameBoard.actualizarPuntaje(tamanioSnake());
            gameOver = true; // Establecer el estado de fin de juego
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
        return isWallCollision() || isSelfCollision();
    }

    private boolean isWallCollision() {
        Point head = snakeBody.get(0);
        return head.x < 0 || head.x >= width || head.y < 0 || head.y >= height;
    }

    private boolean isSelfCollision() {
        Point head = snakeBody.get(0);
        return snakeBody.subList(1, snakeBody.size()).contains(head);
    }

    public int tamanioSnake() {
        return snakeBody.size();
    }

    public boolean isGameOver() {
        return gameOver;
    }

}