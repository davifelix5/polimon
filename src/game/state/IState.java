package game.state;

import java.awt.*;

public interface IState {
    void tick();
    void render(Graphics g);
    IStateManager getStateManager();
}
