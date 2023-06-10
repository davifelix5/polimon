package game.state;

import game.animation.SpriteSheet;
import game.entity.Player;
import game.entity.PlayerAnimations;
import game.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;

public class Outside implements IState {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private final StateManager gameStateManager;
    private BufferedImage backgroundImage;

    public Outside(KeyHandler keyHandler, StateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        this.player = new Player(65, 50, 2, keyHandler, PlayerAnimations.Walk, tm);
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
            // Spritsheets
            BufferedImage city2 = ImageIO.read(new FileInputStream("src/game/res/sprites/cityTileSet2.png"));
            SpriteSheet city2Spritesheet = new SpriteSheet(city2, 32, 32);
            BufferedImage city3 = ImageIO.read(new FileInputStream("src/game/res/sprites/cityTileSet3.png"));
            SpriteSheet city3Spritesheet = new SpriteSheet(city3, 32, 32);
            BufferedImage city5 = ImageIO.read(new FileInputStream("src/game/res/sprites/cityTileSet5.png"));
            SpriteSheet city5Spritesheet = new SpriteSheet(city5, 32, 32);
            BufferedImage house = ImageIO.read(new FileInputStream("src/game/res/sprites/houseTileSet.png"));
            SpriteSheet houseSpritesheet = new SpriteSheet(house, 32, 32);
            BufferedImage pokemonLike = ImageIO.read(new FileInputStream("src/game/res/sprites/PokemonLike.png"));
            SpriteSheet pokemonLikeSpritesheet = new SpriteSheet(pokemonLike, 32, 32);
            BufferedImage ship1 = ImageIO.read(new FileInputStream("src/game/res/sprites/shipTileSet.jpg"));
            SpriteSheet ship1Spritesheet = new SpriteSheet(ship1, 32, 32);
            BufferedImage car = ImageIO.read(new FileInputStream("src/game/res/sprites/carTileset.png"));
            SpriteSheet carSpritesheet = new SpriteSheet(car, 32, 32);
            BufferedImage tree = ImageIO.read(new FileInputStream("src/game/res/sprites/treeTileSet.png"));
            SpriteSheet treeTileset = new SpriteSheet(tree, 32, 32);
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/mapas/MapaRaiaChao.png"));

            // Tilemaps
            BufferedReader aguaTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_Agua.csv"));
            BufferedReader barcosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_Barcos.csv"));
            BufferedReader troncosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_troncos.csv"));
            BufferedReader carrosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_Carros.csv"));
            BufferedReader cruspTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_Crusp.csv"));
            BufferedReader muroTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_Muro.csv"));
            BufferedReader portasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_Portas.csv"));
            BufferedReader prediosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_predios.csv"));
            BufferedReader folhasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_folhas.csv"));
            BufferedReader postesTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_topoPoste.csv"));
            BufferedReader basePostesTileMap = new BufferedReader(new FileReader("src/game/res/mapas/Mapa Raia_BasePoste.csv"));

            // Carregando layers
            this.tm.addLayer(new MapLayer(aguaTileMap, pokemonLikeSpritesheet, false));
            this.tm.addLayer(new MapLayer(troncosTileMap, treeTileset, true));
            this.tm.addLayer(new MapLayer(barcosTileMap, ship1Spritesheet, true));
            this.tm.addLayer(new MapLayer(muroTileMap, city2Spritesheet, true));
            this.tm.addLayer(new MapLayer(prediosTileMap, city5Spritesheet, true));
            this.tm.addLayer(new MapLayer(carrosTileMap, carSpritesheet, true));
            this.tm.addLayer(new MapLayer(cruspTileMap, houseSpritesheet, true));
            this.tm.addLayer(new MapLayer(portasTileMap, city5Spritesheet, true));
            this.tm.addLayer(new MapLayer(basePostesTileMap, city3Spritesheet,true));
            this.tm.addLayer(new MapLayer(folhasTileMap, treeTileset,false));
            this.tm.addLayer(new MapLayer(postesTileMap, city3Spritesheet, false));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
