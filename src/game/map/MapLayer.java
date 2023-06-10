package game.map;

import game.Game;
import game.animation.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.util.ArrayList;

public class MapLayer {
    private final BufferedReader tilemapFile; // Arquivo csv com a posição dos tiles
    private final ArrayList<ArrayList<Integer>> tileNumbers = new ArrayList<>(); // matriz com os números do tilemapFile
    private final SpriteSheet spritesheet; // Tilset correspondente
    private final boolean solid; // indica se os tiles da camada serão colidíveis
    private int rows, cols;
    private Tile[][] tileMap; // tilemap com todos os tiles da camada em suas respectivas posições
    private LayerType type;

    public MapLayer(BufferedReader tilemapFile, SpriteSheet spriteSheet, boolean solid) {
        this.tilemapFile = tilemapFile;
        this.spritesheet = spriteSheet;
        this.solid = solid;
    }

    public MapLayer(BufferedReader tilemapFile, SpriteSheet spriteSheet) {
        this.tilemapFile = tilemapFile;
        this.spritesheet = spriteSheet;
        this.solid = false;
    }

    public MapLayer(BufferedReader tilemapFile, SpriteSheet spriteSheet, boolean solid, LayerType type) {
        this.tilemapFile = tilemapFile;
        this.spritesheet = spriteSheet;
        this.solid = solid;
        this.type = type;
    }

    /**
     * Renficação os tiles do em suas devidas posições na tela a partir de um ponto de referência
     * @param g gráficos sendo utilizados no jogo
     * @param referenceX posição de referência para renderização em x
     * @param referenceY posição de referência para renderização em y
     */
    public void render(Graphics g, int referenceX, int referenceY) {
        int x, y = 0;
        tileMap = new Tile[rows][cols];
        for (int i = 0; i < rows; i++) {
            x = 0;
            for (int j = 0; j < cols; j ++) {
                int tileNumber = tileNumbers.get(i).get(j);
                if (tileNumber >= 0) {
                    BufferedImage tileImage = spritesheet.getSprite(tileNumber);
                    Tile currentTile = new Tile(spritesheet.spriteWidth, spritesheet.spriteHeigth, tileImage, solid);
                    currentTile.draw(g, x - referenceX, y - referenceY);
                    tileMap[i][j] = currentTile;
                }
                x += Game.tileSize;
            }
            y += Game.tileSize;
        }
    }

    /**
     * Percorre o arquivo csv com os tiles e forma uma matriz de inteiros correspondendo ao números dos tiles
     */
    public void parseTileMap() {
        try {
            for (int i = 0; i < rows; i++) {
                String[] numbers = tilemapFile.readLine().split(",");
                ArrayList<Integer> line = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
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

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public LayerType getType() {
        return type;
    }
}
