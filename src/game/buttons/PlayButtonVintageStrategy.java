package game.buttons;

import game.Game;
import game.game_states.GameState;
import game.handlers.MouseInteractionStrategy;
import game.map.factory.VintageMap;

public class PlayButtonVintageStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.setMapFactory(new VintageMap());
        game.getStateManager().setState(GameState.Outside);
    }
}
