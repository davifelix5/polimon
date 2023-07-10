package game.buttons;

import game.Game;
import game.game_states.GameState;
import game.handlers.MouseInteractionStrategy;
import game.map.factory.ClassicMap;

public class PlayButtonClassicStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        Game.setMapFactory(new ClassicMap());
        game.getStateManager().setState(GameState.Outside);
    }
}
