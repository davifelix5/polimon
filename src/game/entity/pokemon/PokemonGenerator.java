package game.entity.pokemon;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

import game.entity.animation.SpriteSheet;
import game.map.PokemonArea;
import game.map.TileManager;

public class PokemonGenerator {

    private Instant oldTime;
    private MapPokemonStrategy strategy = new WalkPokemonStrategy();
    private final Random random;
    private static SpriteSheet pokeSprites;
    private final int delayInSeconds; // Intervalo de tempo para gerar novos pokemons
    private final ArrayList<PokemonArea> pokemonAreas = new ArrayList<>();
    private final ArrayList<MapPokemon> pokemons;

    public PokemonGenerator(ArrayList<MapPokemon> pokemons, int delayInSeconds) {
        pokeSprites = new SpriteSheet("src/game/res/sprites/pokemon/pokemon.png", 64, 64);
        random = new Random();
        this.pokemons = pokemons;
        this.delayInSeconds = delayInSeconds;
    }

    public void generatePokemon(TileManager tm) {
        for (PokemonArea area: pokemonAreas) {
            MapPokemon pokemon = generatePokemonInArea(area.getTypes(), tm, area);
            if (pokemon != null)
                this.pokemons.add(pokemon);
        }
    }

    public MapPokemon generatePokemonInArea(PokemonType[] types, TileManager tm, PokemonArea area) {
        PokemonType type = types[random.nextInt(types.length)]; // Escolha aleatório de qual dos tipos gerar

        double attempt = random.nextFloat(); // Número aleatório para calcular a propabilidade de um pokemonser gerado

        if (attempt <= type.getGenProbability() && area.getPokemonCount() + 1 <= area.getMaximumPokemons()) {
            PokemonID ID = type.getPokemons().get(random.nextInt(type.getPokemons().size()));
            MapPokemon pokemon;

            int minX = (int) area.getAppearanceArea().getX();
            int maxX = minX + (int) area.getAppearanceArea().getWidth() - 64;
            int minY = (int) area.getAppearanceArea().getY();
            int maxY = minY + (int) area.getAppearanceArea().getHeight() - 64;

            int posX = random.nextInt(minX, maxX);
            int posY = random.nextInt(minY, maxY);

            pokemon = new MapPokemon(posX, posY, ID, pokeSprites.getSprite(ID.getValue() - 1), tm, area);
            pokemon.setStrategy(strategy.copy());

            area.addPokemon();

            return pokemon;

        }

        return null;
    }

    public void generate(TileManager tm) {
        Instant newTime = Instant.now();
        if (this.oldTime == null) this.oldTime = newTime;
        long elapsedTime = Duration.between(oldTime, newTime).toMillis();
        if (elapsedTime >= delayInSeconds * 1000L) {
            this.generatePokemon(tm);
            this.oldTime = newTime;
        }
    }

    public void setStrategy(MapPokemonStrategy strategy) {
        this.strategy = strategy;
    }

    public void addArea(PokemonArea area) {
        this.pokemonAreas.add(area);
    }

    public ArrayList<PokemonArea> getPokemonAreas() {
        return pokemonAreas;
    }
}
