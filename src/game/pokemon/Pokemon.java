package game.pokemon;

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

    public Pokemon(PokemonID ID) {
        this.ID = ID;
    }
}
