package game.ui.buttons;

import game.Game;
import game.ui.handlers.MouseInteractionStrategy;

public class ExitButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        System.exit(0);
    }
}
