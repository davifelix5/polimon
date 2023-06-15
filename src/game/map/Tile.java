package game.map;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private final int width, height;
    private final boolean solid;
    private final BufferedImage image;


    public Tile(int width, int height, BufferedImage image, boolean solid) {
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.image = image;
    }

    public void draw(Graphics g, int x, int y) {
        if (solid) {
            g.setColor(Color.red);
        } else {
            g.setColor(Color.green);
        }
        g.drawRect(x, y, width, height);
        g.drawImage(this.image, x, y, width, height, null);
    }

    public boolean isSolid() {
        return solid;
    }
}
