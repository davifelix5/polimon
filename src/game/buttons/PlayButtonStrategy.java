package game.buttons;

import game.GamePanel;
import game.handlers.MouseInteractionStrategy;
import game.state.State;

public class PlayButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(GamePanel game) {
        game.setGameState(State.Game);
    }
}
