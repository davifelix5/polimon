package sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {
    public BufferedImage image;

    public int spriteWidth, spriteHeigth;

    public ArrayList<BufferedImage> sprites = new ArrayList<>();

    // 
    public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeigth){
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeigth = spriteHeigth;
        this.getSpriteList();
    }

    public BufferedImage grabImage(int col, int lin){
        int x = (col * spriteWidth) - spriteWidth;
        int y = (lin * spriteHeigth) - spriteHeigth;
        return image.getSubimage(x, y, spriteWidth, spriteHeigth);
    }

    public void getSpriteList(){
        int cols = (image.getWidth() / spriteWidth);
        int lins = (image.getHeight() / spriteHeigth);

        for(int i = 0; i < cols; i ++){
            for(int j = 0; j < lins; j ++){
                sprites.add(grabImage(i, j));
            }
        }
    }

    public BufferedImage getSprite(int lin, int col){
        int i = lin--;
        int j = col--; 
        return sprites.get((i*4) + j);
    }
}
