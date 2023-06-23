package game.map;

import game.Game;
import game.entity.Entity;
import game.map.interactions.InteractableLayer;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que representa o mapa inteiro
 */
public class TileManager {
    private final ArrayList<MapLayer> layers = new ArrayList<>(); // Todas as camadas renderizadas no momento

    private final int mapRows, mapCols; // Linhas e colunas do mapa
    private final int maxWidth, maxHeight; // Largura e altura do mapa
    private int referenceX, referenceY; // Posição de referência



    public TileManager(int mapRows, int mapCols) {
        this.mapRows = mapRows;
        this.mapCols = mapCols;
        this.maxWidth = this.mapCols*Game.tileSize;
        this.maxHeight = this.mapRows*Game.tileSize;
    }

    public void addLayer(MapLayer layer) {
        layer.setRows(mapRows);
        layer.setCols(mapCols);
        layer.parseTileMap();
        this.layers.add(layer);
    }

    /**
     * Verifica se a entidade passada está colidindo com algum tile sólido da camada passada
     * @param entity entidade passada
     * @param layer camada passada
     * @return verdadeiro se há colisão e falso se não há
     */
    private boolean checkCollision(Entity entity, MapLayer layer) {
        // Pega os limites de colisão da entidade
        int left = entity.getWorldX() + entity.getBounds().x;
        int right = left + entity.getBounds().width;
        int up = entity.getWorldY() + entity.getBounds().y;
        int down = up + entity.getBounds().width;

        // Pega as próximas posições que os limites acima ocuparão
        int nextColRight = (right + entity.getVelX()) / Game.tileSize;
        int nextColLeft = (left + entity.getVelX()) / Game.tileSize;
        int nextRowDown = (up + entity.getVelY()) / Game.tileSize;
        int nextRowUp = (down + entity.getVelY()) / Game.tileSize;

        // Se há intersecção entre os tiles e os próximos limites da entidade, há colisão
        if (layer.getTileMap() != null) {
            Tile tile1 = layer.getTileMap()[nextRowDown][nextColRight];
            Tile tile2 = layer.getTileMap()[nextRowDown][nextColLeft];
            Tile tile3 = layer.getTileMap()[nextRowUp][nextColRight];
            Tile tile4 = layer.getTileMap()[nextRowUp][nextColLeft];
            return ((tile1 != null && tile1.isSolid()) ||
                    (tile2 != null && tile2.isSolid()) ||
                    (tile3 != null && tile3.isSolid()) ||
                    (tile4 != null && tile4.isSolid()));
        }

        return false;

    }

    /**
     * Percorre todas camadas para chegar colisão para a entidade entity
     * @param entity entidade sendo verificada
     * @return verdadeiro se há colisão em alguma camada e falso se não há
     */
    public boolean colides(Entity entity) {
        boolean hasColision = false;
        int i = 0;
        while (!hasColision && i < layers.size()) {
            hasColision = checkCollision(entity, layers.get(i));
            i++;
        }
        return hasColision;
    }

    public void interacts() {
        for (MapLayer l: layers) {
            if (!l.isInteractable() || l.getTileMap() == null)
                continue;

            Entity entity = ((InteractableLayer) l).getElement();
            int left = entity.getWorldX() + entity.getBounds().x;
            int right = left + entity.getBounds().width;
            int up = entity.getWorldY() + entity.getBounds().y;
            int down = up + entity.getBounds().width;

            int leftCol = left / Game.tileSize;
            int rightCol = right / Game.tileSize;
            int downRow = down / Game.tileSize;
            int upRow = up / Game.tileSize;

            Tile tile1 = l.getTileMap()[upRow][rightCol];
            Tile tile2 = l.getTileMap()[upRow][leftCol];
            Tile tile3 = l.getTileMap()[downRow][rightCol];
            Tile tile4 = l.getTileMap()[downRow][leftCol];

            if (tile1 != null || tile2 != null || tile3 != null || tile4 != null)
                ((InteractableLayer) l).handleInteraction();
        }
    }


    /**
     * Renderiza uma camada do tilemap
     * @param layerIndex índice da camada no vetor de camadas
     * @param g gráficos sendo utilizados no jogo
     */
    public void renderLayer(int layerIndex, Graphics g) {
        this.layers.get(layerIndex).render(g, referenceX, referenceY);
    }

    /**
     * Rederiza várias camadas em sequência com apenas um comando
     * @param layerStartIndex índice da primeira camada a ser renderizada
     * @param layerEndIndex índica da última camada a ser renderizada (intervalo fechado)
     * @param g gráficos usados no jogo
     */
    public void renderRange(int layerStartIndex, int layerEndIndex, Graphics g) {
        for (int i = layerStartIndex; i <= layerEndIndex; i ++) {
            renderLayer(i, g);
        }
    }

    public void renderRange(int layerStartIndex, Graphics g) {
        for (int i = layerStartIndex; i < layers.size(); i ++) {
            renderLayer(i, g);
        }
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

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}
