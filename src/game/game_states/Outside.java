package game.game_states;

import game.Game;
import game.animation.SpriteSheet;
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
    private final Npc npc;
    private final GameStateManager gameStateManager;
    private BufferedImage backgroundImage;
    private final ArrayList<String> dialogues = new ArrayList<>();
    private final KeyHandler keyHandler;

    public Outside(GameStateManager gameStateManager, Player player, KeyHandler keyHandler) {
        this.gameStateManager = gameStateManager;
        this.player = player;
        this.player.setTileManager(tm);
        this.keyHandler = keyHandler;
        int npcPosX = 13 * Game.tileSize;
        int npcPosY = 27 * Game.tileSize;
        Rectangle npcWalkArea = new Rectangle(9*Game.tileSize, 4*Game.tileSize);
        Dialogue dialogue = new Dialogue(dialogues, keyHandler, new Font("arial", Font.PLAIN, 20));
        loadMapLayers();
        setDialogues();
        this.npc = new Npc(npcPosX, npcPosY, tm, 2, npcWalkArea, dialogue);
    }

    @Override
    public void tick() {
        player.tick();
        npc.tick();
        this.player.setColliding(this.tm.colides(player) || npc.isDialogueActivated());
        this.tm.interacts();
        if (player.getWorldRow() == npc.getWorldRow() && player.getWorldCol() == npc.getWorldCol()) {
            if (keyHandler.enterPressed) {
                npc.setDialogueActivated(true);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.backgroundImage, -tm.getReferenceX(), -tm.getReferenceY(), null);
        this.tm.renderRange(0, 3, g);
        player.render(g);
        npc.render(g);
        this.tm.renderRange(4, g);
        npc.renderDialogue(g);
    }
    private void setDialogues(){

        dialogues.add("Olá, Aventureiro !");
        dialogues.add("Bem vindo a USP !");
        dialogues.add("Serei seu guia inicial nessa nova jornada que o aguarda");
        dialogues.add("Espero que aproveite seu tempo aqui, faça amigos e se divirta");
        dialogues.add("Podemos começar ?");
    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {
        this.player.setTileManager(tm);
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

    @Override
    public IStateManager getStateManager() {
        return gameStateManager;
    }
}
