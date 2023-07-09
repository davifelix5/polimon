package game.map.factory;

import game.animation.SpriteSheet;

import java.awt.image.BufferedImage;

public class NightMap implements MapFactory {

    //  Gera de um mapa com aparência noturna para a implementação do look-and-fells
    private SpriteSheet nightMapTiles;
    private SpriteSheet nightPlayerSprites;
    private BufferedImage nightBackgroundImage;

    public NightMap(SpriteSheet nightMapTiles, SpriteSheet nightPlayerSprites, BufferedImage nightBackgroundImage) {
        this.nightMapTiles = nightMapTiles;
        this.nightPlayerSprites = nightPlayerSprites;
        this.nightBackgroundImage = nightBackgroundImage;
    }

    @Override
    public SpriteSheet getMapTileSet() {
        return nightMapTiles;
    }
    @Override
    public SpriteSheet getPlayerSpriteSheets(String movement) {
        return nightPlayerSprites;
    }
    @Override
    public BufferedImage getBackgroundImage(){
        return nightBackgroundImage;
    }
}
