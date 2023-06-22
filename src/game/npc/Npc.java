package game.npc;

import game.Game;
import game.animation.Animation;
import game.animation.IAnimationSet;
import game.animation.MoveAnimationSet;
import game.animation.SpriteSheet;
import game.entity.Entity;
import game.entity.PlayerAnimations;
import game.map.TileManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Npc extends Entity {
    public boolean collision;
    public String direction;
    private int positionX, positionY;
    private final PlayerAnimations currentAnimation;
    private TileManager tileManager;
    public int actionLockCounter = 0;
    private MoveAnimationSet animationSet;
    private Animation getAnimation() {
        return this.animationSet.getCurrentAnimation();
    }


    public Npc( int x, int y) {
        super(x, y);
        this.collision = false;
        this.currentAnimation = PlayerAnimations.Walk;

        loadAnimations();
    }

    @Override
    public void render(Graphics g) {
        //Só pode dar renderer no Npc quando ele estiver dentro da area da camera
        BufferedImage image = getAnimation().nextSprite();
        g.drawImage(image, positionX, positionY, 32, 32, null);
    }


    @Override
    public void tick() {
        this.positionX = (Game.width/2) - getWidth();
        this.positionY = (Game.height/2) - getHeight();

        if (!collision) {
            getAnimation().tick();
            int nextPosX = getWorldX() + getVelX();
            int nextPosY = getWorldY() + getVelY();
            setWorldX(Game.clamp(nextPosX, 0, tileManager.getMaxWidth() - getWidth()));
            setWorldY(Game.clamp(nextPosY, 0, tileManager.getMaxHeight() - getHeight()));
        }

        setAction();
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
                direction = "up";
            }
            if (actionNumber > 25 && actionNumber <= 50) {
                direction = "down";
            }
            if (actionNumber > 50 && actionNumber <= 75) {
                direction = "left";
            }
            if (actionNumber > 75 && actionNumber <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }

    public void loadAnimations() {
        try {
            BufferedImage npcSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/NpcSprites.png"));
            SpriteSheet npcSpriteSheet = new SpriteSheet(npcSprites, 32, 32);
            this.animationSet = new MoveAnimationSet(npcSpriteSheet, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return 32;
    }

    public int getHeight() {
        return 32;

    }
}
