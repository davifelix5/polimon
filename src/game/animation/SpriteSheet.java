package game.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {
    public BufferedImage image;

    public int spriteWidth, spriteHeigth, lins, cols;

    public ArrayList<BufferedImage> sprites = new ArrayList<>();

    public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeigth){
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeigth = spriteHeigth;
        this.lins = (image.getHeight() / spriteHeigth);
        this.cols = (image.getWidth() / spriteWidth);
        this.getSpriteList();
    }

    public BufferedImage grabImage(int col, int lin){
        int x = (col * spriteWidth) - spriteWidth;
        int y = (lin * spriteHeigth) - spriteHeigth;
        return image.getSubimage(x, y, spriteWidth, spriteHeigth);
    }

    public void getSpriteList(){
        for(int j = 1; j <= cols; j ++){
            for(int i = 1; i <= lins; i ++){
                sprites.add(grabImage(i, j));
            }
        }
    }

    public BufferedImage getSprite(int lin, int col){
        return sprites.get((lin*4) + col);
    }
}
