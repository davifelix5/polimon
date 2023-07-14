package game.ui.buttons;

import game.Game;
import game.ui.handlers.MouseInteractionStrategy;
import game.ui.game_states.GameState;

public class PlayButtonClassicStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.setMapFactory("Classic");
        game.getStateManager().setState(GameState.Outside);

    }
}
