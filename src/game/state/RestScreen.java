package game.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;

public class RestScreen extends KeyAdapter {

	public void tick() {
		
	}
	public void render(Graphics g) {
		
		Font h1 = new Font("arial", Font.BOLD, 60);
		Font h2 = new Font("arial", Font.BOLD, 26);
		
		g.setFont(h1);
		g.setColor(Color.white);
		g.drawString("Polimon", (640 - (8 * h1.getSize() / 2)) / 2, 150);
		g.setFont(h2);
		g.setColor(Color.white);
		g.drawString("Pressione qualquer tecla...", (640 - (25 * h2.getSize() / 2)) / 2, 300);
	

		

	}
}

