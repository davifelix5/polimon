package game.combate;

import java.awt.*;

public class StatBar {
    private final int posX, posY, width, height, maxValue;
    private final Color color;
    public int currentValue;


    public StatBar(int posX, int posY, int width, int height, int maxValue, int currentValue, Color color) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.color = color;

    }

    public int getMaxValue() {
        return maxValue;
    }
    public int getCurrentValue() {
        return currentValue;
    }
    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }
    public void render(Graphics g){
        float percentageValue = (float) currentValue / maxValue;
        g.setColor(color);
        g.fillRect(posX,posY,(int) (width * percentageValue),height);
    }
}
