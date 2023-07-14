package game.ui.game_states;

import game.ui.buttons.*;
import game.ui.buttons.Button;
import game.entity.npc.NPCStrategy;
import game.ui.handlers.MouseHandler;
import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu implements IState {

	Button playClassic, playVintage, exit, moveNpcs, walkNpcs;
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
		exit = new Button("Exit",35 ,180, 350, 600, 60, mouse, new ExitButtonStrategy());
		moveNpcs = new Button("NPCs & Pokemons andando",35 ,180, 450, 600, 80, mouse, new WalkNPCPokemonButtonStrategy());
		walkNpcs = new Button("NPCs & Pokemons parados",35 ,180, 550, 600, 80, mouse, new FixedNPCPokemonButtonStrategy());

		Font h1 = new Font("arial", Font.BOLD, 48);

		g.setFont(h1);
		g.setColor(Color.white);
		g.drawString("Main Menu", (960 - (9 * h1.getSize() / 2)) / 2, 100);

		playVintage.render(g);
		playClassic.render(g);
		exit.render(g);
		moveNpcs.render(g);
		walkNpcs.render(g);
	}

	@Override
	public void destroy() {
		mouse.resetElements();
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
