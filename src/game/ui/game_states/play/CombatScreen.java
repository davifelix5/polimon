package game.ui.game_states.play;

import game.entity.player.Player;
import game.entity.player.PlayerAnimations;
import game.entity.pokemon.Pokemon;
import game.map.TileManager;
import game.ui.handlers.MouseHandler;
import game.ui.buttons.Button;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import game.combate.StatBar;
import game.map.factory.MapFactory;
import game.entity.pokemon.MapPokemonStrategy;
import game.utilities.Fontes;

public class CombatScreen implements GameScreen {
    private final ScreenManager screenManager;
    private BufferedImage backgroundImage, combatHUD, playerImage;
    private Pokemon enemyPokemon;
    private MapFactory factory;
    private final Player player;
    private final ArrayList<Pokemon> pokemons;

    private String message;
    private final Button pegar;
    private final Button correr;


    public CombatScreen(Player player, MouseHandler mouse, ScreenManager screenManager, ArrayList<Pokemon> pokemons) {
        this.player = player;
        this.screenManager = screenManager;
        this.pokemons = pokemons;

        this.pegar = new Button(
                "Pegar",
                20,
                960 / 2 + 10, 640 - 176,
                210, 156,
                mouse,
                this::capturePokemon
        );

        this.correr = new Button(
                "Correr",
                20,
                960 / 2 + 240, 640 - 176,
                210, 156,
                mouse, () -> screenManager.setCurrentScreenIndex(0)
        );


    }

    @Override
    public void loadAnimations() {
        pegar.setIsActive(true);
        correr.setIsActive(true);
        // Inicial message
        this.message = "Para pegar um Pokemon basta escolher o botão de pegar Dependendo da dificuldade do jogo e da quantidade de bolas de pokemon que você possui esse pokemon será adicionado na sua pokedex";
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

    public void setEnemyPokemon(Pokemon enemyPokemon) {
        this.enemyPokemon = enemyPokemon;
    }

    private void capturePokemon() {
        if (this.player.hasPokeballs() && this.player.getHP() > 0) {
            this.player.removePokeball();
            this.player.reduceHP(10);
            this.player.increaseExpeciente(10);
            this.pokemons.remove(enemyPokemon);
            enemyPokemon.getPokeArea().removePokemon();
            pegar.setIsActive(false);
            correr.setIsActive(false);
            this.screenManager.setCurrentScreenIndex(0);
        } else if (!this.player.hasPokeballs()) {
            this.message = "Você não tem pokebolas";
        } else if (this.player.getHP() == 0) {
            this.message = "Você está muito casado para capturar este pokemon. Tente se curar!";
        }
        else {
            this.message = this.enemyPokemon.getName() + " não quer ser capturado. Tente novamente!";
        }
    }

    @Override
    public void render(Graphics g) {

        Font h2 = new Font("arial", Font.PLAIN, 20);
        g.setFont(h2);

        StatBar enemyHP = new StatBar(
                210, 135,
                402 - 210, 143 - 135,
                100, 100,
                Color.green
        );

        StatBar alliedHP = new StatBar(
                698, 363,
                890 - 698, 371 - 363,
                100, this.player.getHP(),
                Color.green
        );

        StatBar alliedXP = new StatBar(
                634, 425,
                888 - 634, 436 - 425,
                100, this.player.getExperience(),
                Color.cyan
        );

        // Fundo
        g.drawImage(backgroundImage, 0, 0, null);
        g.setColor(Color.green);
        // Imagem do player
        g.drawImage(playerImage, 100, 230, 220, 220, null);
        // HUD
        g.drawImage(combatHUD, 0, 640 - 196, null);
        // Imagem do pokemon
        g.drawImage(enemyPokemon.getPokeImage(), 550, 35, 220,220, null);

        // Nome do pokemon
        g.setColor(Color.black);
        g.drawString("Você", 698, 325);
        g.drawString(enemyPokemon.getName(), 210, 100);

        // Botões e barras de HP
        pegar.render(g);
        correr.render(g);

        enemyHP.render(g);
        alliedHP.render(g);

        alliedXP.render(g);

        // Instruções
        g.setColor(Color.white);
        g.fillRoundRect(30, 640 - 176, 400, 156, 30, 30);
        g.setColor(Color.black);
        g.drawRoundRect(30, 640 - 176, 400, 156, 30, 30);
        Fontes.renderText(
                g,
                message,
                30, 640 - 176, 300
        );

        this.player.renderPokeballAmount(g);
    }

    @Override
    public void tick() {

    }

    @Override
    public void setPokemonStrategy(MapPokemonStrategy strategy) {

    }

    @Override
    public TileManager getTileManager() {
        return null;
    }

}
