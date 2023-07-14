package game.map.interactions;

import game.entity.player.Player;
import game.ui.game_states.GameStateManager;
import game.ui.game_states.GameState;

public class BienioEnterStrategy implements LayerInteractionStrategy<Player> {
    GameStateManager gameStateManager;

    public BienioEnterStrategy(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
    }

    @Override
    public void handleElementHitsLayer(Player element) {
        int enterWorldX = 888, enterWorldY = 538;
        element.setWorldX(enterWorldX);
        element.setWorldY(enterWorldY);
        gameStateManager.setState(GameState.Bienio);
    }
}
