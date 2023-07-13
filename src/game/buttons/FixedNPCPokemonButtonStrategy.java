package game.buttons;

import game.Game;
import game.entity.FixedNPCStrategy;
import game.handlers.MouseInteractionStrategy;
import game.pokemon.FixedPokemonStrategy;

public class FixedNPCPokemonButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setNPCStrategy(new FixedNPCStrategy());
        game.getStateManager().setMapPokemonStrategy(new FixedPokemonStrategy());
    }
}
