package game.ui.buttons;

import game.Game;
import game.entity.npc.FixedNPCStrategy;
import game.ui.handlers.MouseInteractionStrategy;
import game.entity.pokemon.FixedPokemonStrategy;

public class FixedNPCPokemonButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setNPCStrategy(new FixedNPCStrategy());
        game.getStateManager().setMapPokemonStrategy(new FixedPokemonStrategy());
    }
}
