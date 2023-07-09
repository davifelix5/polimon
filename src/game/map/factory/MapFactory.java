package game.map.factory;

import game.animation.SpriteSheet;

import java.awt.image.BufferedImage;

public interface MapFactory {
//    interface de criação de mapas para a implementação ddo look-and-fells
    SpriteSheet getMapTileSet();
    SpriteSheet getPlayerSpriteSheets(String movement);
    BufferedImage getBackgroundImage();
}
