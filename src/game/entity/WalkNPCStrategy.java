package game.entity;

import game.npc.Npc;

import java.util.Random;

public class WalkNPCStrategy implements NPCStrategy {

    int actionLockCounter = 0;

    @Override
    public void setAction(Npc npc) {
        int BACKWARD = 0, LEFT = 1,  RIGHT = 2, FOWARD = 3; // Indices das animações
        actionLockCounter++;

        Random i = new Random();
        int actionNumber = i.nextInt(100) + 1;

        if (actionLockCounter == 120) {
            if (actionNumber <= 25) {
                npc.getAnimationSet().setCurrentIndex(FOWARD);
                npc.getAnimationSet().getCurrentAnimation().start();
                npc.setVelX(0);
                npc.setVelY(-npc.getMovingRate());
            }
            else if (actionNumber <= 50) {
                npc.getAnimationSet().setCurrentIndex(BACKWARD);
                npc.getAnimationSet().getCurrentAnimation().start();
                npc.setVelX(0);
                npc.setVelY(npc.getMovingRate());
            }
            else if (actionNumber <= 75) {
                npc.getAnimationSet().setCurrentIndex(LEFT);
                npc.getAnimationSet().getCurrentAnimation().start();
                npc.setVelY(0);
                npc.setVelX(-npc.getMovingRate());
            }
            else {
                npc.getAnimationSet().setCurrentIndex(RIGHT);
                npc.getAnimationSet().getCurrentAnimation().start();
                npc.setVelY(0);
                npc.setVelX(npc.getMovingRate());
            }
            actionLockCounter = 0;
        }
    }
}
