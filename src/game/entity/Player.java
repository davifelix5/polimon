package game.entity;

import game.Game;
import game.animation.Animation;
import game.animation.SpriteSheet;
import game.handlers.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    private int width = 32, height = 41;
    private final KeyHandler movementKeyInput;
    private ArrayList<Animation> animations;
    private int currentAnimationIndex;
    private int movingRate;
    private SpriteSheet animationSpriteSheet;
    private boolean coliding;

    public Player(int x, int y, int movingRate, SpriteSheet animationSpriteSheet, KeyHandler movementKeyInput) {
        super(x, y);
        this.movingRate = movingRate;
        this.currentAnimationIndex = 0;
        this.animationSpriteSheet = animationSpriteSheet;
        this.movementKeyInput = movementKeyInput;
        this.coliding = false;
        updateAnimations();
    }

    @Override
    public void tick() {
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3;

        if (!movementKeyInput.downPressed && !movementKeyInput.upPressed || coliding){
            if (currentAnimationIndex == FOWARD || currentAnimationIndex == BACKWARD) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelY(0);
        }

        if (!movementKeyInput.rightPressed && !movementKeyInput.leftPressed || coliding) {
            if (currentAnimationIndex == RIGHT || currentAnimationIndex == LEFT) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelX(0);
        }

        // Movimentação em X
        if (getVelX() == 0) {
            if (movementKeyInput.upPressed) {
                currentAnimationIndex = FOWARD;
                getAnimation().start();
                setVelY(-movingRate);
            } else if (movementKeyInput.downPressed) {
                currentAnimationIndex = BACKWARD;
                getAnimation().start();
                setVelY(movingRate);
            }
        }

        // Movimentação em y
        if (getVelY() == 0) {
            if (movementKeyInput.leftPressed) {
                currentAnimationIndex = LEFT;
                getAnimation().start();
                setVelX(-movingRate);
            } else if (movementKeyInput.rightPressed) {
                currentAnimationIndex = RIGHT;
                getAnimation().start();
                setVelX(movingRate);
            }
        }

        if (!coliding) {
            getAnimation().tick();
            int nextPosX = getX() + getVelX();
            int nextPosY = getY() + getVelY();
            setX(Game.clamp(nextPosX, 0, Game.width - width));
            setY(Game.clamp(nextPosY, 0, Game.height - height));
        }
    }

    private Animation getAnimation() {
        return animations.get(currentAnimationIndex);
    }

    public void updateAnimations(){
        this.width = animationSpriteSheet.spriteWidth;
        this.height = animationSpriteSheet.spriteHeigth;
        ArrayList<Animation> animations = new ArrayList<>();
        for (int i = 0; i < animationSpriteSheet.lins; i++) {
            ArrayList<BufferedImage> frames = new ArrayList<>();
            for (int j = 0; j < animationSpriteSheet.cols; j++)
                frames.add(animationSpriteSheet.getSprite(i, j));
            animations.add(new Animation(frames, 10));
        }
        this.animations = animations;
    }

    public void setAnimationFromSpriteSheet(SpriteSheet spriteSheet) {
        if (spriteSheet != animationSpriteSheet) {
            this.animationSpriteSheet = spriteSheet;
            updateAnimations();
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage image = getAnimation().nextSprite();
        g.drawImage(image, getX(), getY(), 32, 32, null);
        g.setColor(Color.GREEN);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 10, 10);
    }

    public void setColiding(boolean coliding) {
        this.coliding = coliding;
    }

    public void setMovingRate(int movingRate) {
        this.movingRate = movingRate;
    }
}


