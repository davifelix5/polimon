package game.map;

import game.Game;
import game.animation.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class MapLayer {
    private final BufferedReader tilemapFile;
    private final ArrayList<ArrayList<Integer>> tileNumbers = new ArrayList<>();
    private final SpriteSheet spritesheet;
    private final boolean solid;
    private final Tile[][] tileMap = new Tile[Game.maxScreenRow][Game.maxScreenCol];

    public MapLayer(BufferedReader tilemapFile, SpriteSheet spriteSheet, boolean solid) {
        this.tilemapFile = tilemapFile;
        this.spritesheet = spriteSheet;
        this.solid = solid;
        parseTileMap();
    }

    public void render(Graphics g) {
        int x, y = 0;
        for (int i = 0; i < Game.maxScreenRow; i++) {
            x = 0;
            for (int j = 0; j < Game.maxScreenCol; j ++) {
                int tileNumber = tileNumbers.get(i).get(j);
                if (tileNumber >= 0) {
                    BufferedImage tileImage = spritesheet.getSprite(tileNumber);
                    Tile currentTile = new Tile(spritesheet.spriteWidth, spritesheet.spriteHeigth, tileImage, solid);
                    currentTile.draw(g, x, y);
                    tileMap[i][j] = currentTile;
                }
                x += Game.tileSize;
            }
            y += Game.tileSize;
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

    public Tile[][] getTileMap() {
        return tileMap;
    }
}
