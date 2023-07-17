package game.ui.game_states;

import game.Game;
import game.combate.Easy;
import game.combate.GameMode;
import game.combate.Hard;
import game.combate.Medium;
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
import java.util.Arrays;

public class Menu implements IState {

	private final IStateManager stateManager;
	private BufferedImage backgroundImage;

	private final Button[] buttons = new Button[8];

	private final Sound music = new Sound("src/game/res/sound/menu.wav");

	 private final GameMode[] modes = {new Easy(), new Medium(), new Hard()};

	public Menu( Game game, IStateManager stateManager, MouseHandler mouse) {
		this.stateManager = stateManager;

		loadImages();

		/* Botões para trocar de fábrica abstrata */
		buttons[0] = new Button("Play Vintage Mode",35 ,180, 60, 600, 60, mouse, () -> {
			game.setMapFactory("Vintage");
			stateManager.setState(GameState.Outside);
		});
		buttons[1] = new Button("Play Classic Mode",35 ,180, 130, 600, 60, mouse, () -> {
			game.setMapFactory("Classic");
			stateManager.setState(GameState.Outside);
		});

		/* Botões para alterar a estratégia nos NPCs e Pokemons */
		buttons[2] = new Button("NPCs & Pokemons andando",35 ,180, 200, 600, 60, mouse, () -> {
			stateManager.setNPCStrategy(new WalkNPCStrategy());
			stateManager.setMapPokemonStrategy(new WalkPokemonStrategy());
		});
		buttons[3] = new Button("NPCs & Pokemons parados",35 ,180, 270, 600, 60, mouse, () -> {
			stateManager.setNPCStrategy(new FixedNPCStrategy());
			stateManager.setMapPokemonStrategy(new FixedPokemonStrategy());
		});

		/* Botões para viabilizar a troca de dificuldades */
		buttons[4] = new Button("Dificuldade fácil",35 ,180, 340, 600, 60, mouse, () -> game.setGameMode(modes[0]));
		buttons[5] = new Button("Dificuldade média",35 ,180, 410, 600, 60, mouse, () -> game.setGameMode(modes[1]));
		buttons[6] = new Button("Dificuldade difícil",35 ,180, 480, 600, 60, mouse, () -> game.setGameMode(modes[2]));
		buttons[7] = new Button("Exit",35 ,180, 550, 600, 60, mouse, () -> System.exit(0));
	}

	public void tick() {

	}

	public void render(Graphics g) {

		g.drawImage(backgroundImage, 0, 0, null);

		Arrays.stream(buttons).forEach(btn -> btn.render(g));
	}

	@Override
	public void destroy() {
		Arrays.stream(buttons).forEach(btn -> btn.setIsActive(false));
		music.stop();
	}

	@Override
	public void start() {
		music.restart();
		Arrays.stream(buttons).forEach(btn -> btn.setIsActive(true));
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
