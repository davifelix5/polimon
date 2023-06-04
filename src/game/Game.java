package game;

import game.handlers.KeyHandler;
import game.handlers.MouseHandler;
import game.state.*;
import game.state.Menu;

import javax.swing.*;
import java.awt.*;

public class Game extends JPanel implements Runnable {

    public static final int originalTileSize = 32, scale = 1, tileSize = scale * originalTileSize;
    public static final int maxScreenRow = 20, maxScreenCol = 30;
    public static final int width = tileSize*maxScreenCol, height = tileSize*maxScreenRow;
    final int FPS = 60;

    Thread thread;
    private final StateManager gameStateManager = new StateManager();

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);

    public Game() {
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setDoubleBuffered(true);
        this.gameStateManager.addState(StateID.RestScreen, new RestScreen(keyHandler, gameStateManager));
        this.gameStateManager.addState(StateID.Menu, new Menu(mouseHandler, gameStateManager));
        this.gameStateManager.addState(StateID.Bienio, new Bienio(keyHandler, gameStateManager));
        this.gameStateManager.setState(StateID.RestScreen);

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
        gameStateManager.getCurrentState().tick();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameStateManager.getCurrentState().render(g);
    }

    public IStateManager getStateManager() {
        return this.gameStateManager;
    }

    public static int clamp(int value, int min, int max) {
        if (value >= max)
            return max;
        else if (value <= min)
            return min;
        return value;
    }
}
