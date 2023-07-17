package game.entity.pokemon;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

import game.entity.animation.SpriteSheet;
import game.map.PokemonArea;
import game.map.TileManager;

/**
 * Responsável por gerenciar a geração de pokemons no mapa
 */
public class PokemonGenerator {

    private Instant oldTime; // tempo em que houve a última geração de pokemons
    private MapPokemonStrategy strategy = new WalkPokemonStrategy(); // estratégia padrão para os pokemons gerados
    private final Random random;
    private static SpriteSheet pokeSprites; // spritesheet contendo todos os pokemons
    private final int delayInSeconds; // Intervalo de tempo para gerar novos pokemons
    private final ArrayList<PokemonArea> pokemonAreas = new ArrayList<>(); // áreas para aparição de pokemons
    private final ArrayList<Pokemon> pokemons; // todos os pokemons atualmente no mapa

    public PokemonGenerator(ArrayList<Pokemon> pokemons, int delayInSeconds) {
        pokeSprites = new SpriteSheet("src/game/res/sprites/pokemon/pokemon.png", 64, 64);
        random = new Random();
        this.pokemons = pokemons;
        this.delayInSeconds = delayInSeconds;
    }

    /**
     * Percorre todas as áreas onde podem ser gerados pokemos e executa a lógica de geração
     * @param tm TileManager para ser setado nos pokemons gerados
     */
    private void generatePokemon(TileManager tm) {
        for (PokemonArea area: pokemonAreas) {
            Pokemon pokemon = generatePokemonInArea(area, tm);
            if (pokemon != null)
                this.pokemons.add(pokemon);
        }
    }

    /**
     * Gera pokemon em uma determinada área
     * @param area área de gerão em que o pokemon deve ser gerado
     * @param tm TileManager para ser setado nos pokemons gerados
     * @return pokemon gerado, caso haja
     */
    private Pokemon generatePokemonInArea(PokemonArea area, TileManager tm) {
        PokemonType[] types = area.getTypes();
        PokemonType type = types[random.nextInt(types.length)]; // Escolha aleatório de qual dos tipos gerar

        double attempt = random.nextFloat(); // Número aleatório para calcular a propabilidade de um pokemonser gerado

        // Gera o pokemon dependendo da probilidade de geração e da quantida de pokemons já presentes na área
        if (attempt <= type.getGenProbability() && area.getPokemonCount() + 1 <= area.getMaximumPokemons()) {
            PokemonID ID = type.getPokemons().get(random.nextInt(type.getPokemons().size()));
            Pokemon pokemon;

            // Coordenadas máximas e mínimas em que um pokemon pode aparecer na áera
            int minX = (int) area.getAppearanceArea().getX();
            int maxX = minX + (int) area.getAppearanceArea().getWidth() - 64;
            int minY = (int) area.getAppearanceArea().getY();
            int maxY = minY + (int) area.getAppearanceArea().getHeight() - 64;

            // Escolhe aleatória de coordenadas para o aparecimento do pokemon
            int posX = random.nextInt(minX, maxX);
            int posY = random.nextInt(minY, maxY);

            // Criação do pokemon
            pokemon = new Pokemon(posX, posY, ID, pokeSprites.getSprite(ID.getValue() - 1), tm, area);
            pokemon.setStrategy(strategy.copy());

            area.addPokemon();

            return pokemon;

        }

        return null;
    }

    /**
     * Método para gerar pokemons conforme o intervalo de tempo determinado
     * @param tm TileManager para ser setado nos pokemons gerados
     */
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

}
