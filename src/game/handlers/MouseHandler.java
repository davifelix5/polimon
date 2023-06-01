package game.handlers;

import game.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class MouseHandler extends MouseAdapter {
    int pressedPositionX, pressedPositionY;
    GamePanel game;

    public MouseHandler(GamePanel game) {
        this.game = game;
    }

    private final ArrayList<MouseInteraction> mouseElements = new ArrayList<>();
    public void mousePressed(MouseEvent e) {
        this.pressedPositionX = e.getX();
        this.pressedPositionY = e.getY();

        for (MouseInteraction el: mouseElements) {
            if (isMouseOverOnPress(el.getPosX(), el.getPosY(),  el.getWidth(), el.getHeight()))
                el.getInteractionStrategy().onPress(game);
        }
    }

    public void mouseReleased(MouseEvent e) {

    }
    private boolean isMouseOverOnPress(int x, int y, int width, int height) {
        if(pressedPositionX > x && pressedPositionX < x + width) {
            if(pressedPositionY > y && pressedPositionY < y + height) {
                return true;
            }else return false;
        }else return false;

    }

    public void addElement(MouseInteraction element) {
        mouseElements.add(element);
    }
}
