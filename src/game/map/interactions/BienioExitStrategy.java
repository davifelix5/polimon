package game.map.interactions;

import game.entity.player.Player;
import game.ui.game_states.play.ScreenManager;

public class BienioExitStrategy implements LayerInteractionStrategy<Player>{
    ScreenManager screenManager;

    public BienioExitStrategy(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    @Override
    public void handleElementHitsLayer(Player element) {
        int exitWorldX = 672, exitWorldY = 1664;
        element.setWorldX(exitWorldX);
        element.setWorldY(exitWorldY);
        screenManager.setCurrentScreenIndex(0);
    }
}
