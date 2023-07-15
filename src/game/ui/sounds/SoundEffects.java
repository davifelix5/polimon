package game.ui.sounds;

public class SoundEffects {
    SoundClass soundEffect = new SoundClass();

    public SoundEffects(){
    }

    public void stopSoundEffect(){
        soundEffect.stopAudio();
    }

    public void playSoundEffect(String pathName){
        soundEffect.setAudio(pathName);
        soundEffect.playAudio();
    }
}
