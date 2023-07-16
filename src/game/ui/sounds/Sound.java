package game.ui.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound implements ISound{
    Clip clip;
    public Sound() {

    }

    @Override
    public void setAudio(String pathName) {
        try{
            clip = AudioSystem.getClip();
            AudioInputStream music = AudioSystem.getAudioInputStream(new File(pathName).getAbsoluteFile());
            clip.open(music);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playAudio() {
        clip.start();
    }

    @Override
    public void stopAudio() {
        clip.stop();
    }

    @Override
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
