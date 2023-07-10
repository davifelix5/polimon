package game.state;

import game.entity.NPCStrategy;
import game.game_states.GameState;

public interface IStateManager {
     IState getCurrentState();
     void setState(GameState id);
     void addState(GameState id, IState state);
     void setNPCStrategy(NPCStrategy strategy);
}
