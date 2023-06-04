package game.map;

import game.GamePanel;
import game.animation.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class Map {
    private final ArrayList<Tile> tiles = new ArrayList<>();
    private final SpriteSheet sprites;
    private final BufferedReader tilemapFile;
    private final int[][] spriteMap;
    private final ArrayList<ArrayList<Integer>> tileNumbers = new ArrayList<>();

    public Map(SpriteSheet sprites, BufferedReader tilemapFile, int[][] spriteMap) {
        this.sprites = sprites;
        this.tilemapFile = tilemapFile;
        this.spriteMap = spriteMap;
        loadSprite();
        parseTileMap();
    }

    public void render(Graphics g) {
        int x, y = 0;
        for (int i = 0; i < GamePanel.maxScreenRow; i++) {
            x = 0;
            for (int j = 0; j < GamePanel.maxScreenCol; j ++) {
                int tileNumber = tileNumbers.get(i).get(j);
                if (tileNumber >= 0) {
                    tiles.get(tileNumber).draw(g, x, y);
                }
                x += GamePanel.originalTileSize;
            }
            y += GamePanel.originalTileSize;
        }
    }

    public void loadSprite() {
        for (int[] ints : spriteMap) {
            BufferedImage tileImage = sprites.getSprite(ints[0], ints[1]);
            tiles.add(new Tile(GamePanel.originalTileSize, GamePanel.originalTileSize, true, tileImage));
        }
    }

    public void parseTileMap() {
        try {
            for (int i = 0; i < GamePanel.maxScreenRow; i++) {
                String[] numbers = tilemapFile.readLine().split(",");
                ArrayList<Integer> line = new ArrayList<>();
                for (int j = 0; j < GamePanel.maxScreenCol; j++) {
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
