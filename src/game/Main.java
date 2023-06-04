package game;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		Game game = new Game();
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Polimon");
		window.add(game);
		game.start();
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
