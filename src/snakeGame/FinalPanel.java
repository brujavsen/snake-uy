package snakeGame;

import java.awt.CardLayout;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class FinalPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ImageIcon bgImage;
	private MusicPlayer musicPlayer;
	private boolean isMusicPlaying;

	public FinalPanel(JPanel contentPane) {
		setSize(600, 600);
        bgImage = new ImageIcon(getClass().getResource("bg-final.png"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        musicPlayer = new MusicPlayer();
        
        isMusicPlaying = false;
        
        Component rigidArea = Box.createRigidArea(new Dimension(20, 70));
        add(rigidArea);
        
        JLabel textoFinal1 = new JLabel("Felicidades has conseguido derrotar al Jefe Final");
        textoFinal1.setFont(new Font("Lato", Font.BOLD, 27));
        textoFinal1.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(textoFinal1);
        
        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        add(rigidArea_1);
        
        JLabel textoFinal2 = new JLabel("Gracias por haber jugado");
        textoFinal2.setFont(new Font("Lato", Font.BOLD | Font.ITALIC, 21));
        textoFinal2.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(textoFinal2);
        
        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 400));
        add(rigidArea_2);
        
        JButton volverMenuBtn = new JButton("Volver al Menú");
        volverMenuBtn.setForeground(new Color(255, 255, 255));
        volverMenuBtn.setBackground(new Color(255, 0, 128));
        volverMenuBtn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CardLayout layout = (CardLayout) contentPane.getLayout();
                layout.show(contentPane, "mainMenu");

                GameBoardWithBoss gameBoss = (GameBoardWithBoss) contentPane.getComponent(7);
                gameBoss.reiniciarJuego(); // Reiniciar con el nuevo personaje seleccionado
                detenerMusica();
                repaint();
        	}
        });
        volverMenuBtn.setFont(new Font("Power Red and Blue", Font.PLAIN, 25));
        volverMenuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(volverMenuBtn);

	}
	
	// Reproducción de música solo cuando el panel es visible
    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag && !isMusicPlaying) {
            musicPlayer.playMusic("final.WAV");
            isMusicPlaying = true;
        } else if (!aFlag) {
        	detenerMusica();
        }
    }

    private void detenerMusica() {
        if (isMusicPlaying) {
            musicPlayer.stopMusic();
            isMusicPlaying = false;
        }
    }
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    }
}
