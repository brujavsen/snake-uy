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
    private GameBoardWithBoss gameBoss;
    private GameBoardBoss gameBoardBoss;
    private GameOverPanel gameOverPanel;
    private SelectionPj selectionPj;
    private HelpPanel helpPanel;
    private FinalPanel finalPanel;
    private JPanel contentPane;
    private CardLayout cardLayout;

    // BOTONES
    private JButton startButton;
    private JButton bossButton;
    private JButton menuPjBtn;
    private JButton musicBtn;
    private JButton menuHelpBtn;
    private JButton exitButton;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel titleGame;
    private Component rigidArea;
    private Component rigidArea_1;
    private Component rigidArea_2;
    private Component rigidArea_3;
    
    public int opcionIniciar;

    private JButton[] buttons; // Array para los botones
    private int currentButtonIndex = 0; // Índice del botón actual
    private JLabel toolTipLabel;
    private MusicPlayer musicPlayer;
    
    public GameFrame() {
        setTitle("Animal Snake");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        
        musicPlayer = new MusicPlayer();
        
        musicPlayer.setIsActivo(false);

        ImageIcon icon = new ImageIcon(getClass().getResource("termoabajo.png"));
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
        lblNewLabel = new JLabel("Ver. 1.10");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainMenuPanel.add(lblNewLabel, BorderLayout.NORTH);

        //Panel de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setForeground(Color.DARK_GRAY);
        buttonPanel.setBackground(new Color(255, 255, 255));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        rigidArea = Box.createRigidArea(new Dimension(15, 80));
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
        
        rigidArea_3 = Box.createRigidArea(new Dimension(20, 50));
        buttonPanel.add(rigidArea_3);
        
        toolTipLabel = new JLabel("");
        toolTipLabel.setFont(new Font("Lato", Font.ITALIC, 18));
        toolTipLabel.setForeground(new Color(255, 255, 255));
        toolTipLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        toolTipLabel.setPreferredSize(new Dimension(400, 30));
        buttonPanel.add(toolTipLabel);

        rigidArea_2 = Box.createRigidArea(new Dimension(20, 30));
        buttonPanel.add(rigidArea_2);

        // Iniciar juego btn
        startButton = new JButton("Modo Libre");
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(24, 39, 71));
        startButton.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        startButton.addActionListener(e -> iniciarJuego());
        
        bossButton = new JButton("Modo Historia");
        bossButton.setForeground(Color.WHITE);
        bossButton.setBackground(new Color(120, 39, 71));
        bossButton.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        bossButton.addActionListener(e -> iniciarJuegoConJefe());
        
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
        
        // Personajes Btn
        musicBtn = new JButton("Música");
        musicBtn.setForeground(new Color(0, 0, 0));
        musicBtn.setBackground(new Color(0, 155, 66));
        musicBtn.setFont(new Font("Power Red and Blue", Font.BOLD, 20));
        musicBtn.addActionListener(e -> {
        	if(musicPlayer.getIsActivo() == false) {
        		musicPlayer.setIsActivo(true);
        	} else {
        		musicPlayer.setIsActivo(false);
        	}
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
        buttons = new JButton[]{startButton, bossButton, menuPjBtn, musicBtn, menuHelpBtn, exitButton}; // Array de botones
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

        gameBoard = new GameBoardSnake(cardLayout, contentPane, musicPlayer);
        gameBoss = new GameBoardWithBoss(cardLayout, contentPane, musicPlayer);
        selectionPj = new SelectionPj(contentPane, null);
        gameOverPanel = new GameOverPanel(cardLayout, contentPane, this);
        helpPanel = new HelpPanel(contentPane);
        gameBoardBoss = new GameBoardBoss(cardLayout, contentPane, musicPlayer);
        finalPanel = new FinalPanel(contentPane);
        
        contentPane.add(mainMenuPanel, "mainMenu");
        contentPane.add(gameBoard, "gameBoard");
        contentPane.add(gameOverPanel, "gameOverPanel");
        contentPane.add(selectionPj, "selectionPjMenu");
        contentPane.add(helpPanel, "helpPanel");
        contentPane.add(gameBoardBoss, "gameBoardBoss");
        contentPane.add(finalPanel, "finalPanel");
        contentPane.add(gameBoss, "gameBoardWithBoss");
        
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

        toolTipLabel.setText(obtenerTextoTooltip(currentButtonIndex));
        this.repaint();
        this.revalidate();
    }

    private String obtenerTextoTooltip(int buttonIndex) {
        switch (buttonIndex) {
            case 0: return "Juega libremente para conseguir el puntaje más alto";
            case 1: return "Juega el modo historia para luchar contra el jefe final";
            case 2: return "Selecciona un personaje, cada personaje tiene su estilo diferente";
            case 3: return "Musíca activada: " + musicPlayer.getIsActivo();
            case 4: return "¿Una ayuda rápida antes de jugar?";
            case 5: return "¿Ya te vas?";
            default: return "";
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
        opcionIniciar = 1;
    }
    
    private void iniciarJuegoConJefe() {
        cardLayout.show(contentPane, "gameBoardWithBoss");
        gameBoss.requestFocusInWindow();
        gameBoss.setFocusable(true);
        if (gameBoss.isTimerRunning()) {
        	gameBoss.stopTimer();
        }
        gameBoss.startTimer();
        pack();
        opcionIniciar = 2;
    }
}