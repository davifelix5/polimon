package game.entity.player;

import game.Game;
import game.entity.pokemon.Pokemon;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pokedex {
    private final ArrayList<Pokemon> pokemons = new ArrayList<>();

    private final int padding = 5;
    private final int itemsPerRow = 5;

    private final int itemSize = 3 * Game.tileSize;

    private final int frameSize = itemsPerRow * (itemSize + padding) + padding;
    private final int frameX = (Game.width - frameSize) / 2;
    private final int frameY = (Game.height - frameSize) / 2;

    public void render(Graphics g) {
        drawSubWindow(g);
        int x = frameX + padding;
        int y = frameY + padding;
        int items = 0;
        for (Map.Entry<Pokemon, Integer> entry: groupPokemons().entrySet()) {
            renderItem(g, x, y, entry.getKey(), entry.getValue());
            x += itemSize + padding;
            items ++;
            if (items == itemsPerRow) {
                y += padding + itemSize;
                x = frameX + padding;
            }
        }
    }

    private void renderItem(Graphics g, int x, int y, Pokemon pokemon, int pokeCount) {
        g.setFont(new Font("arial", Font.PLAIN, 15));
        g.setColor(Color.BLACK);
        g.drawRoundRect(x, y, itemSize, itemSize, 15, 15);
        g.setColor(Color.white);

        String pokemonAmount = pokeCount + "x";
        g.drawString(pokemon.getName(), x + 15, y + 15);
        g.drawImage(pokemon.getPokeImage(), x + 15, y + 15, itemSize - 20,itemSize - 20, null);
        g.drawString(pokemonAmount, x + padding, y+itemSize - padding);
    }

    private void drawSubWindow(Graphics g) {
        g.setColor(new Color(48, 38, 18));
        g.fillRoundRect(frameX, frameY, frameSize, frameSize, 30, 30);
    }

    public Map<Pokemon, Integer> groupPokemons() {
        Map<Pokemon, Integer> finalMap = new HashMap<>();
        Map<Integer, List<Pokemon>> pokemonMap = pokemons.stream().collect(
                Collectors.groupingBy(poke -> poke.getID().getValue())
        );

        pokemonMap.forEach((key, value) -> finalMap.put(value.get(0), value.size()));

        return finalMap;
    }

    public void addPokemon(Pokemon poke) {
        this.pokemons.add(poke);
    }

    public ArrayList<Pokemon> getPokemons() {
        return pokemons;
    }

    public void tick() {

    }
}
