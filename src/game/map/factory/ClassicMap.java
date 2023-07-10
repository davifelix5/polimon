package game.map.factory;

import game.animation.SpriteSheet;
import game.entity.PlayerAnimations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class ClassicMap implements MapFactory{

    //  Gera o mapa clássico do jogo para a implementação do look-and-fells
    private BufferedImage classicBackgroundImage;
    private BufferedImage classicPlayerWalkSprites;
    private BufferedImage classicPlayerSwimSprites;
    private BufferedImage classicPlayerBikeSprites;
    private SpriteSheet classicMapTiles;

//    Construtor do mapa
    public ClassicMap() {
        try{
            this.classicBackgroundImage =  ImageIO.read(new FileInputStream("src/game/res/mapas/MapaRaiaChao.png"));
            this.classicMapTiles = new SpriteSheet("src/game/res/sprites/tileset_mapa.png", 32,32);
            this.classicPlayerWalkSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            this.classicPlayerSwimSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSwim.png"));
            this.classicPlayerBikeSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public SpriteSheet getMapTileSet() {
        return classicMapTiles;
    }
    @Override
    public SpriteSheet getPlayerSpriteSheets(PlayerAnimations movement) {
        if (movement == PlayerAnimations.Swimming){
            return new SpriteSheet(classicPlayerSwimSprites, 64, 82);
        }
        else if(movement == PlayerAnimations.Bike){
            return new SpriteSheet(classicPlayerBikeSprites, 48, 48);
        }
        return new SpriteSheet(classicPlayerWalkSprites, 32, 41);
    }
    @Override
    public BufferedImage getBackgroundImage() {
        return classicBackgroundImage;
    }

}
