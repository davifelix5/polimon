package game.map.interactions;

public interface LayerInteractionStrategy<Interactable> {

    void handleElementHitsLayer(Interactable element);

}
