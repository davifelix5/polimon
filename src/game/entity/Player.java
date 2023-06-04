package game.entity;

import game.GamePanel;
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

    public Player(int x, int y, int movingRate, KeyHandler movementKeyInput) {
        super(x, y);
        this.movingRate = movingRate;
        this.currentAnimationIndex = 0;
        this.movementKeyInput = movementKeyInput;
        readSpriteSheetImage();
        definePlayerAnimation();
    }

    @Override
    public void tick() {
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3;

        if (!movementKeyInput.downPressed && !movementKeyInput.upPressed){
            if (currentAnimationIndex == FOWARD || currentAnimationIndex == BACKWARD) {
                getAnimation().stop();
                getAnimation().reset();
            }
            setVelY(0);
        }

        if (!movementKeyInput.rightPressed && !movementKeyInput.leftPressed) {
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

        getAnimation().tick();
        int nextPosX = getX() + getVelX();
        int nextPosY = getY() + getVelY();
        setX(GamePanel.clamp(nextPosX, 0, GamePanel.width - playerWidth));
        setY(GamePanel.clamp(nextPosY, 0, GamePanel.height - playerHeight));
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

    }

}


