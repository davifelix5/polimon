package game.map.factory;

import game.animation.SpriteSheet;

import java.awt.image.BufferedImage;

public class VintageMap implements MapFactory {

//  Gera de um mapa com aparência vintage para a implementação do look-and-fells
    private SpriteSheet vintageMapTiles;
    private SpriteSheet vintagePlayer;
    private BufferedImage vintageBackgroundImage;

    public VintageMap(SpriteSheet vintageMapTiles, SpriteSheet vintagePlayer, BufferedImage vintageBackgroundImage) {
        this.vintageMapTiles = vintageMapTiles;
        this.vintagePlayer = vintagePlayer;
        this.vintageBackgroundImage = vintageBackgroundImage;
    }

    @Override
    public SpriteSheet getMapTileSet() {
        return vintageMapTiles;
    }
    @Override
    public SpriteSheet getPlayerSpriteSheets(String movement) {
        return vintagePlayer;
    }
    @Override
    public BufferedImage getBackgroundImage() {
        return vintageBackgroundImage;
    }

}
