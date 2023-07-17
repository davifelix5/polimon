package game.entity.npc;

import game.Game;
import game.entity.animation.Animation;
import game.entity.animation.MoveAnimationSet;
import game.entity.animation.SpriteSheet;
import game.entity.Entity;
import game.map.TileManager;
import game.ui.handlers.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Npc extends Entity {
    private MoveAnimationSet animationSet;
    private final int initialPositionX, initialPositionY;
    private final int movingRate;
    private final Rectangle moveArea;
    private final Dialogue dialogue;
    private NPCStrategy strategy;
    private boolean dialogueChanged = false;
    private boolean dialogActivated = false;
    private final KeyHandler keyInput;
    private final NpcInteractionStrategy interaction;

    public Npc(Rectangle moveArea, TileManager tm, int movingRate, Dialogue dialogue, KeyHandler keyInput, NpcInteractionStrategy interaction) {
        super(moveArea.x, moveArea.y);
        this.tileManager = tm;
        this.initialPositionX = moveArea.x;
        this.initialPositionY = moveArea.y;
        this.moveArea = moveArea;
        this.movingRate = movingRate;
        this.dialogue = dialogue;
        this.keyInput = keyInput;
        this.interaction = interaction;
    }

    public void setSpritesheet(SpriteSheet sprites) {
        this.animationSet = new MoveAnimationSet(sprites, 0, 20);
        this.animationSet.setCurrentIndex(0);
        this.animationSet.getCurrentAnimation().start();
    }

    public void renderDialogue(Graphics g) {
        if (dialogActivated) this.dialogue.render(g);
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
        if (animationSet == null)
            return;

        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações

        int maxPosX = initialPositionX + (int) moveArea.getWidth() - getWidth();
        int maxPosY = initialPositionY + (int) moveArea.getHeight() - getHeight();

        if (getVelX() != 0 || getVelY() != 0) {
            getAnimation().tick();
        }

        /* MOVIMENTAÇÃO */
        if (!isDialogueActivated()) {
            int nextPosX = getWorldX() + getVelX();
            int nextPosY = getWorldY() + getVelY();
            setWorldX(Game.clamp(nextPosX, initialPositionX, maxPosX));
            setWorldY(Game.clamp(nextPosY, initialPositionY, maxPosY));
            strategy.setAction(this);
        }

        /* ANIMAÇÃO */
        if ((getWorldX() == maxPosX || dialogActivated) && animationSet.getCurrentIndex() == RIGHT ||
                (getWorldX() == initialPositionX || dialogActivated) && animationSet.getCurrentIndex() == LEFT) {
            setVelX(0);
            animationSet.setCurrentIndex(RIGHT);
            getAnimation().reset();
            getAnimation().stop();
        }

        if ((getWorldY() == maxPosY || dialogActivated) && animationSet.getCurrentIndex() == BACKWARD ||
                (getWorldY() == initialPositionY || dialogActivated) && animationSet.getCurrentIndex() == FOWARD) {
            animationSet.setCurrentIndex(BACKWARD);
            getAnimation().reset();
            getAnimation().stop();
            setVelY(0);
        }

        /* DIÁLOGO */
        if(keyInput.isSpacePressed() && dialogActivated) { // Está ocorrendo um diálogo, sendo solicitada a próxima fala
            if (!dialogueChanged) { // Se não houve uma mudança após espaço pressionado
                dialogueChanged = true;
                if (dialogue.getCurrentLine() < dialogue.getDialogues().length - 1) // Vai para a próxima fala
                    dialogue.nextLine();
                else if (dialogue.getCurrentLine() == dialogue.getDialogues().length - 1) { // Acaba o diálogo quando acabaram as falas
                    // Diálogo acabou
                    dialogueChanged = false;
                    dialogActivated = false;
                    this.dialogue.reset();
                    this.interaction.onDialogueFinished();
                }
            }
        } else {
            dialogueChanged = false; // O espaço foi solto, podendo haver uma mudança de fala na próx ativação
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 25, 30);
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
        return dialogActivated;
    }

    public void setDialogueActivated(boolean dialogueActivated) {
        this.dialogActivated = dialogueActivated;
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
