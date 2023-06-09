package game.entity;

import game.Game;
import game.animation.*;
import game.handlers.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Player extends Entity {

    private final KeyHandler movementKeyInput;
    private int movingRate;
    private boolean coliding;

    private final IAnimationSet[] animationSets = new IAnimationSet[PlayerAnimations.values().length];
    private PlayerAnimations currentAnimation;

    public Player(int x, int y, int movingRate, KeyHandler movementKeyInput, PlayerAnimations currentAnimation) {
        super(x, y);
        this.movingRate = movingRate;
        this.movementKeyInput = movementKeyInput;
        this.currentAnimation = currentAnimation;
        this.coliding = false;
        loadAnimations();
    }

    private void loadAnimations() {
        try {
            BufferedImage bikeSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
            BufferedImage walkSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            SpriteSheet bikeSpritesheet = new SpriteSheet(bikeSprites, 48, 48);
            SpriteSheet walkSpriteSheet = new SpriteSheet(walkSprites, 32, 41);
            this.animationSets[PlayerAnimations.Bike.getValue()] = new MoveAnimationSet(bikeSpritesheet, 0);
            this.animationSets[PlayerAnimations.Walk.getValue()] = new MoveAnimationSet(walkSpriteSheet, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3;

        if (movementKeyInput.bikeButtonPressed) {
            setCurrentAnimation(PlayerAnimations.Bike);
            this.setMovingRate(3);
        } else{
            setCurrentAnimation(PlayerAnimations.Walk);
            this.setMovingRate(2);
        }

        if (!movementKeyInput.downPressed && !movementKeyInput.upPressed || coliding){
            if (getCurrentAnimationSet().getCurrentIndex() == FOWARD || getCurrentAnimationSet().getCurrentIndex() == BACKWARD) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelY(0);
        }

        if (!movementKeyInput.rightPressed && !movementKeyInput.leftPressed || coliding) {
            if (getCurrentAnimationSet().getCurrentIndex() == RIGHT || getCurrentAnimationSet().getCurrentIndex() == LEFT) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelX(0);
        }

        // Movimentação em X
        if (getVelX() == 0) {
            if (movementKeyInput.upPressed) {
                getCurrentAnimationSet().setCurrentIndex(FOWARD);
                getAnimation().start();
                setVelY(-movingRate);
            } else if (movementKeyInput.downPressed) {
                getCurrentAnimationSet().setCurrentIndex(BACKWARD);
                getAnimation().start();
                setVelY(movingRate);
            }
        }

        // Movimentação em y
        if (getVelY() == 0) {
            if (movementKeyInput.leftPressed) {
                getCurrentAnimationSet().setCurrentIndex(LEFT);
                getAnimation().start();
                setVelX(-movingRate);
            } else if (movementKeyInput.rightPressed) {
                getCurrentAnimationSet().setCurrentIndex(RIGHT);
                getAnimation().start();
                setVelX(movingRate);
            }
        }

        if (!coliding) {
            getAnimation().tick();
            int nextPosX = getX() + getVelX();
            int nextPosY = getY() + getVelY();
            setX(Game.clamp(nextPosX, 0, Game.width - getCurrentAnimationSet().getSprites().spriteWidth));
            setY(Game.clamp(nextPosY, 0, Game.height - getCurrentAnimationSet().getSprites().spriteHeigth));
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage image = getAnimation().nextSprite();
        g.drawImage(image, getX(), getY(), 32, 32, null);
    }

    private Animation getAnimation() {
        return this.animationSets[this.currentAnimation.getValue()].getCurrentAnimation();
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

    public void setCurrentAnimation(PlayerAnimations currentAnimation) {
        this.currentAnimation = currentAnimation;
    }

    public IAnimationSet getCurrentAnimationSet() {
        return this.animationSets[this.currentAnimation.getValue()];
    }

}


