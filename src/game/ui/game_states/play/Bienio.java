package game.ui.game_states.play;

import game.entity.animation.SpriteSheet;
import game.entity.player.Player;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.map.interactions.BienioExitStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bienio implements GameScreen {
    private MapFactory factory;
    private final Player player;
    private final ScreenManager screenManager;
    private final TileManager tm = new TileManager(20, 30);
    private BufferedImage background;


    public Bienio(Player player, ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.player = player;
    }

    public void tick() {
        player.tick();
        player.setColliding(tm.colides(player));
        this.tm.interacts();
    }

    @Override
    public void loadAnimations() {
        // Sprites
        SpriteSheet mapSprites = factory.getMapTileSet();

        // Chao
        this.background = factory.getBienioBackground();

        // Layers
        this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_solidos.csv", mapSprites, true));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/bienio_tapete.csv", mapSprites, new BienioExitStrategy(screenManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_nao_solido_antes.csv", mapSprites, false));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_nao_solido.csv", mapSprites, false));
    }

    @Override
    public void setMapFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public TileManager getTileManager() {
        return tm;
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null); // Map background
        this.tm.renderRange(0, 2, g);
        player.render(g); // Player
        this.tm.renderLayer(3,g ); // Plantas
    }

}
