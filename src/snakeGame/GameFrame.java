package snakeGame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class GameFrame extends JFrame {

    public int width = 600;
    public int height = 600;

    private ImageIcon bgImage;

    private static final long serialVersionUID = 1L;
    private GameBoardSnake gameBoard;
    private GameBoardBoss gameBoardBoss;
    private GameOverPanel gameOverPanel;
    private SelectionPj selectionPj;
    private HelpPanel helpPanel;
    private FinalPanel finalPanel;
    private JPanel contentPane;
    private CardLayout cardLayout;

    // BOTONES
    private JButton startButton;
    private JButton menuPjBtn;
    private JButton menuHelpBtn;
    private JButton exitButton;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel titleGame;
    private Component rigidArea;
    private Component rigidArea_1;
    private Component rigidArea_2;

    private JButton[] buttons; // Array para los botones
    private int currentButtonIndex = 0; // Índice del botón actual

    public GameFrame() {
        setTitle("Animal Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("serpientearriba.png"));
        setIconImage(icon.getImage());

        bgImage = new ImageIcon(getClass().getResource("snake-uy.png"));

        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);

        JPanel mainMenuPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        mainMenuPanel.setLayout(new BorderLayout());

        //Version label
        lblNewLabel = new JLabel("Ver. 0.90");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainMenuPanel.add(lblNewLabel, BorderLayout.NORTH);

        //Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setForeground(Color.DARK_GRAY);
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        rigidArea = Box.createRigidArea(new Dimension(15, 100));
        buttonPanel.add(rigidArea);

        // Título
        titleGame = new JLabel("Animal Snake");
        titleGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleGame.setBackground(new Color(0, 128, 0));
        titleGame.setForeground(new Color(255, 255, 255));
        titleGame.setHorizontalAlignment(SwingConstants.CENTER);
        titleGame.setFont(new Font("Power Clear", Font.BOLD, 60));
        buttonPanel.add(titleGame);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));

        rigidArea_2 = Box.createRigidArea(new Dimension(20, 50));
        buttonPanel.add(rigidArea_2);

        // Iniciar juego btn
        startButton = new JButton("Iniciar juego");
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(24, 39, 71));
        startButton.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        startButton.addActionListener(e -> iniciarJuego());

        // Personajes Btn
        menuPjBtn = new JButton("Personajes");
        menuPjBtn.setForeground(new Color(0, 0, 0));
        menuPjBtn.setBackground(new Color(36, 55, 66));
        menuPjBtn.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        menuPjBtn.addActionListener(e -> {
        	cardLayout.show(contentPane, "selectionPjMenu");
            SelectionPj selectionPj = (SelectionPj) contentPane.getComponent(3); // Índice correcto del panel de selección
            selectionPj.requestFocusInWindow(); // Solicitar foco al panel de selección
        });
        
        // Panel de ayuda Btn
        menuHelpBtn = new JButton("Cómo Jugar");
        menuHelpBtn.setForeground(new Color(0, 0, 0));
        menuHelpBtn.setBackground(new Color(0, 100, 66));
        menuHelpBtn.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        menuHelpBtn.addActionListener(e -> {
        	cardLayout.show(contentPane, "helpPanel");
            HelpPanel helpPanel = (HelpPanel) contentPane.getComponent(4); // Índice correcto del panel de selección
            helpPanel.requestFocusInWindow(); // Solicitar foco al panel de selección
        });

        // Salir juego btn
        exitButton = new JButton("Salir");
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(165, 42, 42));
        exitButton.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        exitButton.addActionListener(e -> System.exit(0));

        // Configuración de botones
        buttons = new JButton[]{startButton, menuPjBtn, menuHelpBtn, exitButton}; // Array de botones
        for (JButton button : buttons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            buttonPanel.add(button);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        buttonPanel.add(rigidArea_1);

        mainMenuPanel.add(buttonPanel, BorderLayout.CENTER);

        lblNewLabel_1 = new JLabel("Programación, Testing, Diseño, Música: Bru, Mosca, Seba, Joaco");
        lblNewLabel_1.setForeground(new Color(255, 255, 255));
        lblNewLabel_1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainMenuPanel.add(lblNewLabel_1, BorderLayout.SOUTH);

        gameBoard = new GameBoardSnake(cardLayout, contentPane);
        selectionPj = new SelectionPj(contentPane, null);
        gameOverPanel = new GameOverPanel(cardLayout, contentPane);
        helpPanel = new HelpPanel(contentPane);
        gameBoardBoss = new GameBoardBoss(cardLayout, contentPane);
        finalPanel = new FinalPanel(contentPane);
        
        contentPane.add(mainMenuPanel, "mainMenu");
        contentPane.add(gameBoard, "gameBoard");
        contentPane.add(gameOverPanel, "gameOverPanel");
        contentPane.add(selectionPj, "selectionPjMenu");
        contentPane.add(helpPanel, "helpPanel");
        contentPane.add(gameBoardBoss, "gameBoardBoss");
        contentPane.add(finalPanel, "finalPanel");
        
        /*Component[] components = contentPane.getComponents();
    	for (int i = 0; i < components.length; i++) {
    	    System.out.println("Index " + i + ": " + components[i].getClass().getName());
    	}*/

        setContentPane(contentPane);
        pack();

        setupKeyListener(); // Configurar el KeyListener
        resaltarBotonSeleccionado(); // Resaltar el botón actual
    }

    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                    currentButtonIndex = (currentButtonIndex - 1 + buttons.length) % buttons.length;
                    resaltarBotonSeleccionado();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                    currentButtonIndex = (currentButtonIndex + 1) % buttons.length;
                    resaltarBotonSeleccionado();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttons[currentButtonIndex].doClick(); // Simula un clic en el botón actual
                }
            }
        });
        setFocusable(true); // Permitir que el marco sea enfocado
    }

    private void resaltarBotonSeleccionado() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setForeground(i == currentButtonIndex ? Color.YELLOW : Color.WHITE);
        }
    }

    private void iniciarJuego() {
        cardLayout.show(contentPane, "gameBoard");
        gameBoard.requestFocusInWindow();
        gameBoard.setFocusable(true);
        if (gameBoard.isTimerRunning()) {
            gameBoard.stopTimer();
        }
        gameBoard.startTimer();
        pack();
    }
}