package game.pokemon;

import game.entity.Entity;
import game.Game;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.Random;

public class MapPokemon extends Entity {

    private PokemonID ID;
    private PokemonType type;

    private int movingRate = 1;
    private boolean colliding;

    private int actionLockCounter;
    private int currentDirection;
    private Random random;
    
    public MapPokemon(int x, int y, PokemonID ID, PokemonType type) {
        super(x, y);
        this.velX = 0;
        this.velY = 0;
        this.ID = ID;
        this.type = type;
        this.actionLockCounter = 0;
        this.random = new Random();
    }

    @Override
    public void tick() {
        if (!this.colliding) {
            this.setWorldX(Game.clamp(this.getWorldX() + this.getVelX(), this.type.getMinX(), this.type.getMaxX()));
            this.setWorldY(Game.clamp(this.getWorldY() + this.getVelY(), this.type.getMinY(), this.type.getMaxY()));
        }

        this.actionLockCounter++;

        if (this.actionLockCounter == 150) {
            this.currentDirection = random.nextInt(4);
            switch (currentDirection) {
                case 0: // Mover para cima
                    this.setVelX(0);
                    this.setVelY(-this.movingRate);
                    break;
                case 1: // Mover para baixo
                    this.setVelX(0);
                    this.setVelY(this.movingRate);
                    break;
                case 2: // Mover para a esquerda
                    this.setVelX(-this.movingRate);
                    this.setVelY(0);
                    break;
                case 3: // Mover para a direita;
                    this.setVelX(this.movingRate);
                    this.setVelY(0);
                    break;
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 10, 10);
    }

    public String getName() {
        return this.ID.getName();
    }

}
