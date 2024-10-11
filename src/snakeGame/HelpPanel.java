package snakeGame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.Box;
import java.awt.Dimension;

public class HelpPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private ImageIcon bgImage;
    private ImageIcon[] helpImages;
    private String[] helpTexts = {
        "El hoyo negro te hara perder uno a uno los mates tomados",
        "El mate te hará crecer",
        "El mate Argentino te dará velocidad"
    };

    public HelpPanel(JPanel contentPane) {
        setSize(600, 600);
        setLayout(new BorderLayout(20, 20));
        bgImage = new ImageIcon(getClass().getResource("selection-bg.png"));
        
        // Cargar imágenes de ayuda
        helpImages = new ImageIcon[]{
            new ImageIcon(getClass().getResource("hoyo.png")),
            new ImageIcon(getClass().getResource("mt-mate.png")),
            new ImageIcon(getClass().getResource("mateArg.png"))
        };

        // Panel para las imágenes
        JPanel mainHelpPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Cambiar a BoxLayout en fila
        mainHelpPanel.setLayout(new BoxLayout(mainHelpPanel, BoxLayout.Y_AXIS));
        
        // Añadir el panel principal a HelpPanel
        add(mainHelpPanel, BorderLayout.CENTER);
        
        Component rigidArea_8 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_8);
        
        JLabel tituloLabel = new JLabel("Cómo Jugar");
        tituloLabel.setForeground(new Color(0, 128, 64));
        tituloLabel.setFont(new Font("Lato", Font.BOLD | Font.ITALIC, 30));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(tituloLabel);
        
        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea);
        
        JLabel imageLabel = new JLabel(helpImages[0]);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(imageLabel);
        
        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_1);
        
        JLabel hoyoLabel = new JLabel(helpTexts[0]);
        hoyoLabel.setFont(new Font("Lato", Font.BOLD, 17));
        hoyoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(hoyoLabel);
        
        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_2);
        
        JLabel imageLabel2 = new JLabel(helpImages[1]);
        imageLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(imageLabel2);
        
        Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_3);
        
        JLabel lblNewLabel = new JLabel(helpTexts[1]);
        lblNewLabel.setFont(new Font("Lato", Font.BOLD, 17));
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(lblNewLabel);
        
        Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_4);
        
        JLabel lblNewLabel_1 = new JLabel(helpImages[2]);
        lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(lblNewLabel_1);
        
        Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_5);
        
        JLabel lblNewLabel_2 = new JLabel(helpTexts[2]);
        lblNewLabel_2.setFont(new Font("Lato", Font.BOLD, 17));
        lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(lblNewLabel_2);
        
        // Botón para volver
        JButton exitBtn = new JButton("Volver al Menú");
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) contentPane.getLayout();
                layout.show(contentPane, "mainMenu");

                GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1);
                gameBoard.reiniciarJuego(); // Reiniciar con el nuevo personaje seleccionado
            }
        });
        
        Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_6);
        
        Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_7);
        
        
        exitBtn.setBackground(new Color(255, 150, 100));
        exitBtn.setForeground(new Color(0, 0, 0));
        exitBtn.setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(exitBtn);
    }
}