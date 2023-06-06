package game.buttons;

import game.Game;
import game.handlers.MouseInteractionStrategy;

public class ExitButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        System.exit(0);
    }
}
