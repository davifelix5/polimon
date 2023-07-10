package game.state;

import game.map.factory.MapFactory;

import java.awt.*;

public interface IState {
    void tick();
    void render(Graphics g);
    void destroy();
    void start();
    IStateManager getStateManager();
    void setFactory(MapFactory factory);
}
