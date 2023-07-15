package game.entity.pokemon;

public interface MapPokemonStrategy {
    void setAction(MapPokemon pokemon);
    MapPokemonStrategy copy();

}
