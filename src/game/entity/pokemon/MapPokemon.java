package game.entity.pokemon;

import game.entity.Entity;
import game.Game;

import java.awt.Rectangle;
import java.awt.Graphics;

public class MapPokemon extends Entity {

    private PokemonID ID;
    private PokemonType type;

    private int movingRate = 1;
    private boolean colliding;

    private MapPokemonStrategy strategy;
    
    public MapPokemon(int x, int y, PokemonID ID, PokemonType type) {
        super(x, y);
        this.velX = 0;
        this.velY = 0;
        this.ID = ID;
        this.type = type;
    }

    @Override
    public void tick() {
        if (!this.colliding) {
            this.setWorldX(Game.clamp(this.getWorldX() + this.getVelX(), this.type.getMinX(), this.type.getMaxX()));
            this.setWorldY(Game.clamp(this.getWorldY() + this.getVelY(), this.type.getMinY(), this.type.getMaxY()));
        }

        this.strategy.setAction(this);
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(10, 20, 10, 10);
    }

    public void setStrategy(MapPokemonStrategy strategy) {
        this.strategy = strategy;
    }

    public int getMovingRate() {
        return this.movingRate;
    }

    public String getName() {
        return this.ID.getName();
    }

}
