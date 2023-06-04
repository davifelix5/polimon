package game.map;

import game.Game;
import game.animation.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class MapLayer {
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final SpriteSheet sprites;
    private final BufferedReader tilemapFile;
    private final int[][] spriteMap;
    private final ArrayList<ArrayList<Integer>> tileNumbers = new ArrayList<>();

    public MapLayer(SpriteSheet sprites, BufferedReader tilemapFile, int[][] spriteMap) {
        this.sprites = sprites;
        this.tilemapFile = tilemapFile;
        this.spriteMap = spriteMap;
        loadSprite();
        parseTileMap();
    }

    public void render(Graphics g) {
        int x, y = 0;
        for (int i = 0; i < Game.maxScreenRow; i++) {
            x = 0;
            for (int j = 0; j < Game.maxScreenCol; j ++) {
                int tileNumber = tileNumbers.get(i).get(j);
                if (tileNumber >= 0) {
                    tiles.get(tileNumber).draw(g, x, y);
                }
                x += Game.tileSize;
            }
            y += Game.tileSize;
        }
    }

    public void loadSprite() {
        for (int[] ints : spriteMap) {
            BufferedImage tileImage = sprites.getSprite(ints[0], ints[1]);
            tiles.add(new Tile(Game.tileSize, Game.tileSize, true, tileImage));
        }
    }

    public void parseTileMap() {
        try {
            for (int i = 0; i < Game.maxScreenRow; i++) {
                String[] numbers = tilemapFile.readLine().split(",");
                ArrayList<Integer> line = new ArrayList<>();
                for (int j = 0; j < Game.maxScreenCol; j++) {
                    int tileNumber = Integer.parseInt(numbers[j]);
                    line.add(tileNumber);
                }
                tileNumbers.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
