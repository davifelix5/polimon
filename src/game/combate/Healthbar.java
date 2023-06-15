package game.combate;

import java.awt.*;

public class Healthbar {
    private final int posX, posY, width, height, maxHP;
    public int currentHP;

    public Healthbar(int posX, int posY, int width, int height, int maxHP) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
    }

    public int getMaxHP() {
        return maxHP;
    }
    public int getCurrentHP() {
        return currentHP;
    }
    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }
    public void render(Graphics g){
        int percentageHP = currentHP/maxHP; //ta concatenando será que é problema?
        g.setColor(Color.green);
        g.fillRect(posX,posY,width * percentageHP,height);
    }
}
