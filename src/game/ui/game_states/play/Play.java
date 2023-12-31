package game.ui.game_states.play;

import game.Game;
import game.entity.npc.NPCStrategy;
import game.entity.npc.Npc;
import game.entity.player.Player;
import game.entity.pokemon.Pokemon;
import game.entity.pokemon.MapPokemonStrategy;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.ui.game_states.GameStateManager;
import game.ui.game_states.IState;
import game.ui.game_states.IStateManager;
import game.ui.handlers.KeyHandler;
import game.ui.handlers.MouseHandler;

import java.awt.*;
import java.util.ArrayList;


public class Play implements IState, ScreenManager {
    private final Player player;
    private final GameStateManager gameStateManager;
    private NPCStrategy npcStrategy;
    private final ArrayList<Npc> npcs = new ArrayList<>();
    private MapFactory factory;
    private final ArrayList<Pokemon> pokemons = new ArrayList<>();
    private final GameScreen[] screens = new GameScreen[3];
    private int currentScreenIndex;

    public Play(Game game, GameStateManager gameStateManager, Player player, KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.currentScreenIndex = 0;
        screens[0] = new Outside(player, keyHandler, npcs, pokemons, this);
        screens[1] = new Bienio(player, this);
        screens[2] = new CombatScreen(game, player, mouseHandler, this, pokemons);
    }

    @Override
    public void tick() {
        this.player.setTileManager(screens[currentScreenIndex].getTileManager());
        screens[currentScreenIndex].tick();
    }

    @Override
    public void render(Graphics g) {
        screens[currentScreenIndex].render(g);
    }

    /**
     * Encerra o estado, apagando as camadas do mapa de todas a telas para elas poderem ser geradas novamente
     */
    @Override
    public void destroy() {
        for (GameScreen screen: screens) {
            TileManager tm = screen.getTileManager();
            if (tm != null)
                screen.getTileManager().clearLayers();
        }
    }

    /**
     * Inicia o estado tocando a música, carregando os sprites da tela atual e atualizando os sprites dos NPCs
     */
    @Override
    public void start() {
        this.player.loadAnimations();
        screens[currentScreenIndex].loadAnimations();
        screens[currentScreenIndex].startMusic();

        for (Npc npc: npcs) {
            npc.setSpritesheet(factory.getNpcSpritesheet());
        }

        this.updateNpcStrategy();
    }

    @Override
    public void setNPCStrategy(NPCStrategy strategy) {
        npcStrategy = strategy;
        this.updateNpcStrategy();
    }

    private void updateNpcStrategy() {
        for (Npc npc: npcs) {
            npc.setStrategy(this.npcStrategy.copy());
        }
    }

    @Override
    public void setMapPokemonStrategy(MapPokemonStrategy strategy) {
        this.updateMapPokemonStrategy(strategy.copy());
        for (GameScreen screen: screens)
            screen.setPokemonStrategy(strategy);
    }

    public void updateMapPokemonStrategy(MapPokemonStrategy strategy) {
        for (Pokemon pokemon : this.pokemons) {
            pokemon.setStrategy(strategy.copy());
        }
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }

    public void setFactory(MapFactory factory) {
        this.factory = factory;
        for (GameScreen screen: screens) {
            screen.setMapFactory(factory);
        }
    }

    /**
     * Muda a tela que atual do jogo conforme o índice do vetor de telas, encerrando a tela atual e inicial a nova tela
     * @param currentScreenIndex índice no vetor de telas da tela a ser inciada
     */
    public void setCurrentScreenIndex(int currentScreenIndex) {
        if (currentScreenIndex != this.currentScreenIndex) {
            player.stopPlayerSoundEffects();
            screens[this.currentScreenIndex].stopMusic();
            TileManager tm = screens[currentScreenIndex].getTileManager();
            if (tm != null)
                tm.clearLayers();
            this.currentScreenIndex = currentScreenIndex;
            this.start();
        }

    }

    /**
     * Obter a tela de combate para atualizar o pokemon a ser combatido
     * @return tela de combate
     */
    @Override
    public GameScreen getBattleScreen() {
        return this.screens[2];
    }
}
