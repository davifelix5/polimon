package game.ui.buttons;

import game.Game;
import game.entity.npc.WalkNPCStrategy;
import game.entity.pokemon.WalkPokemonStrategy;
import game.ui.handlers.MouseInteractionStrategy;

public class WalkNPCPokemonButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(Game game) {
        game.getStateManager().setNPCStrategy(new WalkNPCStrategy());
        game.getStateManager().setMapPokemonStrategy(new WalkPokemonStrategy());
    }
}
