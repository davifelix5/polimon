package game.map;

import game.Game;
import game.entity.Entity;

import java.awt.*;
import java.util.ArrayList;

public class TileManager {
    private final ArrayList<MapLayer> layers = new ArrayList<>();

    private final int mapRows, mapCols;
    private final int maxWidht, maxHeight;
    private int referenceX, referenceY;

    public TileManager(int mapRows, int mapCols) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        this.maxWidht = this.mapCols*Game.tileSize;
        this.maxHeight = this.mapRows*Game.tileSize;
    }

    public void addLayer(MapLayer layer) {
        layer.setRows(mapRows);
        layer.setCols(mapCols);
        layer.parseTileMap();
        this.layers.add(layer);
    }

    private boolean checkColision(Entity entity, MapLayer layer) {
        int left = entity.getWorldX() + entity.getBounds().x;
        int right = left + entity.getBounds().width;
        int up = entity.getWorldY() + entity.getBounds().y;
        int down = up + entity.getBounds().width;

        int nextColRight = (right + entity.getVelX()) / Game.tileSize;
        int nextColLeft = (left + entity.getVelX()) / Game.tileSize;
        int nextRowDown = (up + entity.getVelY()) / Game.tileSize;
        int nextRowUp = (down + entity.getVelY()) / Game.tileSize;

        if (layer.getTileMap() != null) {
            Tile tile1 = layer.getTileMap()[nextRowDown][nextColRight];
            Tile tile2 = layer.getTileMap()[nextRowDown][nextColLeft];
            Tile tile3 = layer.getTileMap()[nextRowUp][nextColRight];
            Tile tile4 = layer.getTileMap()[nextRowUp][nextColLeft];
            return (tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()) || (tile4 != null && tile4.isSolid());
        }

        return false;

    }

    public boolean colides(Entity entity) {
        boolean hasColision = false;
        int i = 0;
        while (!hasColision && i < layers.size()) {
            hasColision = checkColision(entity, layers.get(i));
            i++;
        }
        return hasColision;
    }

    public void renderLayer(int layerIndex, Graphics g) {
        this.layers.get(layerIndex).render(g, referenceX, referenceY);
    }

    public void renderRange(int layerStartIndex, int layerEndIndex, Graphics g) {
        for (int i = layerStartIndex; i <= layerEndIndex; i ++) {
            renderLayer(i, g);
        }
    }

    public Tile searchLayers(int row, int col, LayerType type) {
        try {
            for (MapLayer layer: layers) {
                if (layer.getType() == type) {
                    return layer.getTileMap()[row][col];
                }
            }
        } catch (RuntimeException e) {
            return null;
        }

        return null;
    }

    public void setReferenceX(int referenceX) {
        this.referenceX = referenceX;
    }

    public void setReferenceY(int referenceY) {
        this.referenceY = referenceY;
    }

    public int getReferenceX() {
        return referenceX;
    }

    public int getReferenceY() {
        return referenceY;
    }

    public int getMaxWidht() {
        return maxWidht;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
