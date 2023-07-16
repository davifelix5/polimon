package game.entity.pokemon;

import game.entity.Entity;
import game.Game;
import game.map.PokemonArea;
import game.map.TileManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pokemon extends Entity {

    private final PokemonID ID;
    private final BufferedImage pokeImage;
    private final int movingRate;
    private final PokemonArea pokeArea;

    private MapPokemonStrategy strategy;
    
    public Pokemon(int x, int y, PokemonID ID, BufferedImage pokeImage, TileManager tm, PokemonArea pokeArea) {
        super(x, y);
        this.velX = 0;
        this.velY = 0;
        this.ID = ID;
        this.pokeImage = pokeImage;
        this.tileManager = tm;
        this.movingRate = 1;
        this.pokeArea = pokeArea;
    }

    @Override
    public void tick() {
        Rectangle moveArea = pokeArea.getAppearanceArea();
        int minX = moveArea.x, minY = moveArea.y;
        int maxX = moveArea.x + moveArea.width, maxY = moveArea.y + moveArea.height;
        if (!this.tileManager.colides(this)) {
            this.setWorldX(Game.clamp(this.getWorldX() + this.getVelX(), minX, maxX - 64));
            this.setWorldY(Game.clamp(this.getWorldY() + this.getVelY(), minY, maxY - 64));
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
        return new Rectangle(10, 15, 30, 30);
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

    public BufferedImage getPokeImage() {
        return pokeImage;
    }

    public PokemonArea getPokeArea() {
        return pokeArea;
    }

    public PokemonID getID() {
        return ID;
    }
}
