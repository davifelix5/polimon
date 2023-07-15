package game.ui.game_states.play;

import game.entity.player.PlayerAnimations;
import game.entity.pokemon.MapPokemon;
import game.map.TileManager;
import game.ui.handlers.MouseHandler;
import game.ui.buttons.Button;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import game.combate.StatBar;
import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;
import game.utilities.Fontes;

public class CombatScreen implements GameScreen {
    private final ScreenManager screenManager;
    private final MouseHandler mouse;
    private BufferedImage backgroundImage, combatHUD, playerImage;

    private MapPokemon enemyPokemon;
    private MapFactory factory;

    public CombatScreen(MouseHandler mouse, ScreenManager screenManager) {
        this.mouse = mouse;
        this.screenManager = screenManager;
    }

    @Override
    public void tick() {

    }

    @Override
    public void loadAnimations() {
        try {
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/fotos/combatScreen.jpg"));
            this.combatHUD = ImageIO.read(new FileInputStream("src/game/res/fotos/combatHUD.jpg"));
            this.playerImage = factory.getPlayerSpriteSheets(PlayerAnimations.Walk).getSprite(12); // Player de costas
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMapFactory(MapFactory factory) {
        this.factory = factory;
    }

    @Override
    public void setPokemonStrategy(MapPokemonStrategy strategy) {

    }

    @Override
    public TileManager getTileManager() {
        return null;
    }

    public void setEnemyPokemon(MapPokemon enemyPokemon) {
        this.enemyPokemon = enemyPokemon;
    }

    public void render(Graphics g) {

        Font h2 = new Font("arial", Font.PLAIN, 20);
        g.setFont(h2);

        Button pegar = new Button("Pegar", 20, 960 / 2 + 10, 640 - 176, 210, 156, mouse, () -> screenManager.setCurrentScreenIndex(0));
        Button correr = new Button("Correr", 20, 960 / 2 + 240, 640 - 176, 210, 156, mouse, () -> screenManager.setCurrentScreenIndex(0));
        StatBar enemyHP = new StatBar(210, 135, 402 - 210, 143 - 135, 100, 100, Color.green); //depois trocar para vida max dos pokemons
        StatBar alliedHP = new StatBar(698, 363, 890 - 698, 371 - 363, 100, 100, Color.green);
        StatBar alliedXP = new StatBar(634, 425, 888 - 634, 436 - 425, 100, 100, Color.cyan);

        g.drawImage(backgroundImage, 0, 0, null);
        g.setColor(Color.green);
        g.drawImage(playerImage, 100, 230, 220, 220, null);
        g.drawImage(combatHUD, 0, 640 - 196, null);
        g.drawImage(enemyPokemon.getPokeImage(), 550, 35, 220,220, null);

        g.setColor(Color.black);
        g.drawString("Você", 698, 325);
        g.drawString(enemyPokemon.getName(), 210, 100);

        pegar.render(g);
        correr.render(g);

        enemyHP.render(g);
        alliedHP.render(g);

        alliedXP.render(g);

        g.setColor(Color.white);
        g.fillRoundRect(30, 640 - 176, 400, 156, 30, 30);

        g.setColor(Color.black);
        g.drawRoundRect(30, 640 - 176, 400, 156, 30, 30);

        Fontes.renderText(
                g,
                "Para pegar um Pokemon basta escolher o botão de pegar Dependendo da dificuldade do jogo e da quantidade de bolas de pokemon que você possui esse pokemon será adicionado na sua pokedex",
                30, 640 - 176, 300
        );
    }

}
