package game.state;

public enum StateID {
	Bienio(0), Options(1), Credits(2), RestScreen(3), Menu(4), Outside(5), Combate(6);
	private final int value;
	StateID(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
