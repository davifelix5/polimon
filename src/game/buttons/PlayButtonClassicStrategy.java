package game.buttons;

import game.Game;
import game.handlers.MouseInteractionStrategy;
import game.game_states.GameState;

public class PlayButtonClassicStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.setMapFactory("Classic");
        game.getStateManager().setState(GameState.Outside);

    }
}
