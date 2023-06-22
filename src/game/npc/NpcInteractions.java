package game.npc;

import java.util.ArrayList;

public class NpcInteractions {

    public ArrayList<String> dialogues = new ArrayList<>();

    private void setDialogues(ArrayList<String> dialogues){
        dialogues.set(0, "Olá, Aventureiro !");
        dialogues.set(1, "Bem vindo a USP !");
        dialogues.set(2, "Serei seu guia inicial nessa nova jornada que o aguarda");
        dialogues.set(3, "Espero que aproveite seu tempo aqui, faça amigos e se divirta");
        dialogues.set(4, "Podemos começar ?");
    }



}
