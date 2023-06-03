package game.animation;

import java.awt.image.BufferedImage;

public interface IAnimation {
    BufferedImage nextSprite();
    void tick();
    void reset();
    void stop();
    void start();
}
