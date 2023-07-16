package game.ui.sounds;

public class SoundEffect {
    Sound soundEffect = new Sound();

    public SoundEffect(){
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
