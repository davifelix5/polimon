package game.game_states;

import game.entity.Player;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.map.interactions.BienioEnterStrategy;
import game.map.interactions.SwimStrategy;
import game.state.IState;
import game.state.IStateManager;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Outside implements IState {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private final GameStateManager gameStateManager;
    private BufferedImage backgroundImage;

    private MapFactory factory;

    public Outside(GameStateManager gameStateManager, Player player) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.player.setTileManager(tm);
    }

    public void setFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public void tick() {
        player.tick();
        this.player.setColliding(this.tm.colides(player));
        this.tm.interacts();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundImage, -tm.getReferenceX(), - tm.getReferenceY(), null);
        this.tm.renderRange(0, 3, g);
        this.tm.renderRange(4, g);
        player.render(g);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {
        this.player.setTileManager(tm);
        this.player.loadAnimations();
        loadMapLayers();
    }

    private void loadMapLayers() {

        // Chão
        this.backgroundImage = factory.getBackgroundImage();

        // Tilemaps e layers
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", factory.getMapTileSet(), new BienioEnterStrategy(gameStateManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_base_do_poste.csv", factory.getMapTileSet(),true));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_nao_solido.csv", factory.getMapTileSet(), false));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_agua.csv", factory.getMapTileSet(), new SwimStrategy(), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_solido.csv", factory.getMapTileSet(), true));
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
