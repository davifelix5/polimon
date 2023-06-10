package game.entity;

import game.Game;

import java.awt.*;

public abstract class Entity {

    protected int worldX, worldY;
    protected int velX, velY;

    /***
     * Atualiza a entidade
     */
    public abstract void tick();

    /***
     * Renderiza a entidade na tela
     * @param g gráficos sendo utilizados pelo jogo
     */
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

    /***
     * Define os limites de colisão do jogador
     * @return um objeto do tipo Rectangle com posição relativa ao jogador que indica seus limites de colisão
     */
    public abstract Rectangle getBounds();

    /***
     * Retorna a linha na matriz do tilemap em que a entidade está localizada
     * @return int
     */
    public int getWorldRow() {
        return this.worldY / Game.tileSize;
    }

    /***
     * Retorna a linha na matriz do tilemap em que a entidade está localizada
     * @return int
     */
    public int getWorldCol() {
        return this.worldX / Game.tileSize;
    }
}
