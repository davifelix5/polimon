package game.map.factory;

import game.animation.SpriteSheet;
import game.entity.PlayerAnimations;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class VintageMap implements MapFactory {

//  Gera de um mapa com aparência vintage para a implementação do look-and-fells
    private SpriteSheet vintageMapTiles;
    private BufferedImage vintagePlayerWalkSprites;
    private BufferedImage vintagePlayerSwimSprites;
    private BufferedImage vintagePlayerBikeSprites;
    private BufferedImage vintageBackgroundImage;

    public VintageMap() {
        try{
            this.vintageBackgroundImage =  ImageIO.read(new FileInputStream("src/game/res/mapas/MapaRaiaChaoVintage.png"));
            this.vintageMapTiles = new SpriteSheet("src/game/res/sprites/tileset_mapa.png", 32,32);
            this.vintagePlayerWalkSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSprites.png"));
            this.vintagePlayerSwimSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerSwim.png"));
            this.vintagePlayerBikeSprites = ImageIO.read(new FileInputStream("src/game/res/sprites/playerBike.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public SpriteSheet getMapTileSet() {
        return vintageMapTiles;
    }
    @Override
    public SpriteSheet getPlayerSpriteSheets(PlayerAnimations movement) {
        if (movement == PlayerAnimations.Swimming){
            return new SpriteSheet(vintagePlayerSwimSprites, 64, 82);
        }
        else if(movement == PlayerAnimations.Bike){
            return new SpriteSheet(vintagePlayerBikeSprites, 48, 48);
        }
        return new SpriteSheet(vintagePlayerWalkSprites, 32, 41);
    }
    @Override
    public BufferedImage getBackgroundImage() {
        return vintageBackgroundImage;
    }


}
