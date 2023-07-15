package game.ui.sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundClass implements ISound{
    Clip clip;
    public SoundClass() {

    }

    @Override
    public void setAudio(String pathName) {
        try{
            clip = AudioSystem.getClip();
            AudioInputStream music = AudioSystem.getAudioInputStream(new File(pathName).getAbsoluteFile());
            clip.open(music);
        } catch (Exception e) {
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
