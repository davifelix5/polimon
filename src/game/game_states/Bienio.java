package game.game_states;

import game.animation.SpriteSheet;
import game.entity.NPCStrategy;
import game.entity.Player;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.map.interactions.BienioExitStrategy;
import game.state.IState;
import game.state.IStateManager;
import game.pokemon.MapPokemonStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bienio implements IState {
    private MapFactory factory;
    private final Player player;
    private final GameStateManager gameStateManager;
    private final TileManager tm = new TileManager(20, 30);
    private BufferedImage background;


    public Bienio(GameStateManager stateManager, Player player) {
        this.gameStateManager = stateManager;
        this.player = player;
    }

    public void tick() {
        player.tick();
        player.setColliding(tm.colides(player));
        this.tm.interacts();
    }

    public void render(Graphics g) {
        g.drawImage(background, 0, 0, null); // Map background
        this.tm.renderRange(0, 2, g);
        player.render(g); // Player
        this.tm.renderLayer(3,g ); // Plantas
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

    @Override
    public void setNPCStrategy(NPCStrategy strategy) {

    }

    @Override
    public void setMapPokemonStrategy(MapPokemonStrategy strategy) {

    }

    private void loadMapLayers() {
        // Sprites
        SpriteSheet mapSprites = factory.getMapTileSet();

        // Chao
        this.background = factory.getBienioBackground();

        // Layers
        this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_solidos.csv", mapSprites, true));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/bienio_tapete.csv", mapSprites, new BienioExitStrategy(gameStateManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_nao_solido_antes.csv", mapSprites, false));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/bienio_nao_solido.csv", mapSprites, false));

    }


    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }

    @Override
    public void setFactory(MapFactory factory) {
        this.factory = factory;
    }
}
