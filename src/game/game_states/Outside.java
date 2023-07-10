package game.game_states;

import game.Game;
import game.animation.SpriteSheet;
import game.entity.Player;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
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

    public Outside(GameStateManager gameStateManager, Player player) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.player.setTileManager(tm);
        loadMapLayers();
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
        player.render(g);
        this.tm.renderRange(4, g);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {
        this.player.setTileManager(tm);
    }

    private void loadMapLayers() {
        // Chão
        this.backgroundImage = Game.mapFactory.getBackgroundImage();

        // Tilemaps e layers
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_agua.csv", Game.mapFactory.getMapTileSet(), new SwimStrategy(), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_solido.csv", Game.mapFactory.getMapTileSet(), true));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", Game.mapFactory.getMapTileSet(), new BienioEnterStrategy(gameStateManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_base_do_poste.csv", Game.mapFactory.getMapTileSet(),true));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_nao_solido.csv", Game.mapFactory.getMapTileSet(), false));
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
