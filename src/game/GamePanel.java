package game;

import game.handlers.KeyHandler;
import game.handlers.MouseHandler;
import game.state.Menu;
import game.state.Play;
import game.state.RestScreen;
import game.state.State;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int originalTileSize = 32;
    public static final int scale = 1;
    public static final int tileSize = scale * originalTileSize;
    public static final int maxScreenRow = 20;
    public static final int maxScreenCol = 30;
    public static final int width = tileSize*maxScreenCol;
    public static final int height = tileSize*maxScreenRow;
    final int FPS = 60;

    Thread thread;
    public Menu menu;
    public RestScreen rest;
    public Play play;

    public State gameState;

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);

    public GamePanel() {
        this.gameState = State.RestScreen;
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.setDoubleBuffered(true);
        this.rest = new RestScreen();
        this.menu = new Menu(mouseHandler);
        this.play = new Play(keyHandler);

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

    public void manageState() {
        if (gameState == State.RestScreen) {
            if (keyHandler.anyKeyPressed) {
                this.gameState = State.MainMenu;
            }
        }
    }

    public void tick() {
        manageState();
        switch (gameState) {
            case MainMenu -> menu.tick();
            case RestScreen -> rest.tick();
            case Game -> play.tick();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (gameState) {
            case MainMenu -> menu.render(g);
            case RestScreen -> rest.render(g);
            case Game -> play.render(g);
        }
    }

    public void setGameState(State gameState) {
        this.gameState = gameState;
    }
}
