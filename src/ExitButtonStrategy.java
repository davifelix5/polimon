public class ExitButtonStrategy implements MouseInteractionStrategy {

    @Override
    public void onPress(GamePanel game) {
        System.exit(0);
    }
}
