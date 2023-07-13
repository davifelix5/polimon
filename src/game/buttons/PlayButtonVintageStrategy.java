package game.buttons;

import game.Game;
import game.game_states.GameState;
import game.handlers.MouseInteractionStrategy;

public class PlayButtonVintageStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.setMapFactory("Vintage");
        game.getStateManager().setState(GameState.Outside);
    }
}
