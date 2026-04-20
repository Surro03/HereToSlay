package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.controller.HereToSlaySetup;
import it.univaq.ui.UITerminale;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");

        // 1. Creo l'oggetto che si occuperà di preparare la partita
        HereToSlaySetup hereToSlaySetup = new HereToSlaySetup();

        // 2. Dico al setup di fare la sua magia. 
        // Lui imposterà la partita
        // Alla fine mi restituirà il Controller pronto all'uso.
        HereToSlay hereToSlay = hereToSlaySetup.setupGioco();

        //Creo la UI e la collego al Controllore
        UITerminale gui = new UITerminale(hereToSlay);
        hereToSlay.addObserver(gui);

        // 3. Accendiamo il motore
        hereToSlay.iniziaPartita();

        //4. Mettiamo la macchina in attesa che l'utente prema l'acceleratore
        gui.avviaLoopInput();
    }
}