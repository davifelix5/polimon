package game.map.interactions;

import game.entity.Player;
import game.game_states.GameState;
import game.game_states.GameStateManager;

public class BienioExitStrategy implements LayerInteractionStrategy<Player>{
    GameStateManager gameStateManager;

    public BienioExitStrategy(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void handleElementHitsLayer(Player element) {
        int exitWorldX = 672, exitWorldY = 1664;
        element.setWorldX(exitWorldX);
        element.setWorldY(exitWorldY);
        gameStateManager.setState(GameState.Outside);
    }
}
