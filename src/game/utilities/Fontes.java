package game.utilities;

import game.Game;
import game.ui.game_states.play.GameScreen;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fontes {

    public static Font pokemonHollow;
    public static Font pokemonSolid;

    static {
        try {
            pokemonHollow = Font.createFont(Font.TRUETYPE_FONT, new File("src/game/res/fontes/Pokemon Hollow.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            pokemonSolid = Font.createFont(Font.TRUETYPE_FONT, new File("src/game/res/fontes/Pokemon Solid.ttf"));
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Font getPokemonHollow(float size) {
        return pokemonHollow.deriveFont(size);
    }
    public static Font getPokemonSolid(float size) {
        return pokemonSolid.deriveFont(size);
    }

    public static void renderText(Graphics g, String text, int x, int y, int width) {
        int wordX = x + Game.tileSize;
        int wordY = y + Game.tileSize;
        double lineSize = 0;

        String[] words = text.split(" ");

        for (String w: words) {
            String finalWord = w + " ";
            int wordWidth = g.getFontMetrics().stringWidth(finalWord);
            lineSize += wordWidth;

            if (lineSize >= width - Game.tileSize) {
                lineSize = 0; // zera o controle do espaço ocupado pela fonte
                wordY += Game.tileSize * 0.625; // pula para a próxima linha
                wordX =  x + Game.tileSize; // volta para o começo da linha
            }

            g.drawString(finalWord, wordX, wordY);
            wordX += wordWidth;
        }
    }

}
