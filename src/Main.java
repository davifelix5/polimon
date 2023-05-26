import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		GamePanel game = new GamePanel();
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
