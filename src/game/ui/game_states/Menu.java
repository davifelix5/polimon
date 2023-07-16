package game.ui.game_states;

import game.Game;
import game.entity.npc.FixedNPCStrategy;
import game.entity.npc.WalkNPCStrategy;
import game.entity.pokemon.FixedPokemonStrategy;
import game.entity.pokemon.WalkPokemonStrategy;
import game.ui.buttons.Button;
import game.entity.npc.NPCStrategy;
import game.ui.handlers.MouseHandler;
import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;
import game.ui.sounds.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Menu implements IState {

	private final IStateManager stateManager;
	private BufferedImage backgroundImage;
	private final Button playVintage, playClassic, exit, moveNpcs, walkNpcs;

	private final Sound music = new Sound("src/game/res/sound/menu.wav");

	public Menu(MouseHandler mouse, IStateManager stateManager, Game game) {
		this.stateManager = stateManager;
		loadImages();
		playVintage = new Button("Play Vintage Mode",35 ,180, 150, 600, 60, mouse, () -> {
			game.setMapFactory("Vintage");
			stateManager.setState(GameState.Outside);
		});
		playClassic = new Button("Play Classic Mode",35 ,180, 250, 600, 60, mouse, () -> {
			game.setMapFactory("Classic");
			stateManager.setState(GameState.Outside);
		});
		exit = new Button("Exit",35 ,180, 350, 600, 60, mouse, () -> System.exit(0));
		moveNpcs = new Button("NPCs & Pokemons andando",35 ,180, 450, 600, 80, mouse, () -> {
			stateManager.setNPCStrategy(new WalkNPCStrategy());
			stateManager.setMapPokemonStrategy(new WalkPokemonStrategy());
		});
		walkNpcs = new Button("NPCs & Pokemons parados",35 ,180, 550, 600, 80, mouse, () -> {
			stateManager.setNPCStrategy(new FixedNPCStrategy());
			stateManager.setMapPokemonStrategy(new FixedPokemonStrategy());
		});
	}

	public void tick() {

	}

	public void render(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, null);

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
		playVintage.setIsActive(false);
		playClassic.setIsActive(false);
		exit.setIsActive(false);
		moveNpcs.setIsActive(false);
		walkNpcs.setIsActive(false);
		music.stop();
	}

	@Override
	public void start() {
		playVintage.setIsActive(true);
		playClassic.setIsActive(true);
		exit.setIsActive(true);
		moveNpcs.setIsActive(true);
		walkNpcs.setIsActive(true);
		music.play();
		music.loop();
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
