package game.entity.pokemon;

public class FixedPokemonStrategy implements MapPokemonStrategy {

    @Override
    public void setAction(Pokemon pokemon) {
        pokemon.setVelX(0);
        pokemon.setVelY(0);
    }

    public MapPokemonStrategy copy() {
        return this;
    }
    
}
