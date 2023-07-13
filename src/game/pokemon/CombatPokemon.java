package game.pokemon;

public class CombatPokemon {
    PokemonID ID;
    final int baseHP;
    final int baseAttack;
    final int baseDefence;
    final int baseSpAtk;
    final int baseSpDef;
    final int baseSpeed;
    final PokemonType type;
    PokemonID evolvesTo;

    public CombatPokemon(PokemonID ID, int baseHP, int baseAttack, int baseDefence, int baseSpAtk, int baseSpDef, int baseSpeed, PokemonType type) {
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
