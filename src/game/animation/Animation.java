package game.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements IAnimation {

    private int currentFrame = 0, tickCount = 0;
    private final int ticksPerFrame;
    boolean running;
    private final ArrayList<BufferedImage> frames;

    public Animation(ArrayList<BufferedImage> frames, int ticksPerFrame) {
        this.frames = frames;
        this.ticksPerFrame = ticksPerFrame;
    }

    @Override
    public BufferedImage nextSprite() {
        return frames.get(currentFrame);
    }

    @Override
    public void tick() {
        if (running) {
            if (tickCount == ticksPerFrame) {
                if (currentFrame < frames.size() - 1) {
                    currentFrame++;
                } else {
                    currentFrame = 0;
                }
                tickCount = 0;
            }
            tickCount++;
        }
    }

    @Override
    public void reset() {
       currentFrame = 0;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void start() {
        running = true;
    }


}
