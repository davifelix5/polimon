package game.state;

public interface IStateManager {
     IState getCurrentState();
     void setState(StateID id);
     void addState(StateID id, IState state);
}
