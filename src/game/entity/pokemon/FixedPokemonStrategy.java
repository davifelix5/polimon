package game.entity.pokemon;

public class FixedPokemonStrategy implements MapPokemonStrategy {

    @Override
    public void setAction(MapPokemon pokemon) {
        pokemon.setVelX(0);
        pokemon.setVelY(0);
    }
    
}
