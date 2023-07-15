package game.entity.pokemon;

import java.util.Random;

import game.entity.animation.SpriteSheet;
import game.map.PokemonArea;
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

    public MapPokemon generatePokemon(PokemonType type, TileManager tm, PokemonArea area) {
        double attempt = random.nextFloat();

        if (attempt <= type.getGenProbability() && area.getPokemonCount() + 1 <= area.getMaximumPokemons()) {
            PokemonID ID = type.getPokemons().get(random.nextInt(type.getPokemons().size()));
            MapPokemon pokemon;

            int minX = (int) area.getAppearanceArea().getX();
            int maxX = minX + (int) area.getAppearanceArea().getWidth();
            int minY = (int) area.getAppearanceArea().getY();
            int maxY = minY + (int) area.getAppearanceArea().getHeight();

            int posX = random.nextInt(minX, maxX);
            int posY = random.nextInt(minY, maxY);

            pokemon = new MapPokemon(posX, posY, ID, type, pokeSprites.getSprite(ID.getValue() - 1), tm);
            pokemon.setStrategy(strategy.copy());

            area.addPokemon();

            return pokemon;

        }
        return null;
    }

    public void setStrategy(MapPokemonStrategy strategy) {
        this.strategy = strategy;
    }


}
