package game.state;

import game.handlers.MouseHandler;
import game.state.IState;
import game.state.IStateManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class CombatScreen implements IState {
    IStateManager stateManager;
    MouseHandler mouse;
    BufferedImage backgroundImage;
    BufferedImage combatHUD;
    public CombatScreen(MouseHandler mouse, IStateManager stateManager) {
        this.mouse = mouse;
        this.stateManager = stateManager;
        loadImages();
    }

    public void tick() {

    }

    public void render(Graphics g) {
    g.drawImage(backgroundImage, 0,0, null);
    g.drawImage(combatHUD,0, 640-196,null);
    }

    public void loadImages() {
        try {
            this.backgroundImage = ImageIO.read(new FileInputStream("src/game/res/fotos/combatScreen.jpg"));
            this.combatHUD = ImageIO.read(new FileInputStream("src/game/res/fotos/combatHUD.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IStateManager getStateManager() {
        return stateManager;
    }
}
