package game.state;

import game.entity.NPCStrategy;

import java.awt.*;

public interface IState {
    void tick();
    void render(Graphics g);
    void destroy();
    void start();
    void setNPCStrategy(NPCStrategy strategy);
    IStateManager getStateManager();
}
