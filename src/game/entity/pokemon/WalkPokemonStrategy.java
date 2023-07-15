package game.entity.pokemon;

import java.util.Random;

public class WalkPokemonStrategy implements MapPokemonStrategy {

    private int actionLockCounter;

    public WalkPokemonStrategy() {
        this.actionLockCounter = 0;
    }

    @Override
    public void setAction(MapPokemon pokemon) {
        actionLockCounter++;

        Random i = new Random();
        int actionNumber = i.nextInt(100) + 1;

        if (actionLockCounter == 120) {
            if (actionNumber <= 25) {
                pokemon.setVelX(0);
                pokemon.setVelY(-pokemon.getMovingRate());
            } else if (actionNumber <= 50) {
                pokemon.setVelX(0);
                pokemon.setVelY(pokemon.getMovingRate());
            } else if (actionNumber <= 75) {
                pokemon.setVelY(0);
                pokemon.setVelX(-pokemon.getMovingRate());
            } else {
                pokemon.setVelY(0);
                pokemon.setVelX(pokemon.getMovingRate());
            }
            actionLockCounter = 0;
        }
    }

    @Override
    public MapPokemonStrategy copy() {
        return new WalkPokemonStrategy();
    }
}