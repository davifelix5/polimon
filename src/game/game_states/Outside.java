package game.game_states;

import game.Game;
import game.animation.SpriteSheet;
import game.entity.NPCStrategy;
import game.entity.Player;
import game.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.interactions.BienioEnterStrategy;
import game.map.interactions.SwimStrategy;
import game.npc.Dialogue;
import game.npc.Npc;
import game.state.IState;
import game.state.IStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Outside implements IState {

    private final TileManager tm = new TileManager(60, 70);
    private final Player player;
    private final GameStateManager gameStateManager;
    private BufferedImage backgroundImage;
    private final KeyHandler keyHandler;
    private SpriteSheet npcSpriteSheet;

    private final ArrayList<Npc> npcs = new ArrayList<>();

    public Outside(GameStateManager gameStateManager, Player player, KeyHandler keyHandler) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.player.setTileManager(tm);
        this.keyHandler = keyHandler;
        this.loadAnimations();
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
                npcSpriteSheet
            )
        );

        this.npcs.add(
                new Npc(
                        34 * Game.tileSize, 50 * Game.tileSize, tm, 2,
                        new Rectangle(10*Game.tileSize, 4*Game.tileSize),
                        new Dialogue(dialogues2, keyHandler, dialogueFont),
                        npcSpriteSheet
                )
        );

        loadMapLayers();
    }

    @Override
    public void tick() {
        player.tick();

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
        this.player.setTileManager(tm);
    }

    @Override
    public void setNPCStrategy(NPCStrategy strategy) {
        for (Npc npc: npcs) {
            npc.setStrategy(strategy.copy());
        }
    }

    private void loadMapLayers() {
        try {
            // Spritsheets
            SpriteSheet mapSritesheet = new SpriteSheet("src/game/res/sprites/tileset_mapa.png", 32, 32);

            // Chão
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/mapas/MapaRaiaChao.png"));

            // Tilemaps e layers
            this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_agua.csv", mapSritesheet, new SwimStrategy(), player));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_solido.csv", mapSritesheet, true));
            this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", mapSritesheet, new BienioEnterStrategy(gameStateManager), player));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_base_do_poste.csv", mapSritesheet,true));
            this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_nao_solido.csv", mapSritesheet, false));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAnimations() {
        try {
            BufferedImage npcSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/NpcSprites.png"));
            npcSpriteSheet = new SpriteSheet(npcSprites, 51, 54);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
