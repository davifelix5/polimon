package game.npc;

import game.Game;
import game.state.IState;
import game.state.IStateManager;

import java.awt.*;

public class DialogueScreen implements IState {

    IStateManager stateManager;


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        int x = Game.tileSize*2;
        int y = Game.tileSize/2;

        int width = Game.width - (Game.tileSize*4);
        int height = Game.tileSize*4;

        Color color = new Color(25, 25, 25, 200);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 35, 35 );

        color = new Color(255, 255, 255);
        g.drawRoundRect(x+5, y+5, width -10, height - 10, 25, 25);
    }

    @Override
    public void destroy() {

    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }

}
