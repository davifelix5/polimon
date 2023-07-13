package game.state;

import game.entity.NPCStrategy;

import game.map.factory.MapFactory;
import game.pokemon.MapPokemonStrategy;

import java.awt.*;

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
