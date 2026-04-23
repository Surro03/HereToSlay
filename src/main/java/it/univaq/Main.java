package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.controller.HereToSlaySetup;
import it.univaq.ui.UITerminale;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");

        HereToSlaySetup hereToSlaySetup = new HereToSlaySetup();

        // 1. Creo il motore di gioco
        HereToSlay hereToSlay = hereToSlaySetup.setupGioco();

        // 2. Creo la UI passandogli il backend
        UITerminale gui = new UITerminale(hereToSlay);

        // 3. Faccio i collegamenti
        hereToSlay.addObserver(gui);      // Iscrivo la UI ai messaggi del Turno
        hereToSlay.addTimerObserver(gui); // Iscrivo la UI al Timer

        // 4. Accendiamo il motore
        hereToSlay.iniziaPartita();

        // 5. Mettiamo il gioco in attesa che l'utente scriva
        gui.avviaLoopInput();
    }
}