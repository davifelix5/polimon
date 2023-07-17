package game.entity.player;

public class BaseVelDecorator implements IPlayerVelSetter {
    protected IPlayerVelSetter previousVelSetter;
    protected Player player;

    public BaseVelDecorator(IPlayerVelSetter previousVelSetter) {
        this.previousVelSetter = previousVelSetter;
        this.player = previousVelSetter.getPlayer();
    }

    @Override
    public void setVel() {
        this.previousVelSetter.setVel();
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
