import java.awt.*;

public class Button implements MouseInteraction {

    String title;
    private final int posX, posY;
    private final int width, height;

    private final MouseInteractionStrategy pressStrategy;


    public Button(String title, int posX, int posY, int width, int height, MouseHandler mouse, MouseInteractionStrategy pressStrategy) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
        mouse.addElement(this);
        this.pressStrategy = pressStrategy;
    }
    @Override
    public MouseInteractionStrategy getInteractionStrategy() {
        return pressStrategy;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getPosX() {
        return posX;
    }

    @Override
    public int getPosY() {
        return posY;
    }

    public void render(Graphics g) {
        int fontSize = 26;
        Font h2 = new Font("arial", Font.PLAIN, fontSize);
        g.setFont(h2);
        g.setColor(Color.white);
        g.drawString(title, (640 - (5 * h2.getSize() / 2)) / 2, (posY + height / 2) + fontSize / 2);
        g.drawRect(posX, posY, width, height);
    }

}
