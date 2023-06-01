package game;

import game.handlers.KeyHandler;
import game.handlers.MouseHandler;
import game.state.Menu;
import game.state.RestScreen;
import game.state.State;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 32;
    final int scale = 1;
    final int tileSize = scale * originalTileSize;
    final int maxScreenCol = 15;
    final int maxScreenRow = 20;
    final int width = tileSize*maxScreenRow;
    final int height = tileSize*maxScreenCol;
    final int FPS = 60;

    Thread thread;
    public Menu menu;
    public RestScreen rest;

    public State gameState;

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler(this);

    public GamePanel() {
        this.gameState = State.RestScreen;
        this.rest = new RestScreen();
        this.menu = new game.state.Menu(mouseHandler);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.requestFocus();
    }

    int playerX = 128, playerY = 196;
    int playerSpeed = 4;

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
        switch (gameState) {
            case RestScreen: {
                if (keyHandler.anyKeyPressed) {
                    this.gameState = State.MainMenu;
                }
            }
        }
    }

    public void tick() {
        manageState();
        switch (gameState) {
            case MainMenu -> menu.tick();
            case RestScreen -> rest.tick();
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        switch (gameState) {
            case MainMenu -> menu.render(g);
            case RestScreen -> rest.render(g);
        }
    }
}
