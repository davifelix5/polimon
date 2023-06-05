package game.entity;

import game.Game;
import game.animation.Animation;
import game.animation.SpriteSheet;
import game.handlers.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity {

    private final int playerWidth = 32;
    private final int playerHeight = 41;
    private final KeyHandler movementKeyInput;
    private final ArrayList<Animation> animations = new ArrayList<>();
    private int currentAnimationIndex;
    private final int movingRate;
    private BufferedImage spriteSheetImage;
    private boolean coliding;

    public Player(int x, int y, int movingRate, KeyHandler movementKeyInput) {
        super(x, y);
        this.movingRate = movingRate;
        this.currentAnimationIndex = 0;
        this.movementKeyInput = movementKeyInput;
        this.coliding = false;
        readSpriteSheetImage();
        definePlayerAnimation();
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
            setX(Game.clamp(nextPosX, 0, Game.width - playerWidth));
            setY(Game.clamp(nextPosY, 0, Game.height - playerHeight));
        }
    }

    private Animation getAnimation() {
        return animations.get(currentAnimationIndex);
    }

    private void readSpriteSheetImage() {
        try {
            this.spriteSheetImage = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void definePlayerAnimation(){
        SpriteSheet sprites = new SpriteSheet(spriteSheetImage, playerWidth, playerHeight);
        for (int i = 0; i < sprites.lins; i++) {
            ArrayList<BufferedImage> frames = new ArrayList<>();
            for (int j = 0; j < sprites.cols; j++)
                frames.add(sprites.getSprite(i, j));
            animations.add(new Animation(frames, 10));
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
}


