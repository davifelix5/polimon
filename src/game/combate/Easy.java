package game.combate;

public class Easy implements GameMode {
    @Override
    public boolean capturePokemon() {
        return false;
    }

    @Override
    public int getHPLost() {
        return 0;
    }

    @Override
    public int getXPGained() {
        return 0;
    }
}
