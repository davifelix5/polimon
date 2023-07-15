package game.ui.sounds;

public class MainMenuMusic{
    SoundClass mainMenuMusic = new SoundClass();

    public MainMenuMusic() {
    }
    public void stopMainMenuMusic(){
        mainMenuMusic.stopAudio();
    }
    public void playMainMenuMusic(String pathName){
        mainMenuMusic.setAudio(pathName);
        mainMenuMusic.playAudio();
        mainMenuMusic.loop();
    }

}
