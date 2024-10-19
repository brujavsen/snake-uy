package snakeGame;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    private JPanel contentPane;
    private String[] helpTexts = {
        "El hoyo negro te hara perder uno a uno los mates tomados",
        "El mate te hará crecer",
        "El mate Argentino te dará velocidad",
        "Te mueves con las flechas de dirección o si prefieres 'AWSD'"
    };
    private JButton exitBtn; // Botón declarado como atributo de clase

    public HelpPanel(JPanel contentPane) {
        setSize(600, 600);
        setLayout(new BorderLayout(20, 20));
        bgImage = new ImageIcon(getClass().getResource("selection-bg.png"));
        this.contentPane = contentPane;
        
        // Cargar imágenes de ayuda
        helpImages = new ImageIcon[]{
            new ImageIcon(getClass().getResource("hoyo.gif")),
            new ImageIcon(getClass().getResource("mt-mate.png")),
            new ImageIcon(getClass().getResource("mateArg.png")),
            new ImageIcon(getClass().getResource("controles.png"))
        };

        // Panel para las imágenes
        JPanel mainHelpPanel = new JPanel() {

            private static final long serialVersionUID = 1L;

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
        tituloLabel.setForeground(new Color(255, 255, 255));
        tituloLabel.setFont(new Font("Power Red and Blue", Font.BOLD | Font.ITALIC, 30));
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
        hoyoLabel.setForeground(new Color(255, 255, 255));
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
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Lato", Font.BOLD, 17));
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(lblNewLabel);
        
        Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_4);
        
        JLabel imageLabel3 = new JLabel(helpImages[2]);
        imageLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(imageLabel3);
        
        Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_5);
        
        JLabel lblNewLabel_2 = new JLabel(helpTexts[2]);
        lblNewLabel_2.setForeground(new Color(255, 255, 255));
        lblNewLabel_2.setFont(new Font("Lato", Font.BOLD, 17));
        lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(lblNewLabel_2);
        
        Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_6);
        
        JLabel controlesLabelImage = new JLabel(helpImages[3]);
        controlesLabelImage.setForeground(new Color(255, 255, 255));
        controlesLabelImage.setFont(new Font("Lato", Font.BOLD, 17));
        controlesLabelImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(controlesLabelImage);
        
        Component rigidArea_9 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_9);
        
        JLabel controlesLabelText = new JLabel(helpTexts[3]);
        controlesLabelText.setForeground(new Color(255, 255, 255));
        controlesLabelText.setFont(new Font("Lato", Font.BOLD, 17));
        controlesLabelText.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(controlesLabelText);
        
        Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
        mainHelpPanel.add(rigidArea_7);
        
        // Botón para volver
        exitBtn = new JButton("Volver al Menú"); // Asegúrate de que exitBtn sea accesible
        exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) contentPane.getLayout();
                layout.show(contentPane, "mainMenu");

                GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1);
                gameBoard.reiniciarJuego(); // Reiniciar con el nuevo personaje seleccionado
            }
        });
        
        exitBtn.setBackground(new Color(255, 150, 100));
        exitBtn.setForeground(new Color(0, 0, 0));
        exitBtn.setFont(new Font("Power Red and Blue", Font.PLAIN, 20));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainHelpPanel.add(exitBtn);
        setupKeyListener();
    }
    
    private void setupKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    CardLayout layout = (CardLayout) contentPane.getLayout();
                    layout.show(contentPane, "mainMenu");

                    GameBoardSnake gameBoard = (GameBoardSnake) contentPane.getComponent(1);
                    gameBoard.reiniciarJuego();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Simular clic en el botón "Volver al Menú"
                    exitBtn.doClick(); // Asegúrate de que exitBtn sea accesible aquí
                }
            }
        });
        setFocusable(true); // Permitir que el panel sea enfocado
    }
}
