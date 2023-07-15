package game.ui.buttons;

import game.Game;
import game.ui.game_states.GameState;
import game.ui.handlers.MouseInteractionStrategy;

public class PlayButtonVintageStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.setMapFactory("Vintage");
        game.getStateManager().setState(GameState.Outside);
        game.mainMenuMusic.stopMainMenuMusic();

    }
}
