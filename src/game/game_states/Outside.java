package game.game_states;

import game.Game;
import game.entity.NPCStrategy;
import game.entity.Player;
import game.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.PokemonLayer;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.map.interactions.BienioEnterStrategy;
import game.map.interactions.SwimStrategy;
import game.npc.Dialogue;
import game.npc.Npc;
import game.pokemon.PokemonType;
import game.state.IState;
import game.state.IStateManager;
import game.pokemon.MapPokemon;
import game.pokemon.PokemonGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.time.*;


public class Outside implements IState {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private final GameStateManager gameStateManager;
    private BufferedImage backgroundImage;
    private final KeyHandler keyHandler;
    private boolean loaded = false;

    private NPCStrategy npcStrategy;

    private final ArrayList<Npc> npcs = new ArrayList<>();
    private MapFactory factory;

    private ArrayList<MapPokemon> pokemons = new ArrayList<>();
    private MapPokemon foundPokemon;
    private Instant oldTime, newTime;

    public Outside(GameStateManager gameStateManager, Player player, KeyHandler keyHandler) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.player.setTileManager(tm);
        this.keyHandler = keyHandler;
    }

    public void setFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public void tick() {
        if (!loaded)
            return;
            
        this.newTime = Instant.now();
        if (this.oldTime == null) this.oldTime = this.newTime;
        long elapsedTime = Duration.between(oldTime, newTime).toMillis();
        if (elapsedTime >= 30000) {
            this.clearPokemons();
            this.generatePokemons();
            this.oldTime = this.newTime;
        }
            
        player.tick();
        for (MapPokemon pokemon : this.pokemons) {
            pokemon.tick();
        }

        foundPokemon = this.findPokemonWithinPlayer();
        if (foundPokemon != null) {
            System.out.println("O player achou um " + foundPokemon.getName() + "!");
        }

        boolean hasDialogue = false;
        for (Npc npc: npcs) {
            if (npc.isDialogueActivated()) {
                hasDialogue = true;
            }
            npc.tick();
        }

        this.player.setColliding(this.tm.colides(player) || hasDialogue);
        this.tm.interacts();
        for (Npc npc: npcs) {
            if (player.getWorldRow() == npc.getWorldRow() && player.getWorldCol() == npc.getWorldCol()) {
                if (keyHandler.enterPressed) {
                    npc.setDialogueActivated(true);
                }
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundImage, -tm.getReferenceX(), -tm.getReferenceY(), null);
        this.tm.renderRange(0, 3, g);
        player.render(g);

        for (Npc npc: npcs)
            npc.render(g);

        this.tm.renderRange(4, g);

        for (Npc npc: npcs)
            npc.renderDialogue(g);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {
        this.loaded = true;
        this.clearNpcs();
        this.clearPokemons();
        this.tm.clearLayers();
        this.player.setTileManager(tm);
        this.player.loadAnimations();
        this.loadAnimations();

        this.oldTime = Instant.now();

        this.generatePokemons();


        Font dialogueFont = new Font("arial", Font.PLAIN, 20);

        String[] dialogues1 = {
                "Olá, Aventureiro !",
                "Bem vindo a USP !",
                "Serei seu guia inicial nessa nova jornada que o aguarda",
                "Espero que aproveite seu tempo aqui, faça amigos e se divirta",
                "Podemos começar ?"
        };

        String[] dialogues2 = {
                "Olá, caro jogador!",
                "Bem vindo à Poli !",
                "Aqui a situação é tensa, mas no fim todos falam que vale a pena",
                "Será?!",
                "Fique por aqui e descubra!"
        };

        this.npcs.add(
                new Npc(
                        13 * Game.tileSize, 27 * Game.tileSize, tm, 2,
                        new Rectangle(9*Game.tileSize, 4*Game.tileSize),
                        new Dialogue(dialogues1, keyHandler, dialogueFont),
                        factory.getNpcSpritesheet()
                )
        );

        this.npcs.add(
                new Npc(
                        34 * Game.tileSize, 50 * Game.tileSize, tm, 2,
                        new Rectangle(10*Game.tileSize, 4*Game.tileSize),
                        new Dialogue(dialogues2, keyHandler, dialogueFont),
                        factory.getNpcSpritesheet()
                )
        );

        this.updateNpcStrategy();
    }

    @Override
    public void setNPCStrategy(NPCStrategy strategy) {
        npcStrategy = strategy;
        this.updateNpcStrategy();
    }

    private void clearNpcs() {
        npcs.clear();
    }

    private void updateNpcStrategy() {
        for (Npc npc: npcs) {
            npc.setStrategy(this.npcStrategy.copy());
        }
    }

    public void addPokemon(MapPokemon pokemon) {
        this.pokemons.add(pokemon);
    }

    public void clearPokemons() {
        this.pokemons.clear();
    }

    public void generatePokemons() {
        for (MapLayer layer : this.tm.getLayers()) {
            if (layer.isPokemonLayer()) {
                PokemonGenerator generator = PokemonGenerator.getInstance();
                MapPokemon newPokemon = generator.generatePokemon(layer.getPokemonType());
                if (newPokemon != null) {
                    this.addPokemon(newPokemon);
                }
            }
        }
    }

    public MapPokemon findPokemonWithinPlayer() {
        for (MapPokemon pokemon : pokemons) {
            if (player.getWorldRow() == pokemon.getWorldRow() && player.getWorldCol() == pokemon.getWorldCol()) {
                return pokemon;
            }
        }
        return null;
    }

    private void loadAnimations() {
        // Background
        this.backgroundImage = factory.getBackgroundImage();
        // Tilemaps e layers
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_agua.csv", factory.getMapTileSet(), new SwimStrategy(), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_solido.csv", factory.getMapTileSet(), true));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", factory.getMapTileSet(), new BienioEnterStrategy(gameStateManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_base_do_poste.csv", factory.getMapTileSet(),true));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_nao_solido.csv", factory.getMapTileSet(), false));
        this.tm.addLayer(new PokemonLayer("src/game/res/mapas/raia_pokemon_normal.csv", factory.getMapTileSet(), PokemonType.Normal));

    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
