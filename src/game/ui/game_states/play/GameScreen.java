package game.ui.game_states.play;

import game.entity.pokemon.MapPokemonStrategy;
import game.map.TileManager;
import game.map.factory.MapFactory;

import java.awt.*;

/**
 * Parte do estado de Play, contempla os diferentes ambiente do jogo
 */
public interface GameScreen {
    void render(Graphics g);
    void tick();
    void loadAnimations();
    void setMapFactory(MapFactory factory);
    void setPokemonStrategy(MapPokemonStrategy strategy);
    TileManager getTileManager();
    void startMusic();
    void stopMusic();
}
