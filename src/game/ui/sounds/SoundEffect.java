package game.ui.sounds;

/**
 * Abstração para o uso de efeitos sonoros, envelopando a classe Sound
 */
public class SoundEffect {

    private final Sound soundEffect;

    public SoundEffect(String pathname){
        this.soundEffect = new Sound(pathname);
    }

    public void stop(){
        soundEffect.stop();
    }

    public void play(){
        soundEffect.play();
        soundEffect.loop();
    }
}
