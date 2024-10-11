package snakeGame;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    private Clip clip;
    private Clip effectClip;

    public void playMusic(String musicFile) {
        try {
            InputStream audioStream = getClass().getResourceAsStream("/" + musicFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Reproduce en bucle
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    
    public void playOneTimeMusic(String musicFile) {
        try {
            InputStream audioStream = getClass().getResourceAsStream("/" + musicFile);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);
            effectClip = AudioSystem.getClip();
            effectClip.open(audioInputStream);
            effectClip.start();
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
