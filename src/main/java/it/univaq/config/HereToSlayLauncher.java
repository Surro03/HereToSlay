package it.univaq.config;

import it.univaq.controller.HereToSlay;
import it.univaq.ui.UITerminale;

public class HereToSlayLauncher {

    // Nascondiamo il costruttore
    private HereToSlayLauncher() {}

    public static void avviaApplicazione() {

        // 1. SETUP DEL DOMINIO
        HereToSlay controller = HereToSlaySetup.setupGioco();

        // 2. SETUP DELLA UI (Istanzio la vista specifica)
        UITerminale gui = new UITerminale(controller);

        // 3. CABLAGGIO DEGLI EVENTI (Observer/Listener)
        controller.addObserver(gui);
        controller.addTimerObserver(gui);

        // 4. START!
        controller.iniziaPartita();
        gui.avviaLoopInput();
    }
}
