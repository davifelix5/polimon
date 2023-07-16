package game.entity.animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SpriteSheet {
    public BufferedImage image;

    public int spriteWidth, spriteHeigth, lins, cols;

    public final ArrayList<BufferedImage> sprites = new ArrayList<>();

    public SpriteSheet(BufferedImage image, int spriteWidth, int spriteHeigth){
        this.image = image;
        this.spriteWidth = spriteWidth;
        this.spriteHeigth = spriteHeigth;
        this.lins = (image.getHeight() / spriteHeigth);
        this.cols = (image.getWidth() / spriteWidth);
        this.getSpriteList();
    }

    public SpriteSheet(String imagePath, int spriteWidth, int spriteHeigth){
        try {
            this.image = ImageIO.read(new FileInputStream(imagePath));
            this.spriteWidth = spriteWidth;
            this.spriteHeigth = spriteHeigth;
            this.lins = (image.getHeight() / spriteHeigth);
            this.cols = (image.getWidth() / spriteWidth);
            this.getSpriteList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna uma subimagem do tamanho do sprite na posição especificada
     * @param lin posição vetical da imagem
     * @param col posição horizontal da imagem
     * @return imagem na poisção especificada
     */
    public BufferedImage grabImage(int lin, int col){
        int x = (col * spriteWidth);
        int y = (lin * spriteHeigth);
        return image.getSubimage(x, y, spriteWidth, spriteHeigth);
    }

    /**
     * Le os sprites dentro do spritesheet e salva-os em uma lista.
     */
    public void getSpriteList(){
        for(int i = 0; i < lins; i ++){
            for(int j = 0; j < cols; j ++){
                sprites.add(grabImage(i, j));
            }
        }
    }

    /**
     * Retorna um sprite a partir da identificação matricial na imagem
     * @param lin linha em que o sprite está posicionado
     * @param col coluna em que o sprite está posicionado
     * @return imagem do sprite correspondente
     */
    public BufferedImage getSprite(int lin, int col){
        return sprites.get((lin*this.cols) + col);
    }

    /**
     * Retorna o sprite na lista correspondente ao índice passado
     * @param index índice do sprite
     * @return sprite corresondente ao índice passado
     */
    public BufferedImage getSprite(int index) {
        return sprites.get(index);
    }
}
