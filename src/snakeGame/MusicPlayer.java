package snakeGame;

import javax.sound.sampled.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    private Clip clip;
    private Clip clipOne;

    public void playMusic(String musicFile) {
        try {
            InputStream audioStream = getClass().getResourceAsStream("/snakeGame/musica/" + musicFile);
            if (audioStream == null) {
                System.out.println("Error: No se encontró el archivo de audio " + musicFile);
                return;
            }
            
            // Envuelve el InputStream en un BufferedInputStream
            BufferedInputStream bufferedStream = new BufferedInputStream(audioStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void playOneTime(String musicFile) {
        try {
            InputStream audioStream = getClass().getResourceAsStream("/snakeGame/musica/" + musicFile);
            if (audioStream == null) {
                System.out.println("Error: No se encontró el archivo de audio " + musicFile);
                return;
            }

            // Envuelve el InputStream en un BufferedInputStream
            BufferedInputStream bufferedStream = new BufferedInputStream(audioStream);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedStream);
            
            clipOne = AudioSystem.getClip();
            clipOne.open(audioInputStream);
            clipOne.start();
            
            // Agregar un LineListener para detener el clip cuando termine
            clipOne.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                	clipOne.close(); // Cierra el clip cuando termina la reproducción
                }
            });
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
