package game.map;

import game.animation.SpriteSheet;
import game.pokemon.PokemonType;

public class PokemonLayer extends MapLayer {
    public PokemonLayer(String tilemapPath, SpriteSheet spriteSheet, PokemonType pokemonType) {
        super(tilemapPath, spriteSheet, false);
        this.pokemonLayer = true;
        this.pokemonType = pokemonType;
    }
}
