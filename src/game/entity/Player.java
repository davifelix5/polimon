package game.entity;

import game.Game;
import game.animation.*;
import game.handlers.KeyHandler;
import game.map.LayerType;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class Player extends Entity {

    private final KeyHandler movementKeyInput;
    private int movingRate;
    private int positionX, positionY;
    private boolean coliding;

    private final IAnimationSet[] animationSets = new IAnimationSet[PlayerAnimations.values().length];
    private PlayerAnimations currentAnimation;
    private final TileManager tileManager;

    public Player(int x, int y, int movingRate, KeyHandler movementKeyInput, PlayerAnimations currentAnimation, TileManager tileManager) {
        super(x, y);
        this.movingRate = movingRate;
        this.movementKeyInput = movementKeyInput;
        this.currentAnimation = currentAnimation;
        this.tileManager = tileManager;
        this.coliding = false;
        loadAnimations();
    }

    private void loadAnimations() {
        try {
            BufferedImage bikeSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
            BufferedImage walkSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            BufferedImage swimSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSwim.png"));
            SpriteSheet bikeSpritesheet = new SpriteSheet(bikeSprites, 48, 48);
            SpriteSheet walkSpriteSheet = new SpriteSheet(walkSprites, 32, 41);
            SpriteSheet swimSpritesheet = new SpriteSheet(swimSprites, 64, 82);
            this.animationSets[PlayerAnimations.Bike.getValue()] = new MoveAnimationSet(bikeSpritesheet, 0);
            this.animationSets[PlayerAnimations.Walk.getValue()] = new MoveAnimationSet(walkSpriteSheet, 0);
            this.animationSets[PlayerAnimations.Swimming.getValue()] = new MoveAnimationSet(swimSpritesheet,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        tileManager.setReferenceX(getWorldX() - positionX);
        tileManager.setReferenceY(getWorldY() - positionY);
        this.positionX = Game.width / 2 - getWidth();
        this.positionY = Game.height / 2 - getHeight();

        // Bordas no fim do mapa
        if (getWorldX() + getWidth() + Game.width / 2 >= this.tileManager.getMaxWidht()) {
            int referenceX = tileManager.getMaxWidht() - Game.width;
            this.positionX = getWorldX() - referenceX;
            this.tileManager.setReferenceX(referenceX);
        }

        if (getWorldY() + getHeight() + Game.height / 2 >= this.tileManager.getMaxHeight()) {
            int referenceY = tileManager.getMaxHeight() - Game.height;
            this.positionY = getWorldY() - referenceY;
            this.tileManager.setReferenceY(referenceY);

        }

        // Bordas no começo do mapa
        if (getWorldX() + getWidth() - Game.width / 2 <= 0 && getWorldX() <= Game.width / 2) {
            tileManager.setReferenceX(0);
            this.positionX = getWorldX();
        }

        if (getWorldY() + getHeight() - Game.height / 2 <= 0 && getWorldY() <= Game.height / 2) {
            tileManager.setReferenceY(0);
            this.positionY = getWorldY();
        }

        // Indices das animações
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3;

        if (movementKeyInput.bikeButtonPressed) {
            setCurrentAnimation(PlayerAnimations.Bike);
            this.setMovingRate(3);
        }

        if (tileManager.searchLayers( getWorldRow(), getWorldCol(), LayerType.SWIMABLE) != null){
            setCurrentAnimation(PlayerAnimations.Swimming);
            this.setMovingRate(4);
        } else if (!movementKeyInput.bikeButtonPressed) {
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
            int nextPosX = getWorldX() + getVelX();
            int nextPosY = getWorldY() + getVelY();
            setWorldX(Game.clamp(nextPosX, 0, tileManager.getMaxWidht() - getWidth()));
            setWorldY(Game.clamp(nextPosY, 0, tileManager.getMaxHeight() - getHeight()));
        }
    }

    @Override
    public void render(Graphics g) {
        BufferedImage image = getAnimation().nextSprite();

        g.drawImage(image, positionX, positionY, 32, 32, null);
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

    public int getWidth() {
        return this.getCurrentAnimationSet().getSprites().spriteWidth;
    }

    public int getHeight() {
        return this.getCurrentAnimationSet().getSprites().spriteHeigth;
    }

}


