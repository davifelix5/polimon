package game.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MoveAnimationSet implements IAnimationSet {
    private int currentIndex;
    private ArrayList<Animation> animations;
    private final SpriteSheet movingSprites;

    public MoveAnimationSet(SpriteSheet movingSprites, int initialIndex) {
        this.movingSprites = movingSprites;
        this.currentIndex = initialIndex;
        updateAnimations();
    }

    public void updateAnimations() {
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