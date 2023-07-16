package game.map;

import game.entity.animation.SpriteSheet;
import game.entity.player.Player;
import game.map.interactions.InteractableLayer;
import game.map.interactions.LayerInteractionStrategy;

public class PlayerInteractableLayer extends MapLayer implements InteractableLayer {

    LayerInteractionStrategy<Player> interactionStrategy;
    Player element;

    public PlayerInteractableLayer(String tilemapPath, SpriteSheet spriteSheet, LayerInteractionStrategy<Player> interactionStrategy, Player element) {
        super(tilemapPath, spriteSheet, false);
        this.interactionStrategy = interactionStrategy;
        this.element = element;
        this.interactable = true;
    }

    @Override
    public void handleInteraction() {
        interactionStrategy.handleElementHitsLayer(element);
    }

    @Override
    public Player getElement() {
        return element;
    }


}
