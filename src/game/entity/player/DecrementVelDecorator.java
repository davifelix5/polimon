package game.entity.player;

public class DecrementVelDecorator extends BaseVelDecorator {
    public DecrementVelDecorator(IPlayerVelSetter previousVelSetter) {
        super(previousVelSetter);
    }

    public void setVel() {
        super.setVel();
        
        if (this.player.getVelX() > 1) {
            this.player.setVelX(this.player.getVelX() - 1);
        }
        else if (this.player.getVelX() < -1) {
            this.player.setVelX(this.player.getVelX() + 1);
        }

        if (this.player.getVelY() > 1) {
            this.player.setVelY(this.player.getVelY() - 1);
        }
        else if (this.player.getVelY() < -1) {
            this.player.setVelY(this.player.getVelY() + 1);
        }
    }
}
