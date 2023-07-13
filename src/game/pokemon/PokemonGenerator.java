package game.pokemon;

import java.util.Random;

import game.Game;

public class PokemonGenerator {
    private static PokemonGenerator instance = null;
    private Random random;

    private PokemonGenerator() {
        random = new Random();
    }

    public static PokemonGenerator getInstance() {
        if (instance == null) {
            instance = new PokemonGenerator();
        }
        return instance;
    }

    public MapPokemon generatePokemon(PokemonType type, MapPokemonStrategy strategy) {
        double attempt = (double) random.nextFloat();
        if (attempt <= type.getGenProbability()) {
            PokemonID ID = type.getPokemons().get(random.nextInt(type.getPokemons().size()));
            MapPokemon pokemon;
            switch (type) {
                case Normal:
                    pokemon = new MapPokemon(35 * Game.tileSize, 28 * Game.tileSize, ID, type);
                    break;
                case Water:
                    pokemon = new MapPokemon(21 * Game.tileSize, 4 * Game.tileSize, ID, type);
                    break;
                case Steel:
                    pokemon = new MapPokemon(5 * Game.tileSize, 44 * Game.tileSize, ID, type);
                    break;
                default:
                    pokemon = null;
            }
            pokemon.setStrategy(strategy);
            return pokemon;
        }
        return null;
    }
}
