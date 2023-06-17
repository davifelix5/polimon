package game.map.interactions;

import game.entity.Entity;

public interface InteractableLayer {
    void handleInteraction();

    Entity getElement();

}
