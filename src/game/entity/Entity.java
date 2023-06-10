package game.entity;

import game.Game;

import java.awt.*;

public abstract class Entity {

    protected int worldX, worldY;
    protected int velX, velY;

    public abstract void tick();

    public abstract void render(Graphics g);

    public Entity(int x, int y) {
        this.worldX = x;
        this.worldY = y;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
    public abstract Rectangle getBounds();

    public int getWorldRow() {
        return this.worldY / Game.tileSize;
    }

    public int getWorldCol() {
        return this.worldX / Game.tileSize;
    }
}
