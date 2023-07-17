package game.entity.player;

public class ReverseVelDecorator extends BaseVelDecorator {
    public ReverseVelDecorator(IPlayerVelSetter previousVelSetter) {
        super(previousVelSetter);
    }

    public void setVel() {
        super.setVel();
        this.player.setVelX(-this.player.getVelX());
        this.player.setVelY(-this.player.getVelY());
    }
}
