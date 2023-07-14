package game.entity.pokemon;

import game.Game;

import java.util.ArrayList;

public enum PokemonType {
    NORMAL(1, 35, 41, 28, 30),
    FIRE(1, 35, 41, 28, 30),
    WATER(1, 21, 27, 4, 6),
    ELECTRIC(1, 35, 41, 28, 30),
    GRASS(1, 35, 41, 28, 30),
    ICE(1, 35, 41, 28, 30),
    FIGHTING(1, 35, 41, 28, 30),
    POISON(1, 35, 41, 28, 30),
    GROUND(1, 35, 41, 28, 30),
    FLYING(1, 35, 41, 28, 30),
    PSYCHIC(1, 35, 41, 28, 30),
    BUG(1, 35, 41, 28, 30),
    ROCK(1, 35, 41, 28, 30),
    GHOST(1, 35, 41, 28, 30),
    DRAGON(1, 35, 41, 28, 30),
    DARK(1, 35, 41, 28, 30),
    STEEL(0.8, 5, 9, 44, 46),
    FAIRY(0.8, 5, 9, 44, 46);

    private final double genProbability;
    private final int minX, maxX, minY, maxY;

    PokemonType(double genProbability, int minX, int maxX, int minY, int maxY) {
        this.genProbability = genProbability;
        this.minX = minX * Game.tileSize;
        this.maxX = maxX * Game.tileSize;
        this.minY = minY * Game.tileSize;
        this.maxY = maxY * Game.tileSize;
    }

    public double getGenProbability() {
        return this.genProbability;
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

    public ArrayList<PokemonID> getPokemons() {
        ArrayList<PokemonID> pokemons = new ArrayList<>();

        for (PokemonID poke: PokemonID.values()) {
            for (PokemonType type: poke.getTypes()) {
                if (type == this)
                    pokemons.add(poke);
            }
        }

        return pokemons;
    }

}

