import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = -1442798787354930462L;
	
	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread;
	private boolean running = false;
	public Menu menu;
	public RestScreen restScreen;
	
	public STATE gameState = STATE.RestScreen;
	
	public Game()  throws Exception{
		
		menu = new Menu(this);
		restScreen = new RestScreen();
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(this));
		
		new Window(WIDTH, HEIGHT, "Polimon", this);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis()- timer > 1000) {
				timer += 1000;
				System.out.println("FPS:" + frames);
				frames = 0;
			}
		}
		stop();
	}
	private void tick() {
		if(gameState != STATE.RestScreen) {
			if(gameState == STATE.MainMenu) {
				menu.tick();
			}
		} else {
			restScreen.tick();
		}

	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		if(gameState != STATE.RestScreen) {
			if(gameState == STATE.MainMenu) {
				menu.render(g);
			}
		}else {
			restScreen.render(g);
		}
	
		
		g.dispose();
		bs.show();
	}
	public static void main(String[] args) {
		try {new Game();
		} catch(Exception e) {
			System.out.println("erro");
		}
	}

}
