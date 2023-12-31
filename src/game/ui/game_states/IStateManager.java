package game.ui.game_states;

import game.entity.npc.NPCStrategy;
import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;


public interface IStateManager {
     IState getCurrentState();
     void setState(GameState id);
     void addState(GameState id, IState state);
     void setNPCStrategy(NPCStrategy strategy);
     void setMapPokemonStrategy(MapPokemonStrategy strategy);
     void setFactory(MapFactory factory);
}
