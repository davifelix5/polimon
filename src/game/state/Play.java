package game.state;

import game.animation.SpriteSheet;
import game.entity.Player;
import game.handlers.KeyHandler;
import game.map.Map;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Play {
    KeyHandler keyHandler;
    Player player;

    private SpriteSheet spritesBienio;

    public Play(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
        player = new Player(50, 50, 2, keyHandler);
        readSpriteImages();
    }

    public void tick() {
        player.tick();
    }

    public void render(Graphics g) {
        try {

            // Map background
            BufferedImage background = ImageIO.read(new FileInputStream("src/game/res/mapas/bienio1-chao.png"));
            g.drawImage(background, 0, 0, null);

            // Mesas
            BufferedReader mesasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/mesas-tilemap.csv"));
            int[][] mesasSpriteMap = {
                    {0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}, {0, 5}, {1, 12}, {1, 13}, {2, 0}, {0, 8}, {0, 9}, {0,10},
                    {0, 11}, {0, 12}, {0, 13}, {2, 7}, {2, 8}, {2, 9}, {2, 1}, {2, 2}, {2, 3}, {2, 4}, {2, 5}, {2, 6},
                    {1, 0}, {1, 1}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6}, {1, 7}, {1, 8}, {1, 9}, {1, 10}, {1, 11}
            };
            Map mesasMap = new Map(spritesBienio, mesasTileMap, mesasSpriteMap);
            mesasMap.render(g);

            // Cadeiras
            BufferedReader cadeirasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/cadeiras-tilemap.csv"));
            int[][] cadeirasSpriteMap = {{0, 6}, {0, 7}, {1, 9}, {1, 10}, {1, 11}};
            Map cadeirasMap = new Map(spritesBienio, cadeirasTileMap, cadeirasSpriteMap);
            cadeirasMap.render(g);

            player.render(g);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSpriteImages() {
        try {
            BufferedImage bienioSpritesImage = ImageIO.read(new FileInputStream("src/game/res/sprites/bienio-sprites.png"));
            this.spritesBienio = new SpriteSheet(bienioSpritesImage, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
