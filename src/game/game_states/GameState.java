package game.game_states;

public enum GameState {
	Bienio(0), Options(1), Credits(2), RestScreen(3), Menu(4), Outside(5), Combate(6);
	private final int value;
	GameState(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
