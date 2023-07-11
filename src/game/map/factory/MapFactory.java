package game.map.factory;

import game.animation.SpriteSheet;
import game.entity.PlayerAnimations;

import java.awt.image.BufferedImage;

public interface MapFactory {
//    interface de criação de mapas para a implementação do look-and-fells
    SpriteSheet getMapTileSet();
    SpriteSheet getPlayerSpriteSheets(PlayerAnimations movement);
    BufferedImage getBackgroundImage();
}
