package game.map.factory;

import game.entity.animation.SpriteSheet;
import game.entity.player.PlayerAnimations;

import java.awt.image.BufferedImage;

/***
 * ‘Interface’ de criação de mapas para a implementação do look-and-fells
 * */
public interface MapFactory {
//
    SpriteSheet getMapTileSet();
    SpriteSheet getPlayerSpriteSheets(PlayerAnimations movement);
    BufferedImage getBackgroundImage();
    SpriteSheet getNpcSpritesheet();
    BufferedImage getBienioBackground();
    MapFactory copy();
}
