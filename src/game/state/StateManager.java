package game.state;

public class StateManager implements IStateManager {
    private final IState[] states = new IState[StateID.values().length];
    private StateID currentState;

    @Override
    public IState getCurrentState() {
        return states[currentState.getValue()];
    }

    @Override
    public void setState(StateID id) {
        if (this.currentState != null) {
            this.getCurrentState().destroy();
        }
        this.currentState = id;
    }

    @Override
    public void addState(StateID id, IState state) {
        this.states[id.getValue()] = state;
    }
}
