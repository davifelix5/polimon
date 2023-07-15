package game.map;

import game.entity.pokemon.PokemonType;

import java.awt.*;

public class PokemonArea {
    private int pokemonCount;
    private final PokemonType type;
    private final Rectangle appearanceArea;
    private final int maximumPokemons;

    public void addPokemon() {
        this.pokemonCount++;
    }

    public PokemonArea(PokemonType type, Rectangle appearanceArea, int maximumPokemons) {
        this.type = type;
        this.appearanceArea = appearanceArea;
        this.maximumPokemons = maximumPokemons;
    }

    public int getPokemonCount() {
        return pokemonCount;
    }

    public PokemonType getType() {
        return type;
    }

    public Rectangle getAppearanceArea() {
        return appearanceArea;
    }

    public int getMaximumPokemons() {
        return maximumPokemons;
    }
}
