package game.buttons;

import game.Game;
import game.handlers.MouseInteractionStrategy;
import game.state.StateID;

public class PlayButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setState(StateID.Outside);
    }
}
