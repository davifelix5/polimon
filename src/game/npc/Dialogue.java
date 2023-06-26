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
        double lineSize = 0;

        String[] words = dialogues.get(currentLine).split(" ");

        for (String w: words) {
            String finalWord = w + " ";
            int wordWidth = g.getFontMetrics().stringWidth(finalWord);
            lineSize += wordWidth;

            if (lineSize >= width - 32) {
                lineSize = 0; // zera o controle do espaço ocupado pela fonte
                b += 40; // pula para a próxima linha
                a =  x + Game.tileSize; // volta para o começo da linha
            }

            g.drawString(finalWord, a, b);
            a += wordWidth;
        }
    }

    public void render(Graphics g) {

        Color color = new Color(25, 25, 25, 200);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 35, 35);

        g.setColor(Color.white);
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
