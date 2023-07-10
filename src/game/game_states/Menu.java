package game.game_states;

import game.buttons.*;
import game.buttons.Button;
import game.handlers.MouseHandler;
import game.map.factory.MapFactory;
import game.state.IState;
import game.state.IStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu implements IState {

	Button playClassic, playVintage, options, credits, exit;
	MouseHandler mouse;
	IStateManager stateManager;
	BufferedImage backgroundImage;

	public Menu(MouseHandler mouse, IStateManager stateManager) {
		this.mouse = mouse;
		this.stateManager = stateManager;
		loadImages();
	}

	public void tick() {

	}

	public void render(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, null);
		playVintage = new Button("Play Vintage Mode",35 ,180, 150, 600, 60, mouse, new PlayButtonVintageStrategy());
		playClassic = new Button("Play Classic Mode",35 ,180, 250, 600, 60, mouse, new PlayButtonClassicStrategy());
		options = new Button("Combat ",35 ,180, 350, 600, 60, mouse, new OptionsButtonStrategy());
		credits = new Button("Credits", 35,180, 450, 600, 60, mouse, new CreditsButtonStrategy());
		exit = new Button("Exit",35 ,180, 550, 600, 60, mouse, new ExitButtonStrategy());

		Font h1 = new Font("arial", Font.BOLD, 48);

		g.setFont(h1);
		g.setColor(Color.white);
		g.drawString("Main Menu", (960 - (9 * h1.getSize() / 2)) / 2, 100);

		playVintage.render(g);
		playClassic.render(g);
		options.render(g);
		credits.render(g);
		exit.render(g);
	}

	@Override
	public void destroy() {
		mouse.resetElements();
	}

	@Override
	public void start() {

	}

	public void loadImages() {
		try {
			this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/fotos/imeBlurred.jpg"));
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
