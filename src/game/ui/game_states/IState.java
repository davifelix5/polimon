package game.ui.game_states;

import game.entity.npc.NPCStrategy;

import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;

import java.awt.*;

/**
 * Estado de jogo, empacota a lógica de um estágio completo do jogo
 */
public interface IState {
    void tick();
    void render(Graphics g);
    void destroy();
    void start();
    void setNPCStrategy(NPCStrategy strategy);
    void setMapPokemonStrategy(MapPokemonStrategy strategy);
    IStateManager getStateManager();
    void setFactory(MapFactory factory);
}
