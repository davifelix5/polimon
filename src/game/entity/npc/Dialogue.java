package game.entity.npc;

import game.Game;
import game.ui.handlers.KeyHandler;

import java.awt.*;

public class Dialogue {

    private final String[] dialogues;
    private final KeyHandler keyHandler;
    private int currentLine;
    int x, y, width, height;
    Font dialogueFont;
    private boolean changed = false;

    private boolean activated = false;

    public Dialogue(String[] dialogues, KeyHandler keyHandler, Font dialogueFont) {
        this.dialogues = dialogues;
        this.keyHandler = keyHandler;
        this.x = Game.tileSize * 7; // posição do diálogo na posição x
        this.y = Game.tileSize / 2; // posição do diálogo da direção y
        this.width =  Game.width - (Game.tileSize * 12); // largura da janela de diálogo
        this.height = Game.tileSize * 5; // altura da janela de diálogo
        this.dialogueFont = dialogueFont;
    }

    private void nextLine(){
       currentLine++;
    }

    private void displayDialogue(Graphics g) {
        int wordX = x + Game.tileSize;
        int wordY = y + Game.tileSize;
        double lineSize = 0;

        String[] words = dialogues[currentLine].split(" ");

        for (String w: words) {
            String finalWord = w + " ";
            int wordWidth = g.getFontMetrics().stringWidth(finalWord);
            lineSize += wordWidth;

            if (lineSize >= width - 32) {
                lineSize = 0; // zera o controle do espaço ocupado pela fonte
                wordY += 40; // pula para a próxima linha
                wordX =  x + Game.tileSize; // volta para o começo da linha
            }

            g.drawString(finalWord, wordX, wordY);
            wordX += wordWidth;
        }
    }

    public void render(Graphics g) {
        if (activated) {
            Color color = new Color(25, 25, 25, 200);
            g.setColor(color);
            g.fillRoundRect(x, y, width, height, 35, 35);

            g.setColor(Color.white);
            g.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

            g.setFont(dialogueFont);

            displayDialogue(g);
        }
    }

    public void tick(){
        if(keyHandler.spacePressed && activated){

            if (!changed) {
                changed = true;
                if (currentLine < dialogues.length - 1)
                    nextLine();
                else if (currentLine == dialogues.length - 1) {
                    activated = false;
                    reset();
                }
            }

        } else {
            changed = false;
        }

    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void reset() {
        currentLine = 0;
    }
}
