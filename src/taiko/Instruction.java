/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taiko;

/**
 *
 * @author GHackAnonymous
 */
public class Instruction {
    
   private String englishInstruction;
   private String spanishInstruction;
   private String basqueInstruction;
   private String italianInstruction;

   public Instruction() {
       englishInstruction = "The objective of this game is to press the "
               + "appropriate arrow when the music notes reach the bottom of "
               + "the screen and they are aligned with the circular mark. "
               + "Depending from which side they arrive you will have to press "
               + "the right or left arrow.";
       spanishInstruction = "El objetivo de este juego es pulsar la flecha "
               + "correspondiente cuando las notas de música llegan abajo de "
               + "la pantalla y están alineados con la marca circular. "
               + "Dependiendo por que lado llegue la nota tendrás que pulsar "
               + "la flecha derecha o la izquierda.";
       basqueInstruction = "Joko honen helburua, musika notak pantailaren "
               + "behera heltzen direnean eta marka borobilarekin lerrokatuta "
               + "dagoenean, gezi egokia sakatzea da. Nota heldu den aldearen "
               + "arabera eskubiko gezia edo ezkerrekoa sakatu beharko duzu.";
       italianInstruction = "L'obiettivo di questo gioco e' premere il tasto "
    		   + "appropriato quando le note della musica raggiungono il fondo "
    		   + "dello schermo e sono allineate con i pulsanti circolari. "
    		   + "A seconda di quale lato arrivino bisognera' premere il tasto "
    		   + "destro o sinistro";
    } 
   public Instruction(String englishInstruction, String spanishInstruction, String basqueInstruction, String italianInstruction) {
        this.englishInstruction = englishInstruction;
        this.spanishInstruction = spanishInstruction;
        this.basqueInstruction = basqueInstruction;
        this.italianInstruction = italianInstruction;
    }

    public String getItalianInstruction() {
	return italianInstruction;
}
public void setItalianInstruction(String italianInstruction) {
	this.italianInstruction = italianInstruction;
}
	public String getEnglishInstruction() {
        return englishInstruction;
    }

    public void setEnglishInstruction(String englishInstruction) {
        this.englishInstruction = englishInstruction;
    }

    public String getSpanishInstruction() {
        return spanishInstruction;
    }

    public void setSpanishInstruction(String spanishInstruction) {
        this.spanishInstruction = spanishInstruction;
    }

    public String getBasqueInstruction() {
        return basqueInstruction;
    }

    public void setBasqueInstruction(String basqueInstruction) {
        this.basqueInstruction = basqueInstruction;
    }
 
}
