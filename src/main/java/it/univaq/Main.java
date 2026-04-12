package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.controller.HereToSlaySetup;

public class Main {
    public static void main(String[] args) {

        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");

        // 1. Creo l'oggetto che si occuperà di preparare la partita (senza parametri!)
        HereToSlaySetup hereToSlaySetup = new HereToSlaySetup();

        // 2. Dico al setup di fare la sua magia. 
        // Lui creerà le carte, i giocatori, il terminale, e collegherà la UI al Controller.
        // Alla fine mi restituirà il Controller pronto all'uso.
        HereToSlay hereToSlay = hereToSlaySetup.setupGioco();

        // 3. BOOM! Si parte.
        hereToSlay.iniziaPartita();
    }
}