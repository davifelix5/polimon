package game.ui.handlers;

public interface MouseInteraction {
    MouseInteractionStrategy getInteractionStrategy();
    int getWidth();
    int getHeight();
    int getPosX();
    int getPosY();
    boolean isActive();
    void setIsActive(boolean isActive);
}
