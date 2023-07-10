package game.buttons;

import game.Game;
import game.entity.StoppedNPCStrategy;
import game.handlers.MouseInteractionStrategy;

public class StopNPCsButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setNPCStrategy(new StoppedNPCStrategy());
    }
}
