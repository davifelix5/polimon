package game.buttons;

import game.Game;
import game.handlers.MouseInteractionStrategy;
import game.game_states.GameState;

public class PlayButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setState(GameState.Outside);
    }
}
