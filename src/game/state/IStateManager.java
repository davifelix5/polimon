package game.state;

import game.game_states.GameState;
import game.map.factory.MapFactory;


public interface IStateManager {
     IState getCurrentState();
     void setState(GameState id);
     void addState(GameState id, IState state);
     void setFactory(MapFactory factory);
}
