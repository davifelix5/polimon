package game.state;

import game.animation.SpriteSheet;
import game.entity.Player;
import game.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.Tile;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class Bienio implements IState {
    KeyHandler keyHandler;
    Player player;
    IStateManager stateManager;
    private final TileManager tm = new TileManager();

    private SpriteSheet spritesBienio;

    public Bienio(KeyHandler keyHandler, IStateManager stateManager) {
        this.keyHandler = keyHandler;
        this.stateManager = stateManager;
        player = new Player(70, 50, 2, keyHandler);
        readSpriteImages();
    }

    public void tick() {
        player.tick();
        player.setColiding(tm.colides(player));
    }

    public void render(Graphics g) {
        try {

            // Map background
            BufferedImage background = ImageIO.read(new FileInputStream("src/game/res/mapas/bienio1-chao.png"));
            g.drawImage(background, 0, 0, null);

            player.render(g);
            // Mesas
            BufferedReader mesasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/mesas-tilemap.csv"));
            Tile[] mesasTiles = {
                    new Tile(spritesBienio, 0, 0, true), new Tile(spritesBienio, 0, 1, true), new Tile(spritesBienio, 0, 2, true), new Tile(spritesBienio, 0, 3, true), new Tile(spritesBienio, 0, 4, true), new Tile(spritesBienio, 0, 5, true), new Tile(spritesBienio, 1, 12, true), new Tile(spritesBienio, 1, 13, true), new Tile(spritesBienio, 2, 0, true), new Tile(spritesBienio, 0, 8, true), new Tile(spritesBienio, 0, 9, true), new Tile(spritesBienio, 0,10, true),
                    new Tile(spritesBienio, 0, 11, true), new Tile(spritesBienio, 0, 12, true), new Tile(spritesBienio, 0, 13, true), new Tile(spritesBienio, 2, 7, true), new Tile(spritesBienio, 2, 8, true), new Tile(spritesBienio, 2, 9, true), new Tile(spritesBienio, 2, 1, true), new Tile(spritesBienio, 2, 2, true), new Tile(spritesBienio, 2, 3, true), new Tile(spritesBienio, 2, 4, true), new Tile(spritesBienio, 2, 5, true), new Tile(spritesBienio, 2, 6, true),
                    new Tile(spritesBienio, 1, 0, true), new Tile(spritesBienio, 1, 1, true), new Tile(spritesBienio, 1, 2, true), new Tile(spritesBienio, 1, 3, true), new Tile(spritesBienio, 1, 4, true), new Tile(spritesBienio, 1, 5, true), new Tile(spritesBienio, 1, 6, true), new Tile(spritesBienio, 1, 7, false), new Tile(spritesBienio, 1, 8, false), new Tile(spritesBienio, 1, 9, true), new Tile(spritesBienio, 1, 10, false), new Tile(spritesBienio, 1, 11, false)
            };
            MapLayer mesasMapLayer = new MapLayer(mesasTileMap, mesasTiles);
            mesasMapLayer.render(g);
            this.tm.addLayer(mesasMapLayer);

            // Cadeiras
            BufferedReader cadeirasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/cadeiras-tilemap.csv"));
            Tile[] cadeirasTiles = {new Tile(spritesBienio, 0, 6, true), new Tile(spritesBienio, 0, 7, true), new Tile(spritesBienio, 1, 9, true), new Tile(spritesBienio, 1, 10, true), new Tile(spritesBienio, 1, 11, true)};
            MapLayer cadeirasMapLayer = new MapLayer(cadeirasTileMap, cadeirasTiles);
            cadeirasMapLayer.render(g);
            this.tm.addLayer(cadeirasMapLayer);

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

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }
}
