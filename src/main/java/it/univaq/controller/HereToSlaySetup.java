package it.univaq.controller;

import it.univaq.entity.*;
import it.univaq.ui.UITerminale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HereToSlaySetup {

    public HereToSlaySetup() {}

    public HereToSlay setupGioco() {
        System.out.println("=== SETUP GIOCO ===");

        // 1. Crea i giocatori con le loro mani pre-impostate per il test
        List<Player> playerList = this.setupPlayers(2, null);
        
        // 2. Crea l'interfaccia grafica
        UITerminale uiTerminale = new UITerminale();
        
        // 3. Crea il Controller usando il NUOVO costruttore
        HereToSlay controller = new HereToSlay(playerList, uiTerminale);
        
        // ---> 4. IL COLLEGAMENTO MAGICO <---
        // Diciamo alla UI qual è il controller a cui deve spedire i messaggi dei bottoni!
        uiTerminale.setController(controller);

        // N.B: Abbiamo rimosso GeneratoreDiEventi e FinestraTemporaleObserver!
        // Ora il gioco non si bloccherà più con Thread strani, fa tutto la Pila.

        return controller;
    }

    public List<Carta> mischiaMazzo(){
        //TODO prende le carte dalla memoria e le mischia, poi le carica sul tavolo
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Player> setupPlayers(int numPlayers, List<Carta> mazzoMischiato){
        
        List<Carta> manoGiocatore1 = new ArrayList<>(Arrays.asList(
                // 1. Un Eroe dal costo 1, requisito 6
                new CartaEroe(0, 6, "Ascia Sfascia", "Distruggi un eroe avversario", 1, ClasseEroe.GUERRIERO),

                // 2. Un Modificatore doppio (+1 / -1)
                new CartaModificatore(1.0f, -1.0f, "Aggiungi +1 o sottrai -1 al tiro"),

                // 3. Una Carta Sfida generica
                new CartaSfida(),

                // 4. Un altro Eroe (Costo 1, requisito 7)
                new CartaEroe(0, 7, "Ezio Miaoditore", "Pesca due carte dal mazzo", 1, ClasseEroe.LADRO),

                // 5. Un Modificatore singolo (+2)
                new CartaModificatore(2, 2.0f, "Aggiungi +2 al tuo tiro")
        ));

        List<Carta> manoGiocatore2 = new ArrayList<>(Arrays.asList(
                // 1. Un Eroe (Costo 1, requisito 5)
                new CartaEroe(0, 5, "Gatto Ladro", "Ruba una carta dalla mano di un giocatore", 1, null),

                // 2. Una Carta Sfida con una classe specifica (es. 2)
                new CartaSfida(2),

                // 3. Un Modificatore molto forte (+3 / -3)
                new CartaModificatore(3.0f, -3.0f, "Aggiungi +3 o sottrai -3 al tiro"),

                // 4. Un'altra Carta Sfida generica
                new CartaSfida(),

                // 5. Un Eroe (Costo 1, requisito 8)
                new CartaEroe(0, 8, "Fenicottero Bardo", "Tira di nuovo un dado", 1, null)
        ));

        Mano manoP1 = new Mano(manoGiocatore1);
        Mano manoP2 = new Mano(manoGiocatore2);

        // Creazione Giocatori
        Player p1 = new Player(1, "Luca Avenia", manoP1);
        Player p2 = new Player(2, "Alessandro Salvitti", manoP2);
        
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        
        return players;
    }
}