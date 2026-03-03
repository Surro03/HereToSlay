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
        Player p1 = new Player(1, "Luca Avenia");
        Player p2 = new Player(2, "Alessandro Salvitti");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        
        // Inizializzazione Turno con 3 Punti Azione (PA) [cite: 3, 12]
        HereToSlay controller = new HereToSlay(2, 1,4, players);
        
        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");
        System.out.println("Scegli il tuo Giocatore: ");
        //String sceltaGiocatore = scanner.nextLine();
        System.out.println("Turno di: " + p1.getNome() + " | PA disponibili: " + controller.getPaRimasti());

        // 2. ESECUZIONE AZIONE: Gioca Carta Eroe (Mossa 1) [cite: 9, 21]
        List<Carta> manoGiocatore1 = new ArrayList<>(Arrays.asList(
                // 1. Un Eroe dal costo 1, requisito 6
                new CartaEroe(0, 6, "Orso Guerriero", "Distruggi un eroe avversario", 1, null),

                // 2. Un Modificatore doppio (+1 / -1)
                new CartaModificatore( 1.0f, -1.0f, "Aggiungi +1 o sottrai -1 al tiro"),

                // 3. Una Carta Sfida generica
                new CartaSfida(),

                // 4. Un altro Eroe (Costo 1, requisito 7)
                new CartaEroe(0, 7, "Volpe Magica", "Pesca due carte dal mazzo", 1, null),

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
        System.out.println("\n--- Fase 1: Richiesta Mossa ---");
        // Il controller verifica i PA e scala il punto [cite: 10, 11]


        int sceltaMossa = scanner.nextInt();
        controller.richiestaMossa(sceltaMossa);
        
        // 3. SELEZIONE E GIOCATA CARTA [cite: 54]
        CartaEroe bardo = new CartaEroe(0, 7, "Bardo Canterino", "Pesca una carta", 1, ClasseEroe.BARDO);
        System.out.println("[AZIONE] " + p1.getNome() + " gioca la carta: " + bardo.getNome());
        controller.giocaCarta(bardo); // La carta viene salvata nella FaseGiocaCarta [cite: 53, 54]

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
        controller.checkVittoria(p1); // Conta le classi uniche nel Party [cite: 84, 85]

        System.out.println("\nPA rimanenti per " + p1.getNome() + ": " + controller.getPaRimasti());
        System.out.println("=================================");
    }
}