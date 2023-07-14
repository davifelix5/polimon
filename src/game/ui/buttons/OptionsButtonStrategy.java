package game.ui.buttons;

import game.Game;
import game.ui.handlers.MouseInteractionStrategy;
import game.ui.game_states.GameState;

public class OptionsButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {game.getStateManager().setState(GameState.Combate);}
}
