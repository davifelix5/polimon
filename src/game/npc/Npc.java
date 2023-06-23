package game.npc;

import game.Game;
import game.animation.Animation;
import game.animation.MoveAnimationSet;
import game.animation.SpriteSheet;
import game.entity.Entity;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Npc extends Entity {
    private int actionLockCounter = 0;
    private MoveAnimationSet animationSet;
    private final int initialPositionX, initialPositionY;
    private final int movingRate;
    private final Rectangle moveArea;

    private final int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações

    public Npc(int x, int y, TileManager tm, int movingRate, Rectangle moveArea) {
        super(x, y);
        this.tileManager = tm;
        this.initialPositionX = x;
        this.initialPositionY = y;
        this.moveArea = moveArea;
        this.movingRate = movingRate;
        loadAnimations();
        this.animationSet.setCurrentIndex(0);
        setVelY(movingRate);
        this.animationSet.getCurrentAnimation().start();
    }

    @Override
    public void render(Graphics g) {
        //Só pode dar renderer no Npc quando ele estiver dentro da area da camera
        BufferedImage image = getAnimation().nextSprite();
        int posX = getWorldX() - tileManager.getReferenceX(), posY = getWorldY() - tileManager.getReferenceY();
        g.drawImage(image, posX, posY, getWidth(), getHeight(), null);
    }


    @Override
    public void tick() {

        getAnimation().tick();
        int nextPosX = getWorldX() + getVelX();
        int nextPosY = getWorldY() + getVelY();
        int maxPosX = initialPositionX + (int) moveArea.getWidth() - getWidth(), maxPosY = initialPositionY + (int) moveArea.getHeight() - getHeight();
        setWorldX(Game.clamp(nextPosX, initialPositionX, maxPosX));
        setWorldY(Game.clamp(nextPosY, initialPositionY, maxPosY));

        if (getWorldX() == maxPosX && animationSet.getCurrentIndex() == RIGHT ||
                getWorldX() == initialPositionX && animationSet.getCurrentIndex() == LEFT) {
            setVelX(0);
            animationSet.setCurrentIndex(RIGHT);
            getAnimation().reset();
            getAnimation().stop();
        }

        if (getWorldY() == maxPosY && animationSet.getCurrentIndex() == BACKWARD ||
                getWorldY() == initialPositionY && animationSet.getCurrentIndex() == FOWARD) {
            animationSet.setCurrentIndex(BACKWARD);
            getAnimation().reset();
            getAnimation().stop();
            setVelY(0);
        }

        setAction();
        animationSet.getCurrentAnimation().tick();
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 10, 10);
    }

    public void setAction() {

        actionLockCounter++;

        Random i = new Random();
        int actionNumber = i.nextInt(100) + 1;

        if (actionLockCounter == 120) {
            if (actionNumber <= 25) {
                animationSet.setCurrentIndex(FOWARD);
                animationSet.getCurrentAnimation().start();
                setVelX(0);
                setVelY(-movingRate);
            }
            else if (actionNumber <= 50) {
                animationSet.setCurrentIndex(BACKWARD);
                animationSet.getCurrentAnimation().start();
                setVelX(0);
                setVelY(movingRate);
            }
            else if (actionNumber <= 75) {
                animationSet.setCurrentIndex(LEFT);
                animationSet.getCurrentAnimation().start();
                setVelY(0);
                setVelX(-movingRate);
            }
            else {
                animationSet.setCurrentIndex(RIGHT);
                animationSet.getCurrentAnimation().start();
                setVelY(0);
                setVelX(movingRate );
            }
            actionLockCounter = 0;
        }
    }

    public void loadAnimations() {
        try {
            BufferedImage npcSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/NpcSprites.png"));
            SpriteSheet npcSpriteSheet = new SpriteSheet(npcSprites, 51, 54);
            this.animationSet = new MoveAnimationSet(npcSpriteSheet, 0, 20);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return animationSet.getSprites().spriteWidth;
    }

    public int getHeight() {
        return animationSet.getSprites().spriteHeigth;
    }

    private Animation getAnimation() {
        return this.animationSet.getCurrentAnimation();
    }
}
