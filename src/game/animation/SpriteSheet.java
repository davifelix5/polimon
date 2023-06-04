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

    public BufferedImage grabImage(int lin, int col){
        int x = (col * spriteWidth);
        int y = (lin * spriteHeigth);
        return image.getSubimage(x, y, spriteWidth, spriteHeigth);
    }

    public void getSpriteList(){
        for(int i = 0; i < lins; i ++){
            for(int j = 0; j < cols; j ++){
                sprites.add(grabImage(i, j));
            }
        }
    }

    public BufferedImage getSprite(int lin, int col){
        return sprites.get((lin*this.cols) + col);
    }
}
