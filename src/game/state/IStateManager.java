package game.state;

import game.game_states.GameState;

public interface IStateManager {
     IState getCurrentState();
     void setState(GameState id);
     void addState(GameState id, IState state);
}
