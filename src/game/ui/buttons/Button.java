package game.ui.buttons;

import java.awt.*;

import game.ui.handlers.MouseHandler;
import game.ui.handlers.MouseInteraction;
import game.ui.handlers.MouseInteractionStrategy;

public class Button implements MouseInteraction {

    String title;
    int fontSize;
    private final int posX, posY;
    private final int width, height;
    private final MouseInteractionStrategy pressStrategy;
    private boolean active = false;

    @Override
    public String toString() {
        return this.title;
    }

    public Button(String title, int fontSize, int posX, int posY, int width, int height, MouseHandler mouse, MouseInteractionStrategy pressStrategy) {
        this.fontSize = fontSize;
        this.title = title;
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        mouse.addElement(this);
        this.pressStrategy = pressStrategy;
    }
    @Override
    public MouseInteractionStrategy getInteractionStrategy() {
        return pressStrategy;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setIsActive(boolean isActive) {
        this.active = isActive;
    }


    public void render(Graphics g) {
        int strLength = title.length();

        Font h2 = new Font("arial", Font.PLAIN, fontSize);
        g.setFont(h2);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(posX, posY,width,height);

        g.setColor(Color.white);

        g.drawString(title, (posX + width / 2 ) - strLength * (fontSize / 2) / 2, (posY + height / 2) + fontSize / 2);
        g.drawRect(posX, posY, width, height);

    }

}
