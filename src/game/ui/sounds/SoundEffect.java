package game.ui.sounds;

public class SoundEffect {

    private final Sound soundEffect;

    public SoundEffect(String pathname){
        this.soundEffect = new Sound(pathname);
    }

    public void stopSoundEffect(){
        soundEffect.stop();
    }

    public void playSoundEffect(){
        soundEffect.play();
        soundEffect.loop();
    }
}
