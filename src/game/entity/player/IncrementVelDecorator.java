package game.entity.player;

public class IncrementVelDecorator extends BaseVelDecorator {
    public IncrementVelDecorator(IPlayerVelSetter previousVelSetter) {
        super(previousVelSetter);
    }

    public void setVel() {
        super.setVel();

        if (this.player.getVelX() > 0 && this.player.getVelX() < 8) {
            this.player.setVelX(this.player.getVelX() + 1);
        }
        else if (this.player.getVelX() < 0 && this.player.getVelX() > -8) {
            this.player.setVelX(this.player.getVelX() - 1);
        }

        if (this.player.getVelY() > 0 && this.player.getVelX() < 8) {
            this.player.setVelY(this.player.getVelY() + 1);
        }
        else if (this.player.getVelY() < 0 && this.player.getVelX() > -8) {
            this.player.setVelY(this.player.getVelY() - 1);
        }
    }
}
