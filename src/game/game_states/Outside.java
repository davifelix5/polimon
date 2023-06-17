package game.game_states;

import game.animation.SpriteSheet;
import game.entity.Player;
import game.map.LayerType;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.interactions.BienioEnterStrategy;
import game.state.IState;
import game.state.IStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Outside implements IState {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private final GameStateManager gameStateManager;
    private BufferedImage backgroundImage;

    public Outside(GameStateManager gameStateManager, Player player) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.player.setTileManager(tm);
        loadMapLayers();
    }

    @Override
    public void tick() {
        this.player.setTileManager(tm);
        player.tick();
        this.player.setColliding(this.tm.colides(player));
        this.tm.interacts();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundImage, -tm.getReferenceX(), - tm.getReferenceY(), null);
        this.tm.renderRange(0, 7, g);
        player.render(g);
        this.tm.renderRange(8, g);

    }

    @Override
    public void destroy() {

    }

    private void loadMapLayers() {
        try {
            // Spritsheets
            SpriteSheet city2Spritesheet = new SpriteSheet("src/game/res/sprites/cityTileSet2.png", 32, 32);
            SpriteSheet city3Spritesheet = new SpriteSheet("src/game/res/sprites/cityTIleSet3.png", 32, 32);
            SpriteSheet city5Spritesheet = new SpriteSheet("src/game/res/sprites/cityTileSet5.png", 32, 32);
            SpriteSheet houseSpritesheet = new SpriteSheet("src/game/res/sprites/houseTileSet.png", 32, 32);
            SpriteSheet pokemonLikeSpritesheet = new SpriteSheet("src/game/res/sprites/PokemonLike.png", 32, 32);
            SpriteSheet ship1Spritesheet = new SpriteSheet("src/game/res/sprites/shipTileSet.jpg", 32, 32);
            SpriteSheet carSpritesheet = new SpriteSheet("src/game/res/sprites/carTileSet.png", 32, 32);
            SpriteSheet treeTileset = new SpriteSheet("src/game/res/sprites/treeTileSet.png", 32, 32);

            // Chão
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/mapas/MapaRaiaChao.png"));

            // Tilemaps e layers
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_agua.csv", pokemonLikeSpritesheet, false, LayerType.SWIMABLE));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_barco.csv", ship1Spritesheet, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_muro.csv", city2Spritesheet, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_predios.csv", city5Spritesheet, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_carro.csv", carSpritesheet, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_crusp.csv", houseSpritesheet, true));
            this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", city5Spritesheet, new BienioEnterStrategy(gameStateManager), player));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_poste.csv", city3Spritesheet,true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_carroN.csv", carSpritesheet, false));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_arvores.csv", treeTileset, true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_arvoresN.csv", treeTileset, false));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_prediosN.csv", city5Spritesheet, false));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_posteN.csv", city3Spritesheet, false));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_cruspN.csv", houseSpritesheet, false));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
