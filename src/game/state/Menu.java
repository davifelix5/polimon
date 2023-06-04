package game.state;

import game.Game;
import game.buttons.*;
import game.buttons.Button;
import game.handlers.MouseHandler;

import java.awt.*;

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
		play = new Button("Jogar", 120, 130, 400, 50, mouse, new PlayButtonStrategy());
		options = new Button("Options", 120, 200, 400, 50, mouse, new OptionsButtonStrategy());
		credits = new Button("Credits", 120, 270, 400, 50, mouse, new CreditsButtonStrategy());
		exit = new Button("Exit", 120, 340, 400, 50, mouse, new ExitButtonStrategy());

		Font h1 = new Font("arial", Font.BOLD, 32);

		g.setColor(Color.BLACK);
		g.fillRect(0,0, Game.width, Game.height);

		g.setFont(h1);
		g.setColor(Color.white);
		g.drawString("Main Menu", (640 - (9 * h1.getSize() / 2)) / 2, 100);

		play.render(g);
		options.render(g);
		credits.render(g);
		exit.render(g);
	}

	@Override
	public IStateManager getStateManager() {
		return stateManager;
	}
}
