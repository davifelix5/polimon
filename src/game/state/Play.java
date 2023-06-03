package game.state;

import game.entity.Player;
import game.handlers.KeyHandler;

import java.awt.*;

public class Play {
    KeyHandler keyHandler;
    Player player;

    public Play(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        player = new Player(50, 50, keyHandler);
    }

    public void tick() {
        player.tick();
    }

    public void render(Graphics g) {
        player.render(g);
    }
}
