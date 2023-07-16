package game.combate;

import java.util.Random;

public class Medium implements GameMode {
    private final Random random = new Random();
    @Override
    public boolean capturePokemon() {
        double chosenNumber = random.nextDouble();
        return chosenNumber >= 0.3; // 70% de chance
    }

    @Override
    public int getHPLost() {
        return 15;
    }

    @Override
    public int getXPGained() {
        return 5;
    }
}
