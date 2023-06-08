package game.entity;

import game.Game;
import game.animation.Animation;
import game.animation.IAnimationSet;
import game.handlers.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private int width = 32, height = 41;
    private final KeyHandler movementKeyInput;
    private int movingRate;
    private boolean coliding;
    private IAnimationSet moveAnimation;

    public Player(int x, int y, int movingRate, IAnimationSet moveAnimation, KeyHandler movementKeyInput) {
        super(x, y);
        this.movingRate = movingRate;
        this.moveAnimation = moveAnimation;
        this.movementKeyInput = movementKeyInput;
        this.coliding = false;
    }

    @Override
    public void tick() {
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3;

        if (!movementKeyInput.downPressed && !movementKeyInput.upPressed || coliding){
            if (this.moveAnimation.getCurrentIndex() == FOWARD || this.moveAnimation.getCurrentIndex() == BACKWARD) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelY(0);
        }

        if (!movementKeyInput.rightPressed && !movementKeyInput.leftPressed || coliding) {
            if (this.moveAnimation.getCurrentIndex() == RIGHT || this.moveAnimation.getCurrentIndex() == LEFT) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelX(0);
        }

        // Movimentação em X
        if (getVelX() == 0) {
            if (movementKeyInput.upPressed) {
                this.moveAnimation.setCurrentIndex(FOWARD);
                getAnimation().start();
                setVelY(-movingRate);
            } else if (movementKeyInput.downPressed) {
                this.moveAnimation.setCurrentIndex(BACKWARD);
                getAnimation().start();
                setVelY(movingRate);
            }
        }

        // Movimentação em y
        if (getVelY() == 0) {
            if (movementKeyInput.leftPressed) {
                this.moveAnimation.setCurrentIndex(LEFT);
                getAnimation().start();
                setVelX(-movingRate);
            } else if (movementKeyInput.rightPressed) {
                this.moveAnimation.setCurrentIndex(RIGHT);
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

    @Override
    public void render(Graphics g) {
        BufferedImage image = getAnimation().nextSprite();
        g.drawImage(image, getX(), getY(), 32, 32, null);
    }

    private Animation getAnimation() {
        return this.moveAnimation.getCurrentAnimation();
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

    public IAnimationSet getMoveAnimation() {
        return moveAnimation;
    }

    public void setMoveAnimation(IAnimationSet moveAnimation) {
        this.width = moveAnimation.getSprites().spriteWidth;
        this.height = moveAnimation.getSprites().spriteHeigth;
        this.moveAnimation = moveAnimation;
    }
}


