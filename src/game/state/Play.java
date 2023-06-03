package game.state;

import game.entity.Player;
import game.handlers.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Play {
    KeyHandler keyHandler;
    Player player;

    public Play(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        player = new Player(50, 50, 2, keyHandler);
    }

    public void tick() {
        player.tick();
    }

    public void render(Graphics g) {
        try {
            BufferedImage background = ImageIO.read(new FileInputStream("src/game/res/mapas/bienio1-chao.png"));
            g.drawImage(background, 0, 0, null);
            player.render(g);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
