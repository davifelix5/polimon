package game.npc;

import game.Game;
import game.state.IState;
import game.state.IStateManager;

import java.awt.*;
import java.util.ArrayList;

public class DialogueScreen implements IState {

    IStateManager stateManager;

    ArrayList<String> dialogues = new ArrayList<>();


    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        int x = Game.tileSize * 7;
        int y = Game.tileSize / 2;

        int width = Game.width - (Game.tileSize * 12);
        int height = Game.tileSize * 5;

        Color color = new Color(25, 25, 25, 200);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 35, 35);

        Color color2 = new Color(255, 255, 255);
        g.setColor(color2);
        g.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

        Font dialogueFont = new Font("arial", Font.PLAIN, 20);
        g.setFont(dialogueFont);

    }


    @Override
    public void destroy() {

    }

    @Override
    public void start() {

    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }

}
