package game;

import game.combate.Easy;
import game.combate.GameMode;
import game.entity.player.Player;
import game.entity.npc.WalkNPCStrategy;
import game.ui.game_states.*;
import game.ui.game_states.Menu;
import game.ui.game_states.play.Play;
import game.ui.handlers.KeyHandler;
import game.ui.handlers.MouseHandler;
import game.map.factory.ClassicMap;
import game.map.factory.MapFactory;
import game.map.factory.VintageMap;
import game.entity.pokemon.WalkPokemonStrategy;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Game extends JPanel implements Runnable {

    public static final int originalTileSize = 32, scale = 1, tileSize = scale * originalTileSize;
    public static final int maxScreenRow = 20, maxScreenCol = 30;
    public static final int width = tileSize*maxScreenCol, height = tileSize*maxScreenRow;
    final int FPS = 60;

    private GameMode gameMode;

    Thread thread;

    private final GameStateManager gameStateManager = new GameStateManager();
    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);
    Player player;

    public MapFactory mapFactory;
    public final Map<String, MapFactory> factoryMap = new HashMap<>();

    public Game() {
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setDoubleBuffered(true);
        this.player = new Player(30*Game.tileSize ,50*Game.tileSize, keyHandler);
        this.gameStateManager.addState(GameState.RestScreen, new RestScreen(keyHandler, gameStateManager));
        this.gameStateManager.addState(GameState.Menu, new Menu(this, gameStateManager, mouseHandler));
        this.gameStateManager.addState(GameState.Outside, new Play(this, gameStateManager, player, keyHandler, mouseHandler));
        this.gameStateManager.setState(GameState.RestScreen);
        this.gameStateManager.setNPCStrategy(new WalkNPCStrategy());
        this.gameStateManager.setMapPokemonStrategy(new WalkPokemonStrategy());
        this.gameMode = new Easy();

        factoryMap.put("Vintage", new VintageMap());
        factoryMap.put("Classic", new ClassicMap());

        this.setPreferredSize(new Dimension(width, height));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/FPS;
        boolean shouldRender;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        int drawCount = 0;
        int timer = 0;

        while (thread != null) {
            currentTime = System.nanoTime();
            shouldRender = false;

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            while (delta >= 1) {
                // Update information regarding the game state
                tick();
                // Since there has been updates, the game should render
                shouldRender = true;
                delta--;
            }

            if (shouldRender) {
                // Draw game.elements on the screen
                repaint();
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

        }
    }

    public void tick() {
        if (keyHandler.isEscPressed()) {
            gameStateManager.setState(GameState.Menu);
        } else {
            gameStateManager.getCurrentState().tick();
        }
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameStateManager.getCurrentState().render(g);
    }

    public static int clamp(int value, int min, int max) {
        if (value >= max)
            return max;
        else if (value <= min)
            return min;
        return value;
    }

    public void setMapFactory(String factoryName) {
        this.mapFactory = factoryMap.get(factoryName).copy();
        this.player.setFactory(factoryMap.get(factoryName).copy());
        gameStateManager.setFactory(factoryMap.get(factoryName).copy());
    }
}
