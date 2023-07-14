package game.entity.pokemon;

import java.util.ArrayList;
import java.util.Arrays;

import game.Game;

public enum PokemonType {
    Normal(1, new ArrayList<PokemonID>(Arrays.asList(PokemonID.Abra, PokemonID.Cascoon)), 35, 41, 28, 30),
    Fire(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Water(1, new ArrayList<PokemonID>(Arrays.asList(PokemonID.Grovyle)), 21, 27, 4, 6),
    Electric(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Grass(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Ice(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Fighting(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Poison(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Ground(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    FLying(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Psychic(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Bug(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Rock(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Ghost(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Dragon(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Dark(1, new ArrayList<PokemonID>(), 35, 41, 28, 30),
    Steel(0.8, new ArrayList<PokemonID>(Arrays.asList(PokemonID.Silcoon)), 5, 9, 44, 46);

    private final double genProbability;
    private final ArrayList<PokemonID> pokemonsList;
    private final int minX, maxX, minY, maxY;

    PokemonType(double genProbability, ArrayList<PokemonID> pokemonsList, int minX, int maxX, int minY, int maxY) {
        this.genProbability = genProbability;
        this.pokemonsList = pokemonsList;
        this.minX = minX * Game.tileSize;
        this.maxX = maxX * Game.tileSize;
        this.minY = minY * Game.tileSize;
        this.maxY = maxY * Game.tileSize;
    }

    public double getGenProbability() {
        return this.genProbability;
    }

    public ArrayList<PokemonID> getPokemons() {
        return this.pokemonsList;
    }

    public int getMinX() {
        return this.minX;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }
}
