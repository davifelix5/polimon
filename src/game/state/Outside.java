package game.state;

import game.Game;
import game.animation.SpriteSheet;
import game.entity.Player;
import game.entity.PlayerAnimations;
import game.handlers.KeyHandler;
import game.map.LayerType;
import game.map.MapLayer;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Outside implements IState {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private final StateManager gameStateManager;
    private BufferedImage backgroundImage;

    public Outside(KeyHandler keyHandler, StateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.player = new Player(25* Game.tileSize, 10*Game.tileSize, 2, keyHandler, PlayerAnimations.Walk, tm);
        loadMapLayers();
    }

    @Override
    public void tick() {
        player.tick();
        this.player.setColiding(this.tm.colides(player));
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundImage, -tm.getReferenceX(), - tm.getReferenceY(), null);
        this.tm.renderRange(0, 8, g);
        player.render(g);
        this.tm.renderRange(9, 10, g);

    }

    private void loadMapLayers() {
        try {
            String spritesBasePath = "src/game/res/sprites/";
            String tilemapBasePath = "src/game/res/mapas/";

            // Spritsheets
            SpriteSheet city2Spritesheet = new SpriteSheet(spritesBasePath+"cityTileSet2.png", 32, 32);
            SpriteSheet city3Spritesheet = new SpriteSheet(spritesBasePath+"cityTIleSet3.png", 32, 32);
            SpriteSheet city5Spritesheet = new SpriteSheet(spritesBasePath+"cityTileSet5.png", 32, 32);
            SpriteSheet houseSpritesheet = new SpriteSheet(spritesBasePath+"houseTileSet.png", 32, 32);
            SpriteSheet pokemonLikeSpritesheet = new SpriteSheet(spritesBasePath+"PokemonLike.png", 32, 32);
            SpriteSheet ship1Spritesheet = new SpriteSheet(spritesBasePath+"shipTileSet.jpg", 32, 32);
            SpriteSheet carSpritesheet = new SpriteSheet(spritesBasePath+"carTileSet.png", 32, 32);
            SpriteSheet treeTileset = new SpriteSheet(spritesBasePath+"treeTileSet.png", 32, 32);

            // Chão
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/mapas/MapaRaiaChao.png"));

            // Tilemaps e layers
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Agua.csv", pokemonLikeSpritesheet, false, LayerType.SWIMABLE));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_troncos.csv", treeTileset, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Barcos.csv", ship1Spritesheet, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Muro.csv", city2Spritesheet, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Predios.csv", city5Spritesheet, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Carros.csv", carSpritesheet, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Crusp.csv", houseSpritesheet, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_Portas.csv", city5Spritesheet, true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_BasePoste.csv", city3Spritesheet,true));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_folhas.csv", treeTileset));
            this.tm.addLayer(new MapLayer(tilemapBasePath+"Mapa Raia_topoPoste.csv", city3Spritesheet));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
