package game.map;

import game.Game;
import game.entity.Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe que representa o mapa inteiro
 */
public class TileManager {
    private final ArrayList<MapLayer> layers = new ArrayList<>(); // Todas as camadas renderizadas no momento

    private final int mapRows, mapCols; // Linhas e colunas do mapa
    private final int maxWidht, maxHeight; // Largura e altura do mapa
    private int referenceX, referenceY; // Posição de referência

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

    /**
     * Verifica se a entidade passada está colidindo com algum tile sólido da camada passada
     * @param entity endidade passada
     * @param layer camada passada
     * @return verdadeiro se há colisão e falso se não há
     */
    private boolean checkColision(Entity entity, MapLayer layer) {
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
            return (tile1 != null && tile1.isSolid()) || (tile2 != null && tile2.isSolid()) || (tile3 != null && tile3.isSolid()) || (tile4 != null && tile4.isSolid());
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
            hasColision = checkColision(entity, layers.get(i));
            i++;
        }
        return hasColision;
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

    /**
     * Retorna o tile na posição passada na primeira cadamada com o tipo especificado
     * @param row linha do tile procurado
     * @param col coluna do tile procurado
     * @param type tipo de camada a procurar
     * @return tile, caso ele seja encontrado, e null caso contrário
     */
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
