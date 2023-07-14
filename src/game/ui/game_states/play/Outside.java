package game.ui.game_states.play;

import game.Game;
import game.entity.player.Player;
import game.map.interactions.BienioEnterStrategy;
import game.ui.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.PokemonLayer;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.map.interactions.SwimStrategy;
import game.entity.npc.Dialogue;
import game.entity.npc.Npc;
import game.entity.pokemon.PokemonType;
import game.entity.pokemon.MapPokemon;
import game.entity.pokemon.PokemonGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.time.*;


public class Outside implements GameScreen {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private BufferedImage backgroundImage;
    private final KeyHandler keyHandler;
    private MapFactory factory;
    private Instant oldTime;
    private final ArrayList<Npc> npcs;
    private final ArrayList<MapPokemon> pokemons;
    private final ScreenManager screenManager;

    public Outside(Player player, KeyHandler keyHandler, ArrayList<Npc> npcs, ArrayList<MapPokemon> pokemons, ScreenManager screenManager) {
        this.player = player;
        this.player.setTileManager(tm);
        this.keyHandler = keyHandler;
        this.pokemons = pokemons;
        this.npcs = npcs;
        this.screenManager = screenManager;

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
                        new Dialogue(dialogues1, keyHandler, dialogueFont)
                )
        );
        this.npcs.add(
                new Npc(
                        34 * Game.tileSize, 50 * Game.tileSize, tm, 2,
                        new Rectangle(10*Game.tileSize, 4*Game.tileSize),
                        new Dialogue(dialogues2, keyHandler, dialogueFont)
                )
        );
    }

    public void setFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public void tick() {
        Instant newTime = Instant.now();
        if (this.oldTime == null) this.oldTime = newTime;
        long elapsedTime = Duration.between(oldTime, newTime).toMillis();
        if (elapsedTime >= 20000) {
            this.clearPokemons();
            this.generatePokemons();
            this.oldTime = newTime;
        }

        player.tick();
        this.pokemons.forEach(MapPokemon::tick);
        this.npcs.forEach(Npc::tick);

        player.setColliding(this.tm.colides(player) || npcs.stream().anyMatch(Npc::isDialogueActivated));
        this.tm.interacts();

        MapPokemon foundPokemon = this.findPokemonWithinPlayer();
        if (foundPokemon != null) {
            System.out.println("Você achou um " + foundPokemon.getName() + "!");
        }

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
    public void loadAnimations() {
        // Background
        this.backgroundImage = factory.getBackgroundImage();
        // Tilemaps e layers
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_agua.csv", factory.getMapTileSet(), new SwimStrategy(), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_solido.csv", factory.getMapTileSet(), true));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", factory.getMapTileSet(), new BienioEnterStrategy(screenManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_base_do_poste.csv", factory.getMapTileSet(),true));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_nao_solido.csv", factory.getMapTileSet(), false));
        this.tm.addLayer(new PokemonLayer("src/game/res/mapas/raia_pokemon_normal.csv", factory.getMapTileSet(), PokemonType.Normal));
        this.tm.addLayer(new PokemonLayer("src/game/res/mapas/raia_pokemon_agua.csv", factory.getMapTileSet(), PokemonType.Water));
        this.tm.addLayer(new PokemonLayer("src/game/res/mapas/raia_pokemon_metal.csv", factory.getMapTileSet(), PokemonType.Steel));
    }

    @Override
    public void setMapFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public TileManager getTileManager() {
        return tm;
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

}
