package game.map.factory;

import game.entity.animation.SpriteSheet;
import game.entity.player.PlayerAnimations;

/***
 * ‘Interface’ de criação de mapas para a implementação do look-and-fells
 * */
public interface MapFactory {
//
    SpriteSheet getMapTileSet();
    SpriteSheet getPlayerSpriteSheets(PlayerAnimations movement);
    SpriteSheet getNpcSpritesheet();
    MapFactory copy();
}
