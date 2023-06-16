package game.handlers;

import game.Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


public class MouseHandler extends MouseAdapter {
    int pressedPositionX, pressedPositionY;
    Game game;

    public MouseHandler(Game game) {
        this.game = game;
    }

    private ArrayList<MouseInteraction> mouseElements = new ArrayList<>();

    /**
     * Percorre todos os elementos que podem interagir com o mouse e chamam sua estratégia de interação
     * para o evento correspondente
     * @param e o evento a ser processado
     */
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

    /**
     * Função que infica se o mouse está em cima de determinado objeto
     * @param x posição do objeto na direção x
     * @param y posição do objeto na direção y
     * @param width largura do objeto
     * @param height altura do objeto
     * @return verdadeiro se o mouse está em cuma do objeo e falso se o mouse não está
     */
    private boolean isMouseOverOnPress(int x, int y, int width, int height) {
        if(pressedPositionX > x && pressedPositionX < x + width) {
            return pressedPositionY > y && pressedPositionY < y + height;
        }else return false;
    }

    public void resetElements() {
        this.mouseElements = new ArrayList<>();
    }

    public void addElement(MouseInteraction element) {
        mouseElements.add(element);
    }
}
