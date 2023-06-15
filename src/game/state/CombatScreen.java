package game.state;

import game.buttons.ExitButtonStrategy;
import game.handlers.MouseHandler;
import game.buttons.Button;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class CombatScreen implements IState {
    IStateManager stateManager;
    MouseHandler mouse;
    BufferedImage backgroundImage;
    BufferedImage combatHUD;

    BufferedImage alliedPokemon;

    BufferedImage enemyPokemon;

    Button fight, bag, pokemon, run;
    public CombatScreen(MouseHandler mouse, IStateManager stateManager) {
        this.mouse = mouse;
        this.stateManager = stateManager;
        loadImages();
    }

    public void tick() {

    }

    public void render(Graphics g) {
    g.drawImage(backgroundImage, 0,0, null);
    g.setColor(Color.green);
    g.drawImage(alliedPokemon,160, 320, null);
        //g.fillRect(200, 340, 120, 120);
    g.drawImage(combatHUD,0, 640-196,null);
    g.drawImage(enemyPokemon, 620, 130, null);
        //g.fillRect(640, 150, 120, 120);
    fight = new Button("Fight",20 ,960 / 2 + 10, 640 - 176, 210, 78, mouse ,new ExitButtonStrategy());
    bag = new Button("Bag",20 ,960 / 2 + 240, 640 - 176, 210, 78, mouse, new ExitButtonStrategy());
    pokemon = new Button("Pokemon",20 ,960 / 2 + 10, 640 - 100, 210, 80, mouse, new ExitButtonStrategy());
    run = new Button("Run",20 ,960 / 2 + 240, 640 - 100, 210, 78, mouse, new ExitButtonStrategy());

    fight.render(g);
    bag.render(g);
    pokemon.render(g);
    run.render(g);
    g.setColor(Color.white);
    g.fillRoundRect(30, 640 - 176, 400, 156, 30,30);
    g.setColor(Color.black);
    g.drawRoundRect(30, 640 - 176, 400, 156, 30,30);
    Font h2 = new Font("arial", Font.PLAIN, 20);
    g.setFont(h2);
    g.drawString("Texto de Exemplo", 100, 640 - 156);

    }

    public void loadImages() {
        try {
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/fotos/combatScreen.jpg"));
            this.combatHUD = ImageIO.read(new FileInputStream("src/game/res/fotos/combatHUD.jpg"));
            this.alliedPokemon = ImageIO.read(new FileInputStream("src/game/res/sprites/pokemon/treeckoBack.png"));
            this.enemyPokemon = ImageIO.read(new FileInputStream("src/game/res/sprites/pokemon/treecko.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }
}
