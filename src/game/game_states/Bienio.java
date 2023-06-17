package game.game_states;

import game.Game;
import game.animation.SpriteSheet;
import game.entity.Player;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.interactions.BienioExitStrategy;
import game.state.IState;
import game.state.IStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Bienio implements IState {
    Player player;
    GameStateManager gameStateManager;
    private final TileManager tm = new TileManager(20, 30);
    private BufferedImage background;

    public Bienio(GameStateManager stateManager, Player player) {
        this.gameStateManager = stateManager;
        this.player = player;
        loadMapLayers();
    }

    public void tick() {
        this.player.setTileManager(tm);
        player.tick();
        player.setColliding(tm.colides(player));
        this.tm.interacts();
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null); // Map background
        this.tm.renderRange(0, 4, g);
        player.render(g); // Player
        this.tm.renderLayer(5,g ); // Plantas
    }

    @Override
    public void destroy() {

    }

    private void loadMapLayers() {
        try {
            // Sprites
            SpriteSheet tileset4 = new SpriteSheet("src/game/res/sprites/tileSet4.png", Game.tileSize, Game.tileSize);
            SpriteSheet tileset5 = new SpriteSheet("src/game/res/sprites/tileSet5.png", Game.tileSize, Game.tileSize);
            SpriteSheet tileset2 = new SpriteSheet("src/game/res/sprites/tileSet2.png", Game.tileSize, Game.tileSize);
            this.background = ImageIO.read(new FileInputStream("src/game/res/mapas/bienio1-chao.png"));

            // Layers
            this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/bienio_tapete.csv", tileset2, new BienioExitStrategy(gameStateManager), player));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_inicio_tapete.csv", tileset2, false));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_mesas.csv", tileset4, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_bancos.csv", tileset5, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_cadeiras.csv", tileset4, false));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_plantas.csv", tileset4, false));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
