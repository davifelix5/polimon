package game.map;

import game.Game;
import game.animation.SpriteSheet;
import game.pokemon.PokemonType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapLayer {
    private BufferedReader tilemapFile; // Arquivo csv com a posição dos tiles
    private final ArrayList<ArrayList<Integer>> tileNumbers = new ArrayList<>(); // matriz com os números do tilemapFile
    private SpriteSheet spritesheet; // Tilset correspondente
    private boolean solid; // indica se os tiles da camada serão colidíveis
    private int rows, cols;
    private Tile[][] tileMap; // tilemap com todos os tiles da camada em suas respectivas posiçõe
    protected boolean interactable;
    protected boolean pokemonLayer;
    protected PokemonType pokemonType;

    // Construtores recebendo String com o caminho do tilemap
    public MapLayer(String tilemapPath, SpriteSheet spriteSheet, boolean solid) {
        try {
            this.tilemapFile = new BufferedReader(new FileReader(tilemapPath));
            this.spritesheet = spriteSheet;
            this.solid = solid;
            this.interactable = false;
            this.pokemonLayer = false;
            this.pokemonType = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Percorre o arquivo csv com os tiles e forma uma matriz de inteiros correspondendo ao número dos tiles
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

    public boolean isInteractable() {
        return interactable;
    }

    public boolean isPokemonLayer() {
        return pokemonLayer;
    }

    public PokemonType getPokemonType() {
        return this.pokemonType;
    }

}
