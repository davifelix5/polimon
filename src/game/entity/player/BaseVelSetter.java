package game.entity.player;

public record BaseVelSetter(Player player) implements IPlayerVelSetter {

    @Override
    public void setVel() {

        // Movimentação em X
        if (player.getVelX() == 0) { // O jogador não pode se movimentar em duas direções simultaneamente
            if (player.getMovementKeyInput().isUpPressed()) {
                player.getCurrentAnimationSet().setCurrentIndex(Player.FOWARD);
                player.getAnimation().start();
                player.setVelY(-player.getMovingRate());
            } else if (player.getMovementKeyInput().isDownPressed()) {
                player.getCurrentAnimationSet().setCurrentIndex(Player.BACKWARD);
                player.getAnimation().start();
                player.setVelY(player.getMovingRate());
            }

        }

        // Movimentação em y
        if (player.getVelY() == 0) { // O jogador não pode se movimentar em duas direções simultaneamente
            if (player.getMovementKeyInput().isLeftPressed()) {
                player.getCurrentAnimationSet().setCurrentIndex(Player.LEFT);
                player.getAnimation().start();
                player.setVelX(-player.getMovingRate());
            } else if (player.getMovementKeyInput().isRightPressed()) {
                player.getCurrentAnimationSet().setCurrentIndex(Player.RIGHT);
                player.getAnimation().start();
                player.setVelX(player.getMovingRate());
            }
        }
    }
}
