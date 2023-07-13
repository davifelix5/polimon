package game.buttons;

import game.Game;
import game.entity.WalkNPCStrategy;
import game.pokemon.WalkPokemonStrategy;
import game.handlers.MouseInteractionStrategy;

public class WalkNPCPokemonButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setNPCStrategy(new WalkNPCStrategy());
        game.getStateManager().setMapPokemonStrategy(new WalkPokemonStrategy());
    }
}
