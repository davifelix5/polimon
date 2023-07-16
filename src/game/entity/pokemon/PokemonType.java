package game.entity.pokemon;

import java.util.ArrayList;

public enum PokemonType {
    NORMAL(1),
    FIRE(1),
    WATER(1),
    ELECTRIC(1),
    GRASS(1),
    ICE(1),
    FIGHTING(1),
    POISON(1),
    GROUND(1),
    FLYING(1),
    PSYCHIC(1),
    BUG(1),
    ROCK(1),
    GHOST(1),
    DRAGON(1),
    DARK(1),
    STEEL(0.8),
    FAIRY(0.8);

    private final double genProbability;

    PokemonType(double genProbability) {
        this.genProbability = genProbability;
    }

    public double getGenProbability() {
        return this.genProbability;
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

