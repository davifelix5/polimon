package game.ui.game_states;

import game.entity.npc.NPCStrategy;
import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;

public class GameStateManager implements IStateManager {
    private final IState[] states = new IState[GameState.values().length];
    private GameState currentState;

    @Override
    public IState getCurrentState() {
        return states[currentState.getValue()];
    }

    /**
     * Mud ao estado atual do jogo, iniciando o próximo estado e encerrando o antigo
     * @param id estado a ser iniciado
     */
    @Override
    public void setState(GameState id) {
        if (currentState != id) {
            if (this.currentState != null) {
                this.getCurrentState().destroy();
            }
            this.currentState = id;
            this.getCurrentState().start();
        }
    }

    /**
     * Percorre todos os estados do jogo e muda a fábrica abstrata usada por eles
     * @param factory nova fábrica a ser utilizada pelos estados
     */
    @Override
    public void setFactory(MapFactory factory) {
        for (IState s: states) {
            if (s!=null) {
                s.setFactory(factory);
            }
        }
    }

    @Override
    public void addState(GameState id, IState state) {
        this.states[id.getValue()] = state;
    }

    /**
     * Percorre todos os estados e muda a estratégia para movimentação do NPC usada por eles
     * @param strategy nova estratégia para movimentação do NPC
     */
    @Override
    public void setNPCStrategy(NPCStrategy strategy) {
        for (IState s: states) {
            if (s!=null) {
                s.setNPCStrategy(strategy);
            }
        }
    }

    /**
     * Percorre todos os estados e muda a estratégia para movimentação dos Pokemons usada por eles
     * @param strategy nova estratégia para movimentação dos Pokemons
     */
    @Override
    public void setMapPokemonStrategy(MapPokemonStrategy strategy) {
        for (IState s: states) {
            if (s != null) {
                s.setMapPokemonStrategy(strategy);
            }
        }
    }
}
