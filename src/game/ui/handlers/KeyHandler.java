package game.ui.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private boolean upPressed, downPressed, leftPressed, rightPressed, bikeButtonPressed, enterPressed, escPressed;
    private boolean pToggle = false;
    private boolean anyKeyPressed, spacePressed;
    private boolean useItemPressed;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        anyKeyPressed = true;
        switch (code) {
            case KeyEvent.VK_B -> bikeButtonPressed = true;
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
            case KeyEvent.VK_SPACE -> spacePressed = true;
            case KeyEvent.VK_ENTER -> enterPressed = true;
            case KeyEvent.VK_ESCAPE -> escPressed = true;
            case KeyEvent.VK_U -> useItemPressed = true;
            case KeyEvent.VK_P -> pToggle = !pToggle;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        anyKeyPressed = false;
        switch (code) {
            case KeyEvent.VK_B -> bikeButtonPressed = false;
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
            case KeyEvent.VK_SPACE -> spacePressed = false;
            case KeyEvent.VK_ENTER -> enterPressed = false;
            case KeyEvent.VK_ESCAPE -> escPressed = false;
            case KeyEvent.VK_U -> useItemPressed = false;
        }
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isBikeButtonPressed() {
        return bikeButtonPressed;
    }

    public boolean isEnterPressed() {
        return enterPressed;
    }

    public boolean isEscPressed() {
        return escPressed;
    }

    public boolean ispToggle() {
        return pToggle;
    }

    public boolean isAnyKeyPressed() {
        return anyKeyPressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

    public boolean isUseItemPressed() {
        return useItemPressed;
    }
}
