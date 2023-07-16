package game.map;

import game.entity.pokemon.PokemonType;

import java.awt.*;

public class PokemonArea {
    private int pokemonCount;
    private final PokemonType[] types;
    private final Rectangle appearanceArea;
    private final int maximumPokemons;

    public void addPokemon() {
        this.pokemonCount++;
    }

    public void removePokemon() {
        this.pokemonCount--;
    }

    public PokemonArea(PokemonType[] type, Rectangle area, int maximumPokemons) {
        this.types = type;
        this.appearanceArea = area;
        this.maximumPokemons = maximumPokemons;
    }

    public int getPokemonCount() {
        return pokemonCount;
    }

    public PokemonType[] getTypes() {
        return types;
    }

    public Rectangle getAppearanceArea() {
        return appearanceArea;
    }

    public int getMaximumPokemons() {
        return maximumPokemons;
    }

}
