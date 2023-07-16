package game.ui.game_states;

import game.entity.npc.NPCStrategy;
import game.map.factory.MapFactory;
import game.utilities.Fontes;
import game.ui.handlers.KeyHandler;
import game.entity.pokemon.MapPokemonStrategy;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class RestScreen implements IState {

	private final KeyHandler keyHandler;
	private final IStateManager stateManager;
	private BufferedImage backgroundImage;

	public RestScreen(KeyHandler keyHandler, IStateManager stateManager) {
		this.stateManager = stateManager;
		this.keyHandler = keyHandler;
		loadImages();
	}

	public void tick() {
		if (keyHandler.isAnyKeyPressed()) {
			getStateManager().setState(GameState.Menu);
		}
	}
	public void render(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, null);

		Font t1 = Fontes.getPokemonSolid(90f);
		Font t2 = Fontes.getPokemonHollow(90f);
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

	@Override
	public void start() {

	}

	@Override
	public void setNPCStrategy(NPCStrategy strategy) {

	}

	@Override
    public void setMapPokemonStrategy(MapPokemonStrategy strategy) {

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

	@Override
	public void setFactory(MapFactory factory) {

	}
}

