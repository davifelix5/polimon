package game.ui.sounds;

public class MainMenuMusic{
    Sound mainMenuMusic = new Sound();

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
