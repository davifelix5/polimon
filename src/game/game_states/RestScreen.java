package game.game_states;

import game.state.IState;
import game.state.IStateManager;
import game.utilities.Fontes;
import game.handlers.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class RestScreen implements IState {

	KeyHandler keyHandler;
	IStateManager stateManager;
	private BufferedImage backgroundImage;

	public RestScreen(KeyHandler keyHandler, IStateManager stateManager) {
		this.stateManager = stateManager;
		this.keyHandler = keyHandler;
		loadImages();
	}

	public void tick() {
		if (keyHandler.anyKeyPressed) {
			getStateManager().setState(GameState.Menu);
		}
	}
	public void render(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, null);

		Font t1 = Fontes.getPokemonSolid();
		Font t2 = Fontes.getPokemonHollow();
		Font subTitle = new Font("arial", Font.BOLD, 32);

		g.setColor(Color.YELLOW);
		g.setFont(t1);
		g.drawString("Polimon", (960 - (8 * t1.getSize() / 2)) / 2, 250);
		g.setColor(Color.BLUE);
		g.setFont(t2);
		g.drawString("Polimon", (960 - (8 * t2.getSize() / 2)) / 2, 250);
		g.setColor(Color.WHITE);
		g.setFont(subTitle);
		g.drawString("Pressione qualquer tecla...", (960 - (25 * subTitle.getSize() / 2)) / 2, 400);
	}

	@Override
	public void destroy() {

	}

	public void loadImages() {
		try {
			this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/fotos/bienioBlurred.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IStateManager getStateManager() {
		return stateManager;
	}
}

