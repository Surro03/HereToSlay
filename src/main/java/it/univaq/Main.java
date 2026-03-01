package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.entity.*;
import it.univaq.technical.*;
import it.univaq.ui.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. INIZIALIZZAZIONE SISTEMA
        Scanner scanner = new Scanner(System.in);
        
        // Creazione Giocatori
        Player p1 = new Player(1, "Sfidante Oscuro");
        Player p2 = new Player(2, "Eroe della Luce");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        
        // Configurazione Tavolo e Party (come da SD Gioca Carta Eroe [cite: 51, 82])
        Tavolo tavolo = new Tavolo();
        // Nota: Nel codice reale questi verrebbero inizializzati nel costruttore di Tavolo o Party
        
        // Inizializzazione Turno con 3 Punti Azione (PA) [cite: 3, 12]
        List<Fase> pilaFasi = new ArrayList<>();
        pilaFasi.add(new FaseGiocaCarta()); // Fase iniziale
        Turno turnoAttuale = new Turno(pilaFasi, p1);
        HereToSlay controller = new HereToSlay(2, 1,4, players, pilaFasi);
        // (Simuliamo l'iniezione delle dipendenze nel controller tramite riflessione o setter se necessario)
        
        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");
        System.out.println("Turno di: " + p1.getNome() + " | PA disponibili: " + turnoAttuale.checkPaRimasti());

        // 2. ESECUZIONE AZIONE: Gioca Carta Eroe (Mossa 1) [cite: 9, 21]
        System.out.println("\n--- Fase 1: Richiesta Mossa ---");
        // Il controller verifica i PA e scala il punto [cite: 10, 11]
        controller.richiestaMossa(1); 
        
        // 3. SELEZIONE E GIOCATA CARTA [cite: 54]
        CartaEroe bardo = new CartaEroe(0, 7, "Bardo Canterino", "Pesca una carta", 1, ClasseEroe.BARDO);
        System.out.println("[AZIONE] " + p1.getNome() + " gioca la carta: " + bardo.getNome());
        controller.giocoCarta(bardo); // La carta viene salvata nella FaseGiocaCarta [cite: 53, 54]

        // 4. FINESTRA DI SFIDA (Simulazione Timeout) [cite: 59, 65]
        System.out.println("\n--- Fase 2: Finestra di Sfida ---");
        System.out.println("In attesa di reazioni dagli avversari...");
        // Simuliamo che nessuno giochi una CartaSfida, quindi scatta il timeout [cite: 65, 74]
        controller.timeout();

        // 5. ATTIVAZIONE EFFETTO EROE [cite: 80, 114]
        System.out.println("\n--- Fase 3: Utilizzo Effetto ---");
        System.out.println("Vuoi provare ad attivare l'effetto di " + bardo.getNome() + "? (Si/No)");
        String scelta = scanner.nextLine(); 

        if (scelta.equalsIgnoreCase("Si")) {
            // rispostaUtente("Si") crea la FaseEffetto e tira i dadi [cite: 132, 136]
            String risultatoLancio = controller.rispostaUtente("Si");
            System.out.println("[DADI] " + risultatoLancio);

            // 6. GESTIONE MODIFICATORI (Simulazione Punteggio Finale) [cite: 101, 134]
            // Supponiamo che dopo la FaseModificatori il punteggio sia 8 (Requisito era 7)
            int punteggioFinale = 8;
            System.out.println("\n--- Fase 4: Verifica Requisiti ---");
            String esitoEffetto = controller.checkAttivazioneEffetto(punteggioFinale); // [cite: 112, 146]
            System.out.println("[ESITO] " + esitoEffetto);
        }

        // 7. VERIFICA CONDIZIONI DI VITTORIA [cite: 78, 84]
        System.out.println("\n--- Fase Finale: Verifica Vittoria ---");
        tavolo.checkVittoria(p1); // Conta le classi uniche nel Party [cite: 84, 85]

        System.out.println("\nPA rimanenti per " + p1.getNome() + ": " + turnoAttuale.checkPaRimasti());
        System.out.println("=================================");
    }
}