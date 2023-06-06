package game.state;

import game.Game;
import game.buttons.*;
import game.buttons.Button;
import game.handlers.MouseHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu implements IState{

	Button play, options, credits, exit;

	MouseHandler mouse;
	IStateManager stateManager;

	public Menu(MouseHandler mouse, IStateManager stateManager) {
		this.mouse = mouse;
		this.stateManager = stateManager;
	}

	public void tick() {

	}

	public void render(Graphics g) {

		try{
			BufferedImage bg = ImageIO.read(new FileInputStream("src/game/res/fotos/imeBlurred.jpg"));
			g.drawImage(bg, 0, 0, null);
			play = new Button("Play", 180, 150, 600, 80, mouse, new PlayButtonStrategy());
			options = new Button("Options", 180, 250, 600, 80, mouse, new OptionsButtonStrategy());
			credits = new Button("Credits", 180, 350, 600, 80, mouse, new CreditsButtonStrategy());
			exit = new Button("Exit", 180, 450, 600, 80, mouse, new ExitButtonStrategy());

			Font h1 = new Font("arial", Font.BOLD, 48);

			g.setFont(h1);
			g.setColor(Color.white);
			g.drawString("Main Menu", (960 - (9 * h1.getSize() / 2)) / 2, 100);

			play.render(g);
			options.render(g);
			credits.render(g);
			exit.render(g);
		}catch(IOException e){
			e.printStackTrace();
		}

	}

	@Override
	public IStateManager getStateManager() {
		return stateManager;
	}
}
