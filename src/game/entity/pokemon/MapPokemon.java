package game.entity.pokemon;

import game.entity.Entity;
import game.Game;
import game.map.TileManager;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MapPokemon extends Entity {

    private final PokemonID ID;
    private final PokemonType type;
    private final BufferedImage pokeImage;
    private int movingRate = 1;
    private boolean colliding;

    private MapPokemonStrategy strategy;
    
    public MapPokemon(int x, int y, PokemonID ID, PokemonType type, BufferedImage pokeImage, TileManager tm) {
        super(x, y);
        this.velX = 0;
        this.velY = 0;
        this.ID = ID;
        this.type = type;
        this.pokeImage = pokeImage;
        this.tileManager = tm;
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
        int posX = getWorldX() - tileManager.getReferenceX(), posY = getWorldY() - tileManager.getReferenceY();
        g.drawImage(pokeImage, posX, posY, 64, 64, null);
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
