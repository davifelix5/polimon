package game.map.interactions;

import game.entity.player.Player;

public class SwimStrategy implements LayerInteractionStrategy<Player> {
    @Override
    public void handleElementHitsLayer(Player element) {
        element.setSwimming(true);
    }
}
