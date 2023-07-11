package game.game_states;

import game.Game;
import game.entity.NPCStrategy;
import game.entity.Player;
import game.handlers.KeyHandler;
import game.map.MapLayer;
import game.map.PlayerInteractableLayer;
import game.map.TileManager;
import game.map.factory.MapFactory;
import game.map.interactions.BienioEnterStrategy;
import game.map.interactions.SwimStrategy;
import game.npc.Dialogue;
import game.npc.Npc;
import game.state.IState;
import game.state.IStateManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


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
        this.loaded = true;
        this.clearNpcs();
        this.tm.clearLayers();
        this.player.setTileManager(tm);
        this.player.loadAnimations();
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

    private void loadAnimations() {
        // Background
        this.backgroundImage = factory.getBackgroundImage();
        // Tilemaps e layers
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_agua.csv", factory.getMapTileSet(), new SwimStrategy(), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_solido.csv", factory.getMapTileSet(), true));
        this.tm.addLayer(new PlayerInteractableLayer("src/game/res/mapas/raia_portas.csv", factory.getMapTileSet(), new BienioEnterStrategy(gameStateManager), player));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_base_do_poste.csv", factory.getMapTileSet(),true));
        this.tm.addLayer(new MapLayer("src/game/res/mapas/raia_nao_solido.csv", factory.getMapTileSet(), false));
    }

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
