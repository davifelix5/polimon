package game.state;

import game.entity.NPCStrategy;
import game.game_states.GameState;
import game.map.factory.MapFactory;


public interface IStateManager {
     IState getCurrentState();
     void setState(GameState id);
     void addState(GameState id, IState state);
     void setNPCStrategy(NPCStrategy strategy);
     void setFactory(MapFactory factory);
}
