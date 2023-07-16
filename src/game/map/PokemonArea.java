package game.map;

import game.entity.pokemon.PokemonType;

import java.awt.*;

/**
 * Classe que determina a área em que o pokemons de determinados tipos irão aparecer
 */
public class PokemonArea {
    private int pokemonCount; // quantidade de pokemons na área
    private final PokemonType[] types; // tipos de pokemon que podem aparecer na áera
    private final Rectangle appearanceArea;
    private final int maximumPokemons; // quantidade máxima de pokemons na área

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
