package game.buttons;

import game.Game;
import game.handlers.MouseInteractionStrategy;
import game.game_states.GameState;
import game.map.factory.ClassicMap;

public class PlayButtonClassicStrategy implements MouseInteractionStrategy {

    private final ClassicMap classicMap = new ClassicMap();
    @Override
    public void onPress(Game game) {
        game.setMapFactory(classicMap);
        game.getStateManager().setState(GameState.Outside);

    }
}
