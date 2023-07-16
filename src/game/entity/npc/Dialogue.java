package game.entity.npc;

import game.Game;
import game.utilities.Fontes;

import java.awt.*;

public class Dialogue {

    private final String[] dialogues;
    private int currentLine;
    int x, y, width, height;
    Font dialogueFont;

    public Dialogue(String[] dialogues, Font dialogueFont) {
        this.dialogues = dialogues;
        this.x = Game.tileSize * 7; // posição do diálogo na posição x
        this.y = Game.tileSize / 2; // posição do diálogo da direção y
        this.width =  Game.width - (Game.tileSize * 12); // largura da janela de diálogo
        this.height = Game.tileSize * 5; // altura da janela de diálogo
        this.dialogueFont = dialogueFont;
    }

    public void nextLine(){
       currentLine++;
    }

    public void render(Graphics g) {
        Color color = new Color(25, 25, 25, 200);
        g.setColor(color);
        g.fillRoundRect(x, y, width, height, 35, 35);

        g.setColor(Color.white);
        g.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

        g.setFont(dialogueFont);

        Fontes.renderText(g, dialogues[currentLine], x, y, width);
    }

    public String[] getDialogues() {
        return dialogues;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void reset() {
        currentLine = 0;
    }
}
