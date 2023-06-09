package game.entity;

public enum PlayerAnimations {

    Walk(1), Bike(1);

    public final int value;

    PlayerAnimations(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
