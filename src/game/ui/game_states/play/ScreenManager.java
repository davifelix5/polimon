package game.ui.game_states.play;

public interface ScreenManager {
    void setCurrentScreenIndex(int index);

    GameScreen getBattleScreen();
}
