package game.game_states;

import game.buttons.ExitButtonStrategy;
import game.entity.NPCStrategy;
import game.handlers.MouseHandler;
import game.buttons.Button;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import game.combate.StatBar;
import game.map.factory.MapFactory;
import game.state.IState;
import game.state.IStateManager;

public class CombatScreen implements IState {
    IStateManager stateManager;
    MouseHandler mouse;
    BufferedImage backgroundImage;
    BufferedImage combatHUD;

    BufferedImage alliedPokemon;

    BufferedImage enemyPokemon;

    Button fight, bag, pokemon, run;
    StatBar enemyHP, alliedHP, alliedXP;
    public CombatScreen(MouseHandler mouse, IStateManager stateManager) {
        this.mouse = mouse;
        this.stateManager = stateManager;
        loadImages();
    }

    public void tick() {

    }

    public void render(Graphics g) {

        fight = new Button("Fight",20 ,960 / 2 + 10, 640 - 176, 210, 78, mouse ,new ExitButtonStrategy());
        bag = new Button("Bag",20 ,960 / 2 + 240, 640 - 176, 210, 78, mouse, new ExitButtonStrategy());
        pokemon = new Button("Pokemon",20 ,960 / 2 + 10, 640 - 100, 210, 80, mouse, new ExitButtonStrategy());
        run = new Button("Run",20 ,960 / 2 + 240, 640 - 100, 210, 78, mouse, new ExitButtonStrategy());
        enemyHP = new StatBar(210, 135, 402 - 210, 143-135, 100, 30, Color.green); //depois trocar para vida max dos pokemons
        alliedHP = new StatBar(698,363, 890-698, 371-363, 100, 100, Color.green);
        alliedXP = new StatBar(634, 425, 888-634,436-425,100,30,Color.cyan);

        g.drawImage(backgroundImage, 0,0, null);
        g.setColor(Color.green);
        g.drawImage(alliedPokemon,80, 180, null);
        g.drawImage(combatHUD,0, 640-196,null);
        g.drawImage(enemyPokemon, 520, 0, null);

        fight.render(g);
        bag.render(g);
        pokemon.render(g);
        run.render(g);
        enemyHP.render(g);
        alliedHP.render(g);
        alliedXP.render(g);
        g.setColor(Color.white);
        g.fillRoundRect(30, 640 - 176, 400, 156, 30,30);
        g.setColor(Color.black);
        g.drawRoundRect(30, 640 - 176, 400, 156, 30,30);
        Font h2 = new Font("arial", Font.PLAIN, 20);
        g.setFont(h2);
        g.drawString("Texto de Exemplo", 100, 640 - 156);


    }

    @Override
    public void destroy() {

    }

    @Override
    public void start() {

    }

    @Override
    public void setNPCStrategy(NPCStrategy strategy) {

    }

    public void loadImages() {
        try {
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/fotos/combatScreen.jpg"));
            this.combatHUD = ImageIO.read(new FileInputStream("src/game/res/fotos/combatHUD.jpg"));
            this.alliedPokemon = ImageIO.read(new FileInputStream("src/game/res/sprites/pokemon/treeckoBack2.png"));
            this.enemyPokemon = ImageIO.read(new FileInputStream("src/game/res/sprites/pokemon/treecko2.png"));
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
