package game.state;

import game.Game;
import game.animation.SpriteSheet;
import game.entity.Player;
import game.handlers.KeyHandler;
import game.map.MapLayer;
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
    private SpriteSheet tileset1, tileset2;
    private BufferedImage background;

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
            g.drawImage(background, 0, 0, null);

            // Mesas
            BufferedReader mesasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/mesas.csv"));
            MapLayer mesasMapLayer = new MapLayer(mesasTileMap, tileset1, true);
            mesasMapLayer.render(g);
            this.tm.addLayer(mesasMapLayer);

            // Bancos
            BufferedReader bancosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/bancos.csv"));
            MapLayer bancosMapLayer = new MapLayer(bancosTileMap, tileset2, true);
            bancosMapLayer.render(g);
            this.tm.addLayer(bancosMapLayer);

            // Cadeiras
            BufferedReader cadeirasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/cadeiras.csv"));
            MapLayer cadeirasMapLayer = new MapLayer(cadeirasTileMap, tileset1, false);
            cadeirasMapLayer.render(g);
            this.tm.addLayer(cadeirasMapLayer);

            // Player
            player.render(g);

            // Plantas
            BufferedReader plantasTilemap = new BufferedReader(new FileReader("src/game/res/mapas/plantas.csv"));
            MapLayer plantarMapLayer = new MapLayer(plantasTilemap, tileset1, false);
            plantarMapLayer.render(g);
            this.tm.addLayer(plantarMapLayer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readSpriteImages() {
        try {
            BufferedImage tileset1Image = ImageIO.read(new FileInputStream("src/game/res/sprites/tileSet4.png"));
            BufferedImage tileset2Image = ImageIO.read(new FileInputStream("src/game/res/sprites/tileSet5.png"));
            this.tileset1 = new SpriteSheet(tileset1Image, Game.tileSize, Game.tileSize);
            this.tileset2 = new SpriteSheet(tileset2Image, Game.tileSize, Game.tileSize);
            this.background = ImageIO.read(new FileInputStream("src/game/res/mapas/bienio1-chao.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }
}
