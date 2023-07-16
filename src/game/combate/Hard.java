package game.combate;

import java.util.Random;

public class Hard implements GameMode {
    private final Random random = new Random();
    @Override
    public boolean capturePokemon() {
        double chosenNumber = random.nextDouble();
        return chosenNumber >= 0.5; // 50% de chance
    }

    @Override
    public int getHPLost() {
        return 20;
    }

    @Override
    public int getXPGained() {
        return 2;
    }
}
