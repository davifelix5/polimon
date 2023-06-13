package game.pokemon;

public class Pokemon {
    PokemonID ID;
    final int baseHP;
    final int baseAttack;
    final int baseDefence;
    final int baseSpAtk;
    final int baseSpDef;
    final int baseSpeed;
    final PokemonType type;
    PokemonID evolvesTo;

    public Pokemon(PokemonID ID) {
        this.ID = ID;
    }
}
