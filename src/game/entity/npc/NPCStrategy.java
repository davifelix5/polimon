package game.entity.npc;

public interface NPCStrategy {
    void setAction(Npc npc);
    NPCStrategy copy();
}
