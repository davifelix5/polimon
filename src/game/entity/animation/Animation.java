package game.entity.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements IAnimation {

    private int currentFrame = 0, tickCount = 0;
    private final int ticksPerFrame; // quandos ticks devem ser dados para mudar de frame
    boolean running; // indica se a animação está rodando
    private final ArrayList<BufferedImage> frames; // todos os sprites que correspondem a frames da animação

    public Animation(ArrayList<BufferedImage> frames, int ticksPerFrame) {
        this.frames = frames;
        this.ticksPerFrame = ticksPerFrame;
    }


    /**
     * Retorna o próximo sprite na animação
     * @return imagem com o frame atual da animação
     */
    @Override
    public BufferedImage nextSprite() {
        return frames.get(currentFrame);
    }

    /**
     * Atualiza os frames da animação segundo o tick count
     */
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

    /**
     * Reinicia a animação
     */
    @Override
    public void reset() {
       currentFrame = 0;
    }

    /**
     * Para de rodar a animação
     */
    @Override
    public void stop() {
        running = false;
    }

    /**
     * Começa a rodar a animação
     */
    @Override
    public void start() {
        running = true;
    }


}
