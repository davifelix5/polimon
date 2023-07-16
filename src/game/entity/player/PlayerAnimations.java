package game.entity.player;

public enum PlayerAnimations {

    Walk(0), Bike(1), Swimming(2);

    public final int value;

    PlayerAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
