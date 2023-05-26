import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 32;
    final int scale = 1;
    final int tileSize = scale * originalTileSize;
    final int maxScreenCol = 12;
    final int maxScreenRow = 16;
    final int width = tileSize*maxScreenRow;
    final int height = tileSize*maxScreenCol;
    final int FPS = 60;

    Thread thread;

    KeyHandler keyHandler = new KeyHandler();
    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
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
                update();
                // Since there has been updates, the game should render
                shouldRender = true;
                delta--;
            }

            if (shouldRender) {
                // Draw elements on the screen
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

    public void update() {
        if (keyHandler.upPressed)
            playerY -= playerSpeed;
        if (keyHandler.downPressed)
            playerY += playerSpeed;
        if (keyHandler.leftPressed)
            playerX -= playerSpeed;
        if (keyHandler.rightPressed)
            playerX += playerSpeed;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.CYAN);
        g2d.fillRect(playerX, playerY, tileSize, tileSize);
        g2d.dispose();
    }
}
