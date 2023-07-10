package game.game_states;

import game.map.factory.MapFactory;
import game.state.IState;
import game.state.IStateManager;

public class GameStateManager implements IStateManager {
    private final IState[] states = new IState[GameState.values().length];
    private GameState currentState;

    @Override
    public IState getCurrentState() {
        return states[currentState.getValue()];
    }

    @Override
    public void setState(GameState id) {
        if (this.currentState != null) {
            this.getCurrentState().destroy();
        }
        this.currentState = id;
        this.getCurrentState().start();
    }

    @Override
    public void setFactory(MapFactory factory) {
        for (IState s: states) {
            if (s!=null) {
                s.setFactory(factory);
            }
        }
    }

    @Override
    public void addState(GameState id, IState state) {
        this.states[id.getValue()] = state;
    }
}
