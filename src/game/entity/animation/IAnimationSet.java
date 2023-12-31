package game.entity.animation;

public interface IAnimationSet {
    void setCurrentIndex(int index);
    Animation getCurrentAnimation();
    int getCurrentIndex();
    SpriteSheet getSprites();
}
