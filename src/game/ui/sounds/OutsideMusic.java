package game.ui.sounds;

public class OutsideMusic {
    SoundClass outsideMusic = new SoundClass();

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
