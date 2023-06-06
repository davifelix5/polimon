package game.state;

import game.GamePanel;
import game.buttons.*;
import game.buttons.Button;
import game.handlers.MouseHandler;

import java.awt.*;

public class Menu {

	Button play, options, credits, exit;

	MouseHandler mouse;

	public Menu(MouseHandler mouse) {
		this.mouse = mouse;
	}

	public void tick() {

	}

	public void render(Graphics g) {
		play = new Button("Play", 180, 150, 600, 80, mouse, new PlayButtonStrategy());
		options = new Button("Options", 180, 250, 600, 80, mouse, new OptionsButtonStrategy());
		credits = new Button("Credits", 180, 350, 600, 80, mouse, new CreditsButtonStrategy());
		exit = new Button("Exit", 180, 450, 600, 80, mouse, new ExitButtonStrategy());

		Font h1 = new Font("arial", Font.BOLD, 48);

		g.setColor(Color.BLACK);
		g.fillRect(0,0, GamePanel.width, GamePanel.height);

		g.setFont(h1);
		g.setColor(Color.white);
		g.drawString("Main Menu", (960 - (9 * h1.getSize() / 2)) / 2, 100);

		play.render(g);
		options.render(g);
		credits.render(g);
		exit.render(g);
	}
}
