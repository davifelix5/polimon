package game.pokemon;

import java.awt.*;

public class Pokemon {
    PokemonID ID;
    final int baseHP = 2;
    final int baseAttack = 2;
    final int baseDefence = 2;
    final int baseSpAtk = 2;
    final int baseSpDef = 2;
    final int baseSpeed = 2;
    final PokemonType type = PokemonType.Normal;
    PokemonID evolvesTo;

    public Pokemon(PokemonID ID, int baseHP, int baseAttack, int baseDefence, int baseSpAtk, int baseSpDef, int baseSpeed, PokemonType type) {
        this.ID = ID;
        this.baseHP = baseHP;
        this.baseAttack = baseAttack;
        this.baseDefence = baseDefence;
        this.baseSpAtk = baseSpAtk;
        this.baseSpDef = baseSpDef;
        this.baseSpeed = baseSpeed;
        this.type = type;
    }
}
