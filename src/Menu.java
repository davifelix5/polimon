import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{
	
	Game game;
	
	public Menu(Game game) {
		this.game = game;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		
		
		if(mouseOver(mx, my, 120, 130, 400, 50)) {
			game.gameState = STATE.Game;
		}
		if(mouseOver(mx, my, 120, 200, 400, 50)) {
			game.gameState = STATE.Options;
		}
		if(mouseOver(mx, my, 120, 270, 400, 50)) {
			game.gameState = STATE.Credits;
		}
		if(mouseOver(mx, my, 120, 340, 400, 50)) {
			System.exit(1);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	private boolean mouseOver(int mx,int my,int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}else return false;
		}else return false;
		
	}
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		
		Font h1 = new Font("arial", 1, 32);
		Font h2 = new Font("arial", 1, 26);
		
		g.setFont(h1);
		g.setColor(Color.white);
		g.drawString("Main Menu", (640 - (9 * h1.getSize() / 2)) / 2, 100);
		g.setFont(h2);
		g.setColor(Color.white);
		g.drawString("Jogar", (640 - (5 * h2.getSize() / 2)) / 2, 165);
		g.drawString("Opções", (640 - (6 * h2.getSize() / 2)) / 2, 235);
		g.drawString("Créditos", (640 - (7 * h2.getSize() / 2)) / 2, 305);
		g.drawString("Sair", (640 - (4 * h2.getSize() / 2)) / 2, 375);
		
		g.setColor(Color.white);
		
		g.drawRect(120, 130, 400, 50);
		g.drawRect(120, 200, 400, 50);
		g.drawRect(120, 270, 400, 50);
		g.drawRect(120, 340, 400, 50);
		
		
	}
}
