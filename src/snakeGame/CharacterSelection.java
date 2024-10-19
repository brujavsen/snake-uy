package snakeGame;

import javax.swing.*;
import snakeGame.SnakeMovement.AnimalCharacter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CharacterSelection extends JButton {
    private static final long serialVersionUID = 1L;
    private JLabel msgLabel;
    private AnimalCharacter characterType;
    private SelectionPj selectionPanel; // Añadido para referencia al panel de selección

    public CharacterSelection(AnimalCharacter characterType, String buttonText, ImageIcon icon, JLabel msgLabel, SelectionPj selectionPanel) {
        super(buttonText);
        this.characterType = characterType; // Guardar el tipo de personaje
        this.msgLabel = msgLabel; // Guardar referencia al JLabel
        this.selectionPanel = selectionPanel; // Guardar referencia al panel de selección

        // propiedades del botón
        setIcon(icon);
        setForeground(Color.WHITE);
        setBackground(new Color(0, 64, 0));
        setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // efecto de hover en botón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(new Font("Power Red and Blue", Font.PLAIN, 25));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
            }
        });

        // acción al dar clic
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCharacter();
            }
        });
    }

    private void selectCharacter() {
        if (characterType != null) {
            SelectionPj.setPersonajeSeleccionado(characterType);
            msgLabel.setText("Seleccionado correctamente: " + characterType);
        } else {
            selectionPanel.mostrarMenuPrincipal();
        }
    }

    public void highlight() {
        setFont(new Font("Power Red and Blue", Font.PLAIN, 25)); // Cambia el tamaño o estilo
        setForeground(Color.YELLOW); // Cambia el color o estilo
    }

    public void unhighlight() {
        setFont(new Font("Power Red and Blue", Font.PLAIN, 20)); // Tamaño original
        setForeground(Color.WHITE); // Color original
    }
}
