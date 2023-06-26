package game.npc;

import game.Game;
import game.handlers.KeyHandler;

import java.awt.*;
import java.util.ArrayList;

public class Dialogue {

    private final ArrayList<String> dialogues;
    private final KeyHandler keyHandler;
    private int currentLine;
    int x, y, width, height;
    Font dialogueFont;
    private boolean changed = false;
    public Dialogue(ArrayList<String> dialogues, KeyHandler keyHandler, int x, int y, int width, int height, Font dialogueFont) {
        this.dialogues = dialogues;
        this.keyHandler = keyHandler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dialogueFont = dialogueFont;
    }
    private void nextLine(){
       currentLine++;
    }
    private void displayDialogue(Graphics g) {
        int a = x + Game.tileSize;
        int b = y + Game.tileSize;

        if (dialogues.get(currentLine).length() * dialogueFont.getSize() > width - Game.tileSize) {
            for (int i = 0; i < dialogues.get(currentLine).length(); i += 54) {
                String aux = dialogues.get(currentLine).substring(i, Game.clamp(i + 54, 0, dialogues.get(currentLine).length()));
                g.drawString(aux, a, b);
                b += 40;
            }
        }
        else {
            g.drawString(dialogues.get(currentLine), a, b);
        }
    }

    public void render(Graphics g) {

        Color color = new Color(25, 25, 25, 200);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 35, 35);

        Color color2 = new Color(255, 255, 255);
        g.setColor(color2);
        g.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

        g.setFont(dialogueFont);

        displayDialogue(g);
    }

    public void tick(){
        if(keyHandler.spacePressed){
            if (currentLine < dialogues.size() - 1 && !changed) {
                nextLine();
                changed = true;
            }
        } else {
            changed = false;
        }
    }
}
