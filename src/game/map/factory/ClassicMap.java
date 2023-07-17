package game.map.factory;

import game.entity.animation.SpriteSheet;
import game.entity.player.PlayerAnimations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassicMap implements MapFactory{

    //  Gera o mapa clássico do jogo para a implementação do look-and-fells
    private BufferedImage playerWalkSprites;
    private BufferedImage playerSwimSprites;
    private BufferedImage playerBikeSprites;
    private SpriteSheet mapTiles, npcSprites;

//    Construtor do mapa
    public ClassicMap() {
        try{
            this.playerWalkSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            this.playerSwimSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSwim.png"));
            this.playerBikeSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
            this.mapTiles = new SpriteSheet("src/game/res/sprites/tileset_mapa.png", 32,32);
            this.npcSprites = new SpriteSheet("src/game/res/sprites/npc_sprites.png", 51, 54);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public SpriteSheet getMapTileSet() {
        return mapTiles;
    }
    @Override
    public SpriteSheet getPlayerSpriteSheets(PlayerAnimations movement) {
        if (movement == PlayerAnimations.Swimming){
            return new SpriteSheet(playerSwimSprites, 64, 82);
        }
        else if(movement == PlayerAnimations.Bike){
            return new SpriteSheet(playerBikeSprites, 48, 48);
        }
        return new SpriteSheet(playerWalkSprites, 32, 41);
    }

    @Override
    public SpriteSheet getNpcSpritesheet() {
        return npcSprites;
    }


    @Override
    public MapFactory copy() {
        return this;
    }

}
