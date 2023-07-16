package game.ui.game_states.play;

import game.Game;
import game.entity.Entity;
import game.entity.player.Player;
import game.entity.pokemon.MapPokemonStrategy;
import game.map.*;
import game.map.interactions.BienioEnterStrategy;
import game.ui.handlers.KeyHandler;
import game.map.factory.MapFactory;
import game.map.interactions.SwimStrategy;
import game.entity.npc.Dialogue;
import game.entity.npc.Npc;
import game.entity.pokemon.PokemonType;
import game.entity.pokemon.Pokemon;
import game.entity.pokemon.PokemonGenerator;
import game.ui.sounds.Sound;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Outside implements GameScreen {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private BufferedImage backgroundImage;
    private final KeyHandler keyHandler;
    private MapFactory factory;
    private final ArrayList<Npc> npcs;
    private final ArrayList<Pokemon> pokemons;
    private final ScreenManager screenManager;
    private final PokemonGenerator pokeGenerator;

    public final Sound music = new Sound("src/game/res/sound/outside.wav");

    public Outside(Player player, KeyHandler keyHandler, ArrayList<Npc> npcs, ArrayList<Pokemon> pokemons, ScreenManager screenManager) {
        this.pokeGenerator = new PokemonGenerator(pokemons, 20);
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
        String[] dialogues3 = {
                "Olá, caro jogador!",
                "Eu sei que é muito cansativo capturar pokémons.",
                "Mas desistir nunca é uma opção",
                "Tome aqui mais uma força para continuar"
        };

        this.npcs.add(
                new Npc(
                        new Rectangle(13 * Game.tileSize,27 * Game.tileSize, 9*Game.tileSize, 4*Game.tileSize),
                         tm, 2,
                        new Dialogue(dialogues1, dialogueFont),
                        keyHandler,
                        this.player::addPokeball
                )
        );
        this.npcs.add(
                new Npc(
                        new Rectangle(34 * Game.tileSize, 50 * Game.tileSize, 10*Game.tileSize, 4*Game.tileSize),
                        tm, 2,
                        new Dialogue(dialogues2, dialogueFont),
                        keyHandler,
                        this.player::addPokeball
                )
        );
        this.npcs.add(
                new Npc(
                        new Rectangle(61 * Game.tileSize, 42 * Game.tileSize, 7*Game.tileSize, 5*Game.tileSize),
                        tm, 2,
                        new Dialogue(dialogues3, dialogueFont),
                        keyHandler,
                        this.player::cure
                )
        );

        this.pokeGenerator.addArea(
                new PokemonArea(
                        new PokemonType[]{PokemonType.NORMAL, PokemonType.GRASS, PokemonType.FAIRY},
                        new Rectangle(
                            34*Game.tileSize, 27*Game.tileSize,
                            9*Game.tileSize, 5*Game.tileSize
                        ),
                        5
                )
        );

        // Metal
        this.pokeGenerator.addArea(
                new PokemonArea(
                        new PokemonType[]{PokemonType.STEEL, PokemonType.ROCK, PokemonType.POISON},
                        new Rectangle(
                            4*Game.tileSize, 43*Game.tileSize,
                            6*Game.tileSize, 4*Game.tileSize
                        ),
                        3
                )
        );

        // Agua
        this.pokeGenerator.addArea(
                new PokemonArea(
                        new PokemonType[]{PokemonType.WATER},
                        new Rectangle(
                            18*Game.tileSize, 2*Game.tileSize,
                            15*Game.tileSize, 5*Game.tileSize
                        ),
                        10
                )
        );

    }

    public void setFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public void tick() {
        // Gerando pokemons no mapa
        this.pokeGenerator.generate(tm);

        // Ticking game objects
        player.tick();
        this.pokemons.forEach(Pokemon::tick);
        this.npcs.forEach(Npc::tick);

        // Lidando com interações e colisões nas camadas
        player.setColliding(this.tm.colides(player) || npcs.stream().anyMatch(Npc::isDialogueActivated));
        this.tm.interacts();

        // Colisão com pokemon
        Pokemon foundPokemon = this.findPokemonWithinPlayer();
        if (foundPokemon != null & keyHandler.enterPressed) {
            ((CombatScreen) screenManager.getBattleScreen()).setEnemyPokemon(foundPokemon);
            screenManager.setCurrentScreenIndex(2);
            System.out.println("Você achou um " + foundPokemon.getName() + "!");
        }

        // Colisão e diálogo com npcs
        for (Npc npc: npcs) {
            if (this.intersets(player, npc)) {
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

        for (Pokemon poke: pokemons) {
            poke.render(g);
        }

        this.tm.renderRange(4, g);

        player.renderPokeballAmount(g);

        // Diálogos renderizados depois para aperecerem primeiro
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
    }

    @Override
    public void setMapFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public void setPokemonStrategy(MapPokemonStrategy strategy) {
        this.pokeGenerator.setStrategy(strategy);
    }

    @Override
    public TileManager getTileManager() {
        return tm;
    }

    @Override
    public void startMusic() {
        music.play();
        music.loop();
    }

    @Override
    public void stopMusic() {
        music.stop();
    }

    /**
     * Verifica se algum pokemon na tela estão em colisão com o player
     * @return pokemons que está em contato com o player
     */
    public Pokemon findPokemonWithinPlayer() {
        for (Pokemon pokemon : pokemons) {
            if (this.intersets(player, pokemon)) {
                return pokemon;
            }
        }
        return null;
    }

    /**
     * Verifica se uma entidade está colidindo com outra
     * @param entity1 primeira entidade
     * @param entity2 segunda entidade
     * @return indica se há ou não colisão entre a primeira e a segunda entidade
     */
    public boolean intersets(Entity entity1, Entity entity2) {
        return tm.getReferencedBounds(entity1).intersects(tm.getReferencedBounds(entity2));
    }

}
