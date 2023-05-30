import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	Game game;
	public KeyInput(Game game) {
		this.game = game;
	}
	  public void keyPressed(KeyEvent e) {
		  
	  int key = e.getKeyCode();
	  
	  } 
	  public void keyReleased(KeyEvent e) { 
		  if(game.gameState == STATE.RestScreen) {
			  game.gameState = STATE.MainMenu;
		  }
	  int key = e.getKeyCode();
	   
	}

}
