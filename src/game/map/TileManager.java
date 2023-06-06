package game.map;

import game.Game;
import game.entity.Entity;

import java.util.ArrayList;

public class TileManager {
    private final ArrayList<MapLayer> layers = new ArrayList<>();

    public void addLayer(MapLayer layer) {
        this.layers.add(layer);
    }

    private boolean checkColision(Entity entity, MapLayer layer) {
        int left = entity.getX() + entity.getBounds().x;
        int right = left + entity.getBounds().width;
        int up = entity.getY() + entity.getBounds().y;
        int down = up + entity.getBounds().width;

        int nextColRight = (right + entity.getVelX()) / Game.tileSize;
        int nextColLeft = (left + entity.getVelX()) / Game.tileSize;
        int nextRowDown = (up + entity.getVelY()) / Game.tileSize;
        int nextRowUp = (down + entity.getVelY()) / Game.tileSize;

        Tile tile1 = layer.getTileMap()[nextRowDown][nextColRight];
        Tile tile2 = layer.getTileMap()[nextRowDown][nextColLeft];
        Tile tile3 = layer.getTileMap()[nextRowUp][nextColRight];
        Tile tile4 = layer.getTileMap()[nextRowUp][nextColLeft];

        return (tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()) || (tile4 != null && tile4.isSolid());

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

}
