package game.entity.npc;

public class FixedNPCStrategy implements NPCStrategy {
    @Override
    public void setAction(Npc npc) {

    }

    @Override
    public NPCStrategy copy() {
        return new FixedNPCStrategy();
    }
}
