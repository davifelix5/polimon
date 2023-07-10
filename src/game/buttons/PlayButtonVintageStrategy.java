package game.buttons;

import game.Game;
import game.handlers.MouseInteractionStrategy;
import game.game_states.GameState;
import game.map.factory.ClassicMap;
import game.map.factory.VintageMap;

public class PlayButtonVintageStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setState(GameState.Outside);
        Game.setMapFactory(new VintageMap());
    }
}
