package snakeGame;

// Importaciones necesarias
import javax.swing.*;
import snakeGame.SnakeMovement.AnimalCharacter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class SelectionPj extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel msgLabel;
    private ImageIcon bgImage;
    private JPanel contentPane;
    private JPanel mainSelectPanel;

    public static AnimalCharacter personajeSeleccionado; // Variable estática
    private CharacterSelection[] buttons; // Array para los botones
    private int currentButtonIndex = 0; // Índice del botón actual

    public static void setPersonajeSeleccionado(AnimalCharacter personaje) {
        personajeSeleccionado = personaje;
    }

    public static AnimalCharacter obtenerPersonajeSeleccionado() {
        return personajeSeleccionado;
    }

    public static boolean personajeSeleccionado() {
        return personajeSeleccionado != null; // Devuelve true si hay un personaje seleccionado
    }

    public SelectionPj(JPanel contentPane, AnimalCharacter defaultCharacter) {
        setSize(600, 600);
        setLayout(new BorderLayout(0, 0));
        bgImage = new ImageIcon(getClass().getResource("selection-bg.png"));
        this.contentPane = contentPane;

        setPersonajeSeleccionado(defaultCharacter); // Establecer el personaje por defecto

        // Panel para las imágenes y botones
        mainSelectPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        mainSelectPanel.setLayout(new BoxLayout(mainSelectPanel, BoxLayout.Y_AXIS));

        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        mainSelectPanel.add(rigidArea_1);

        JLabel tituloPj = new JLabel("Personajes");
        tituloPj.setForeground(new Color(255, 255, 255));
        tituloPj.setFont(new Font("Power Red and Blue", Font.BOLD, 29));
        tituloPj.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainSelectPanel.add(tituloPj);

        // Espaciado
        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        mainSelectPanel.add(rigidArea);

        // Mensaje personaje seleccionado
        msgLabel = new JLabel("");
        msgLabel.setForeground(new Color(255, 255, 255));
        msgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
        mainSelectPanel.add(msgLabel);

        // Crear lista temporal para los botones de personajes
        crearBotones();

        // Agregar el panel principal al panel de selección
        add(mainSelectPanel, BorderLayout.CENTER);

        setupKeyListener(); // Configurar el KeyListener
        highlightCurrentButton(); // Resaltar el botón actual
        // Asegurarse de que el panel tenga el foco
        requestFocusInWindow();
    }

    public void crearBotones() {
        ArrayList<CharacterSelection> tempButtons = new ArrayList<>();
        tempButtons.add(new CharacterSelection(AnimalCharacter.SERPIENTE, "Felipe", new ImageIcon(getClass().getResource("serpientearriba.png")), msgLabel, this));
        tempButtons.add(new CharacterSelection(AnimalCharacter.CARPINCHO, "Joako", new ImageIcon(getClass().getResource("carpinchoabajo.png")), msgLabel, this));
        tempButtons.add(new CharacterSelection(AnimalCharacter.TIBURON, "Tibu", new ImageIcon(getClass().getResource("tiburonder.png")), msgLabel, this));
        tempButtons.add(new CharacterSelection(AnimalCharacter.GALLINA, "Kopeti", new ImageIcon(getClass().getResource("gallinaabajo.png")), msgLabel, this));
        tempButtons.add(new CharacterSelection(AnimalCharacter.MOSCA, "Moska", new ImageIcon(getClass().getResource("moskaabajo.png")), msgLabel, this));
        tempButtons.add(new CharacterSelection(AnimalCharacter.TERMO, "Termonaitor", new ImageIcon(getClass().getResource("termoabajo.png")), msgLabel, this));

        // Agregar el botón "Volver al Menú" como un nuevo CharacterSelection
        tempButtons.add(new CharacterSelection(null, "Volver al Menú", null, msgLabel, this));

        buttons = tempButtons.toArray(new CharacterSelection[0]);

        // Agregar botones al panel
        for (CharacterSelection button : buttons) {
            mainSelectPanel.add(button);
            mainSelectPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        }

        mainSelectPanel.revalidate(); // Revalida el panel
        mainSelectPanel.repaint();
    }


    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                    currentButtonIndex = (currentButtonIndex - 1 + buttons.length) % buttons.length;
                    highlightCurrentButton();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                    currentButtonIndex = (currentButtonIndex + 1) % buttons.length;
                    highlightCurrentButton();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buttons[currentButtonIndex].doClick(); // Simula un clic en el botón actual
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    mostrarMenuPrincipal();
                }
            }
        });
        setFocusable(true); // Permitir que el panel sea enfocado
    }

    private void highlightCurrentButton() {
        for (int i = 0; i < buttons.length; i++) {
            if (i == currentButtonIndex) {
                buttons[i].highlight(); // Resaltar el botón actual
            } else {
                buttons[i].unhighlight(); // Quitar resaltado de otros botones
            }
        }
    }

    void mostrarMenuPrincipal() {
        CardLayout layout = (CardLayout) contentPane.getLayout();
        layout.show(contentPane, "mainMenu");

        GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1);
        gameBoard.reiniciarJuego(); // Reiniciar con el nuevo personaje seleccionado
    }
}
