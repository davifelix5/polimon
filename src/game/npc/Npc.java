package game.npc;

import game.Game;
import game.animation.Animation;
import game.animation.MoveAnimationSet;
import game.animation.SpriteSheet;
import game.entity.Entity;
import game.entity.NPCStrategy;
import game.map.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Npc extends Entity {
    private final MoveAnimationSet animationSet;
    private final int initialPositionX, initialPositionY;
    private final int movingRate;
    private final Rectangle moveArea;
    private final Dialogue dialogue;

    private NPCStrategy strategy;

    public Npc(int x, int y, TileManager tm, int movingRate, Rectangle moveArea, Dialogue dialogue, SpriteSheet spriteSheet) {
        super(x, y);
        this.tileManager = tm;
        this.initialPositionX = x;
        this.initialPositionY = y;
        this.moveArea = moveArea;
        this.movingRate = movingRate;
        this.animationSet = new MoveAnimationSet(spriteSheet, 0, 20);
        this.animationSet.setCurrentIndex(0);
        this.animationSet.getCurrentAnimation().start();
        this.dialogue = dialogue;
    }

    public void renderDialogue(Graphics g) {
        dialogue.render(g);
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
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações

        getAnimation().tick();
        int nextPosX = getWorldX() + getVelX();
        int nextPosY = getWorldY() + getVelY();
        int maxPosX = initialPositionX + (int) moveArea.getWidth() - getWidth();
        int maxPosY = initialPositionY + (int) moveArea.getHeight() - getHeight();

        if (!dialogue.isActivated()) {
            setWorldX(Game.clamp(nextPosX, initialPositionX, maxPosX));
            setWorldY(Game.clamp(nextPosY, initialPositionY, maxPosY));
            strategy.setAction(this);
        }

        if ((getWorldX() == maxPosX || dialogue.isActivated()) && animationSet.getCurrentIndex() == RIGHT ||
                (getWorldX() == initialPositionX || dialogue.isActivated()) && animationSet.getCurrentIndex() == LEFT) {
            setVelX(0);
            animationSet.setCurrentIndex(RIGHT);
            getAnimation().reset();
            getAnimation().stop();
        }

        if ((getWorldY() == maxPosY || dialogue.isActivated()) && animationSet.getCurrentIndex() == BACKWARD ||
                (getWorldY() == initialPositionY || dialogue.isActivated()) && animationSet.getCurrentIndex() == FOWARD) {
            animationSet.setCurrentIndex(BACKWARD);
            getAnimation().reset();
            getAnimation().stop();
            setVelY(0);
        }

        animationSet.getCurrentAnimation().tick();

        this.dialogue.tick();

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 10, 10);
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

    public boolean isDialogueActivated() {
        return dialogue.isActivated();
    }

    public void setDialogueActivated(boolean dialogueActivated) {
        this.dialogue.setActivated(dialogueActivated);
    }

    public MoveAnimationSet getAnimationSet() {
        return animationSet;
    }

    public int getMovingRate() {
        return movingRate;
    }

    public void setStrategy(NPCStrategy strategy) {
        this.strategy = strategy;
    }
}
