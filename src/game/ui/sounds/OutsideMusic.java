package game.ui.sounds;

public class OutsideMusic {
    Sound outsideMusic = new Sound();

    public OutsideMusic() {
    }
    public void stopOutsideMusic(){
        outsideMusic.stopAudio();
    }
    public void playOutsideMusic(String pathName){
        outsideMusic.setAudio(pathName);
        outsideMusic.playAudio();
        outsideMusic.loop();
    }

}
