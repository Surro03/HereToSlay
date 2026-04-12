package it.univaq.ui;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.CartaModificatore;
import it.univaq.controller.HereToSlay;
import it.univaq.technical.GiocataModificatore;

import java.util.List;
import java.util.Scanner;

public class UITerminale implements InterfacciaUtente {

    private final Scanner scanner = new Scanner(System.in);
    
    // Il nostro "cordone ombelicale" per mandare i dati al motore a stati!
    private HereToSlay controller; 

    // Va chiamato subito dopo aver creato l'oggetto per collegarli
    public void setController(HereToSlay controller) {
        this.controller = controller;
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println(messaggio);
    }

    @Override
    public void mostraTurnoGiocatore(String nomeGiocatore, int paRimasti) {
        System.out.println("\n==================================");
        System.out.println("TURNO DI: " + nomeGiocatore + " | PA RIMASTI: " + paRimasti);
        System.out.println("==================================");
    }

    @Override
    public void richiediSelezioneMossa(boolean puoGiocareEroe, boolean puoAttaccare, boolean puoPescare) {
        System.out.println("\n--- SCEGLI LA TUA MOSSA ---");
        
        if (puoGiocareEroe) System.out.println(" 1 | Gioca Carta Eroe (1 PA)");
        if (puoAttaccare) System.out.println(" 6 | Attacca un Mostro (2 PA)");
        if (puoPescare) System.out.println(" 7 | Pesca Carte (3 PA)");
        System.out.println("99 | Passa il turno (Fine Turno)");
        
        System.out.print("> Digita il numero: ");
        int scelta = scanner.nextInt();
        scanner.nextLine(); // Pulisce il buffer
        
        // Invece di fare il return, SPINGIAMO l'input nel motore!
        controller.riceviInputDaUI(scelta);
    }

    @Override
    public void richiediSceltaCarta(List<CartaEroe> carteTraCuiScegliere) {
        System.out.println("\n--- SCEGLI L'EROE DA GIOCARE ---");
        
        if (carteTraCuiScegliere.isEmpty()) {
            System.out.println("Non hai carte eroe in mano! Annullamento...");
            controller.riceviInputDaUI(null);
            return;
        }

        for (int i = 0; i < carteTraCuiScegliere.size(); i++) {
            CartaEroe e = carteTraCuiScegliere.get(i);
            System.out.printf("[%d] | %-18s | %-10s | %d+%n", (i + 1), e.getNome(), e.getClasseEroe(), e.getRequisito());
        }
        System.out.println("[0] | ANNULLA E TORNA INDIETRO");
        
        System.out.print("> Scelta: ");
        int scelta = scanner.nextInt();
        scanner.nextLine();

        if (scelta == 0) {
            controller.riceviInputDaUI(null); // Segnale di annullamento
        } else {
            // Mando la carta vera e propria!
            controller.riceviInputDaUI(carteTraCuiScegliere.get(scelta - 1));
        }
    }

    @Override
    public void richiediConfermaEffetto(String messaggioDomanda) {
        System.out.println("\n[?] " + messaggioDomanda + " (Si/No)");
        System.out.print("> ");
        String scelta = scanner.nextLine().trim();
        
        boolean vuoleAttivare = scelta.equalsIgnoreCase("Si");
        
        // Mando un booleano al motore!
        controller.riceviInputDaUI(vuoleAttivare);
    }

    @Override
    public void richiediGiocataModificatori(float punteggioIniziale) {
        System.out.println("\n=============================================");
        System.out.println("⏳ FINESTRA MODIFICATORI APERTA! Punteggio dadi: " + punteggioIniziale);
        System.out.println("Tutti i giocatori possono lanciare carte modificatore.");
        System.out.println("=============================================");
        
        // Essendo in console non possiamo fare veri timer simultanei senza impazzire.
        // Simuliamo l'attesa con un menù manuale!
        System.out.println("1 | Qualcuno gioca un modificatore");
        System.out.println("2 | Nessuno gioca nulla (Scade il tempo / TIMEOUT)");
        System.out.print("> Scelta: ");
        
        int scelta = scanner.nextInt();
        scanner.nextLine();

        if (scelta == 2) {
            // Simuliamo il timeout!
            controller.riceviInputDaUI("TIMEOUT");
        } else {
            // Qui in un gioco completo mostreresti la lista delle carte di quel giocatore.
            // Per ora creiamo una simulazione per far capire al motore cosa fare:
            System.out.print("Simulazione: Quanto vale il modificatore? (+2, -1, ecc): ");
            float valoreSimulato = scanner.nextFloat();
            scanner.nextLine();
            
            // Creo una carta finta solo per il test
            CartaModificatore modGiocato = new CartaModificatore((int)valoreSimulato, Math.abs(valoreSimulato), "Mod Test");
            
            boolean usaPositivo = valoreSimulato >= 0;
            
            // Creo il pacchetto e lo invio!
            GiocataModificatore pacchetto = new GiocataModificatore(modGiocato, usaPositivo);
            controller.riceviInputDaUI(pacchetto);
        }
    }

    @Override
    public void aggiornaSchermataModificatori(float nuovoPunteggio) {
        System.out.println(">>> AGGIORNAMENTO: Il punteggio ora è " + nuovoPunteggio + "!");
        System.out.println("Il timer viene resettato a 10 secondi...");
        
        // Richiamo manualmente lo stesso menù per simulare il loop del timer
        richiediGiocataModificatori(nuovoPunteggio);
    }
}