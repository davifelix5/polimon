package game.map.interactions;

import game.entity.player.Player;
import game.ui.game_states.play.ScreenManager;

public class BienioEnterStrategy implements LayerInteractionStrategy<Player> {

    ScreenManager screenManager;

    public BienioEnterStrategy(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void handleElementHitsLayer(Player element) {
        int enterWorldX = 888, enterWorldY = 538;
        element.setWorldX(enterWorldX);
        element.setWorldY(enterWorldY);
        screenManager.setCurrentScreenIndex(1);
    }
}
