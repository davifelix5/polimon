package game.entity;

import game.npc.Npc;

public class StoppedNPCStrategy implements NPCStrategy{
    @Override
    public void setAction(Npc npc) {

    }

    @Override
    public NPCStrategy copy() {
        return new StoppedNPCStrategy();
    }
}
