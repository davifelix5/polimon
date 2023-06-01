package game.elements;

import game.GamePanel;
import game.handlers.MouseInteractionStrategy;

public class ExitButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(GamePanel game) {
        System.exit(0);
    }
}
