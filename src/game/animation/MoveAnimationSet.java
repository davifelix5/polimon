package game.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Abstração para um conjunto de animações que indicam movimento do jogador
 */
public class MoveAnimationSet implements IAnimationSet {
    private int currentIndex; // Animação atual
    private ArrayList<Animation> animations; // Todas as animações na ordem: frente, esquerda, direita, cima
    private final SpriteSheet movingSprites; // spritesheet com as animações

    public MoveAnimationSet(SpriteSheet movingSprites, int initialIndex) {
        this.movingSprites = movingSprites;
        this.currentIndex = initialIndex;
        loadAnimations();
    }

    /**
     * Carrega o spritesheet com as animações para dentro da lista de animações, seguindo a ordem estipulada acima
     */
    public void loadAnimations() {
        ArrayList<Animation> animations = new ArrayList<>();
        for (int i = 0; i < movingSprites.lins; i++) {
            ArrayList<BufferedImage> frames = new ArrayList<>();
            for (int j = 0; j < movingSprites.cols; j++)
                frames.add(movingSprites.getSprite(i, j));
            animations.add(new Animation(frames, 10));
        }
        this.animations = animations;
    }

    @Override
    public SpriteSheet getSprites() {
        return movingSprites;
    }

    @Override
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }

    @Override
    public Animation getCurrentAnimation() {
        return this.animations.get(currentIndex);
    }

    @Override
    public int getCurrentIndex() {
        return currentIndex;
    }
}