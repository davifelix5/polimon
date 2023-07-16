package game.combate;

public class Easy implements GameMode {
    @Override
    public boolean capturePokemon() {
        return true;
    }

    @Override
    public int getHPLost() {
        return 10;
    }

    @Override
    public int getXPGained() {
        return 10;
    }
}
