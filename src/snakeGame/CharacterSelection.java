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

    public CharacterSelection(AnimalCharacter characterType, String buttonText, ImageIcon icon, JLabel msgLabel) {
        super(buttonText);
        this.characterType = characterType; // Guardar el tipo de personaje
        this.msgLabel = msgLabel; // Guardar referencia al JLabel

        // propiedades del boton
        setIcon(icon);
        setForeground(Color.WHITE);
        setBackground(new Color(0, 64, 0));
        setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // efecto de hover en boton
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

        // accion al dar click
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCharacter();
            }
        });
    }

    // Método para seleccionar el personaje
    private void selectCharacter() {
        SelectionPj.setPersonajeSeleccionado(characterType);
        msgLabel.setText("Seleccionado correctamente: " + characterType);
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