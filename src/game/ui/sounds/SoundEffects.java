package game.ui.sounds;

public class SoundEffects {
    SoundClass soundEffect = new SoundClass();

    public SoundEffects(){
    }

    public void stopSoundEffect(){
        soundEffect.stopAudio();
    }

    public void setSoundEffect(String pathName){
        soundEffect.setAudio(pathName);
    }

    public void playSoundEffect(){
        soundEffect.playAudio();
        soundEffect.loop();
    }
}
