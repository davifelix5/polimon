package game.utilities;

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

    public static Font getPokemonHollow() {
        return pokemonHollow.deriveFont(90f);
    }
    public static Font getPokemonSolid() {
        return pokemonSolid.deriveFont(90f);
    }

}
