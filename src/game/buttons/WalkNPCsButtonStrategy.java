package game.buttons;

import game.Game;
import game.entity.WalkNPCStrategy;
import game.handlers.MouseInteractionStrategy;

public class WalkNPCsButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setNPCStrategy(new WalkNPCStrategy());
    }
}
