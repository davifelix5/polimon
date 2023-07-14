package game.entity.pokemon;

import java.util.Random;

import game.Game;
import game.entity.animation.SpriteSheet;
import game.map.TileManager;

public class PokemonGenerator {

    private MapPokemonStrategy strategy;
    private static PokemonGenerator instance = null;
    private final Random random;

    private static SpriteSheet pokeSprites;

    private PokemonGenerator() {
        random = new Random();
    }

    public static PokemonGenerator getInstance() {
        if (instance == null) {
            pokeSprites = new SpriteSheet("src/game/res/sprites/pokemon/pokemon.png", 64, 64);
            instance = new PokemonGenerator();
        }
        return instance;
    }

    public MapPokemon generatePokemon(PokemonType type, TileManager tm) {
        double attempt = random.nextFloat();

        if (attempt <= type.getGenProbability()) {
            PokemonID ID = type.getPokemons().get(random.nextInt(type.getPokemons().size()));
            MapPokemon pokemon;

            switch (type) {
                case NORMAL -> {
                    pokemon = new MapPokemon(35 * Game.tileSize, 28 * Game.tileSize, ID, type, pokeSprites.getSprite(ID.getValue()), tm);
                    pokemon.setStrategy(strategy);
                }
                case WATER -> {
                    pokemon = new MapPokemon(21 * Game.tileSize, 4 * Game.tileSize, ID, type, pokeSprites.getSprite(ID.getValue()), tm);
                    pokemon.setStrategy(strategy);
                }
                case STEEL -> {
                    pokemon = new MapPokemon(5 * Game.tileSize, 44 * Game.tileSize, ID, type, pokeSprites.getSprite(ID.getValue()), tm);
                    pokemon.setStrategy(strategy);
                }
                default -> pokemon = null;
            }

            return pokemon;
        }
        return null;
    }

    public void setStrategy(MapPokemonStrategy strategy) {
        this.strategy = strategy;
    }


}
