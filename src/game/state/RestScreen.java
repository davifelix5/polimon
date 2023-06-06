package game.state;

import game.GamePanel;
import game.utilities.Fontes;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class RestScreen extends KeyAdapter {

	public void tick() {
		
	}
	public void render(Graphics g) {
		try{

			BufferedImage bg = ImageIO.read(new FileInputStream("src/game/res/fotos/bienioBlurred.jpg"));
			g.drawImage(bg, 0, 0, null);

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
		}catch (IOException e){
			e.printStackTrace();
		}

	

		

	}
}

