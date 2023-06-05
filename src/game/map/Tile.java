package game.map;

import game.animation.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private final int width, height;
    private final boolean solid;
    private final BufferedImage image;


    public Tile(SpriteSheet spriteSheet, int lin, int col, boolean solid) {
        this.width = spriteSheet.spriteWidth;
        this.height = spriteSheet.spriteHeigth;
        this.solid = solid;
        this.image = spriteSheet.getSprite(lin, col);
    }

    public void draw(Graphics g, int x, int y) {
        g.drawImage(this.image, x, y, width, height, null);
    }

    public boolean isSolid() {
        return solid;
    }
}
