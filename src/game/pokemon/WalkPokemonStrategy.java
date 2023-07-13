package game.pokemon;

import java.util.Random;

public class WalkPokemonStrategy implements MapPokemonStrategy {

    private int actionLockCounter;
    private int currentDirection;
    private Random random;

    public WalkPokemonStrategy() {
        this.actionLockCounter = 0;
        this.random = new Random();
    }

    @Override
    public void setAction(MapPokemon pokemon) {
        this.actionLockCounter++;

        if (this.actionLockCounter == 120) {
            this.currentDirection = random.nextInt(4);
            switch (currentDirection) {
                case 0: // Mover para cima
                    pokemon.setVelX(0);
                    pokemon.setVelY(-pokemon.getMovingRate());
                    break;
                case 1: // Mover para baixo
                    pokemon.setVelX(0);
                    pokemon.setVelY(pokemon.getMovingRate());
                    break;
                case 2: // Mover para a esquerda
                    pokemon.setVelX(-pokemon.getMovingRate());
                    pokemon.setVelY(0);
                    break;
                case 3: // Mover para a direita;
                    pokemon.setVelX(pokemon.getMovingRate());
                    pokemon.setVelY(0);
                    break;
            }
        }
    }
}
