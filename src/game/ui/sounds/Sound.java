package game.ui.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound implements ISound{
    Clip clip;
    public Sound(String pathname) {
        try{
            clip = AudioSystem.getClip();
            AudioInputStream music = AudioSystem.getAudioInputStream(new File(pathname).getAbsoluteFile());
            clip.open(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        clip.start();
    }

    @Override
    public void stop() {
        clip.stop();
    }

    @Override
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void restart() {
        clip.setMicrosecondPosition(0);
    }
}
