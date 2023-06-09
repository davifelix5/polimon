package game.state;

import game.Game;
import game.animation.IAnimationSet;
import game.animation.MoveAnimationSet;
import game.animation.SpriteSheet;
import game.entity.Player;
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
    private final TileManager tm = new TileManager();
    private SpriteSheet tileset1, tileset2, playerSprites, bikeSprites;
    private IAnimationSet bikeAnimationSet, walkAnimationSet;
    private BufferedImage background;

    public Bienio(KeyHandler keyHandler, IStateManager stateManager) {
        this.keyHandler = keyHandler;
        this.stateManager = stateManager;
        readSpriteImages();
        createAnimationSets();
        loadMapLayers();
        this.player = new Player(70, 50, 2, walkAnimationSet,keyHandler);
    }

    public void tick() {
        if (keyHandler.bikeButtonPressed && player.getMoveAnimation() != bikeAnimationSet) {
            player.setMoveAnimation(bikeAnimationSet);
            player.setMovingRate(3);
        }
        else if (!keyHandler.bikeButtonPressed && player.getMoveAnimation() != walkAnimationSet) {
            player.setMoveAnimation(walkAnimationSet);
            player.setMovingRate(2);
        }
        player.tick();
        player.setColiding(tm.colides(player));
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null); // Map background
        this.tm.renderLayer(0, g); // Mesas
        this.tm.renderLayer(1, g); // Bancos
        this.tm.renderLayer(2, g); // Cadeiras
        player.render(g); // Player
        this.tm.renderLayer(3,g ); // Plantas
    }

    private void loadMapLayers() {
        try {

            // Mesas
            BufferedReader mesasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/mesas.csv"));
            MapLayer mesasMapLayer = new MapLayer(mesasTileMap, tileset1, true);
            this.tm.addLayer(mesasMapLayer);

            // Bancos
            BufferedReader bancosTileMap = new BufferedReader(new FileReader("src/game/res/mapas/bancos.csv"));
            MapLayer bancosMapLayer = new MapLayer(bancosTileMap, tileset2, true);
            this.tm.addLayer(bancosMapLayer);

            // Cadeiras
            BufferedReader cadeirasTileMap = new BufferedReader(new FileReader("src/game/res/mapas/cadeiras.csv"));
            MapLayer cadeirasMapLayer = new MapLayer(cadeirasTileMap, tileset1, false);
            this.tm.addLayer(cadeirasMapLayer);

            // Plantas
            BufferedReader plantasTilemap = new BufferedReader(new FileReader("src/game/res/mapas/plantas.csv"));
            MapLayer plantarMapLayer = new MapLayer(plantasTilemap, tileset1, false);
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
            BufferedImage playerImages = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            this.playerSprites = new SpriteSheet(playerImages, 32, 41);
            BufferedImage bikeSpritesImage = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
            this.bikeSprites = new SpriteSheet(bikeSpritesImage, 48, 48);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createAnimationSets() {
        this.walkAnimationSet = new MoveAnimationSet(playerSprites, 0);
        this.bikeAnimationSet = new MoveAnimationSet(bikeSprites, 0);
    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }
}
