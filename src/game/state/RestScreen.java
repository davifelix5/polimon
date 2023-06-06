package game.state;

import game.Game;
import game.handlers.KeyHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;

public class RestScreen extends KeyAdapter implements IState {

	KeyHandler keyHandler;
	IStateManager stateManager;

	public RestScreen(KeyHandler keyHandler, IStateManager stateManager) {
		this.stateManager = stateManager;
		this.keyHandler = keyHandler;

	}

	public void tick() {
		if (keyHandler.anyKeyPressed) {
			getStateManager().setState(StateID.Menu);
		}
	}
	public void render(Graphics g) {
		Font h1 = new Font("arial", Font.BOLD, 60);
		Font h2 = new Font("arial", Font.BOLD, 26);
		g.setColor(Color.BLACK);
		g.fillRect(0,0, Game.width, Game.height);

		g.setColor(Color.WHITE);
		g.setFont(h1);
		g.drawString("Polimon", (640 - (8 * h1.getSize() / 2)) / 2, 150);
		g.setFont(h2);
		g.drawString("Pressione qualquer tecla...", (640 - (25 * h2.getSize() / 2)) / 2, 300);
	}

	@Override
	public IStateManager getStateManager() {
		return stateManager;
	}
}

