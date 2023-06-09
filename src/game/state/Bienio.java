package game.state;

import game.Game;
import game.animation.SpriteSheet;
import game.entity.Player;
import game.entity.PlayerAnimations;
import game.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Bienio implements IState {
    KeyHandler keyHandler;
    Player player;
    IStateManager stateManager;
    private final TileManager tm = new TileManager(20, 30);
    private BufferedImage background;

    public Bienio(KeyHandler keyHandler, IStateManager stateManager) {
        this.keyHandler = keyHandler;
        this.stateManager = stateManager;
        loadMapLayers();
        this.player = new Player(70, 50, 2, keyHandler, PlayerAnimations.Walk);
    }

    public void tick() {
        player.tick();
        player.setColiding(tm.colides(player));
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null); // Map background
        this.tm.renderRange(0, 2, g);
        player.render(g); // Player
        this.tm.renderLayer(3,g ); // Plantas
    }

    private void loadMapLayers() {
        try {
            // Sprites
            BufferedImage tileset1Image = ImageIO.read(new FileInputStream("src/game/res/sprites/tileSet4.png"));
            BufferedImage tileset2Image = ImageIO.read(new FileInputStream("src/game/res/sprites/tileSet5.png"));
            SpriteSheet tileset1 = new SpriteSheet(tileset1Image, Game.tileSize, Game.tileSize);
            SpriteSheet tileset2 = new SpriteSheet(tileset2Image, Game.tileSize, Game.tileSize);
            this.background = ImageIO.read(new FileInputStream("src/game/res/mapas/bienio1-chao.png"));

            // Tilemaps
            BufferedReader mesasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/mesas.csv"));
            BufferedReader bancosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/bancos.csv"));
            BufferedReader cadeirasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/cadeiras.csv"));
            BufferedReader plantasTilemap = new BufferedReader(new FileReader("src/game/res/mapas/plantas.csv"));

            // Layers
            this.tm.addLayer(new MapLayer(mesasTileMap, tileset1, true));
            this.tm.addLayer(new MapLayer(bancosTileMap, tileset2, true));
            this.tm.addLayer(new MapLayer(cadeirasTileMap, tileset1, false));
            this.tm.addLayer(new MapLayer(plantasTilemap, tileset1, false));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }
}
