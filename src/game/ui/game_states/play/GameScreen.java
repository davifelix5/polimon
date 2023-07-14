package game.ui.game_states.play;

import game.map.TileManager;
import game.map.factory.MapFactory;
import game.ui.game_states.IState;

import java.awt.*;

public interface GameScreen {
    void render(Graphics g);
    void tick();
    void loadAnimations();
    void setMapFactory(MapFactory factory);
    TileManager getTileManager();
}
