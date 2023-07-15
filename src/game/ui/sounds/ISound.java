package game.ui.sounds;

import java.net.URL;

public interface ISound {
    void setAudio(String pathName);
    void playAudio();
    void stopAudio();
    void loop();
}
