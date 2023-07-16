package game.entity.npc;

public class FixedNPCStrategy implements NPCStrategy {
    @Override
    public void setAction(Npc npc) {
        npc.setVelX(0);
        npc.setVelY(0);
        npc.getAnimationSet().getCurrentAnimation().reset();
        npc.getAnimationSet().getCurrentAnimation().stop();
    }

    @Override
    public NPCStrategy copy() {
        return new FixedNPCStrategy();
    }
}
