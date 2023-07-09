package game.map.factory;

import game.animation.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

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
    public SpriteSheet getPlayerSpriteSheets(String movement) {
        if (movement == "Swim"){
            SpriteSheet swimSpritesheet = new SpriteSheet(classicPlayerSwimSprites, 64, 82);
            return swimSpritesheet;
        }
        else if(movement == "Bike"){
            SpriteSheet bikeSpritesheet = new SpriteSheet(classicPlayerBikeSprites, 48, 48);
            return bikeSpritesheet;
        }
        SpriteSheet walkSpriteSheet = new SpriteSheet(classicPlayerWalkSprites, 32, 41);
        return walkSpriteSheet;
    }
    @Override
    public BufferedImage getBackgroundImage() {
        return classicBackgroundImage;
    }

}
