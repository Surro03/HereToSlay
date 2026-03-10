package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.entity.*;
import it.univaq.ui.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. INIZIALIZZAZIONE SISTEMA
        Scanner scanner = new Scanner(System.in);
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


        // Inizializzazione Turno con 3 Punti Azione (PA) [cite: 3, 12]
        HereToSlay controller = new HereToSlay(2, 1, 4, players);
        GeneratoreDiEventi generatoreDiEventi = new GeneratoreDiEventi();
        controller.setGeneratoreDiEventi(generatoreDiEventi);

        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");
        //System.out.println("Scegli il tuo Giocatore: ");
        //String sceltaGiocatore = scanner.nextLine();
        //while (controller.getPaRimasti() > 0) {
            System.out.println("Turno di: " + controller.getGiocatoreAttivo().getNome() + " | PA disponibili: " + controller.getPaRimasti());
            System.out.println(controller.getGiocatoreAttivo().getMano());

            System.out.println("\n--- Fase 1: Richiesta Mossa ---");
            System.out.println("\n=== SCEGLI LA TUA MOSSA ===");
            System.out.printf("%-3s | %-30s | %-10s%n", "ID", "AZIONE", "COSTO PA");
            System.out.println("-------------------------------------------------------");
            System.out.printf(" 1  | %-30s | 1 PA%n", "Gioca Carta Eroe");
            System.out.printf(" 2  | %-30s | 1 PA%n", "Gioca Carta Oggetto");
            System.out.printf(" 3  | %-30s | 1 PA%n", "Gioca Carta Magia");
            System.out.printf(" 4  | %-30s | 1 PA%n", "Pesca Carta dal Mazzo");
            System.out.printf(" 5  | %-30s | 1 PA%n", "Utilizza Effetto Eroe");
            System.out.printf(" 6  | %-30s | 2 PA%n", "Attacca un Mostro");
            System.out.printf(" 7  | %-30s | 3 PA%n", "Scarta Mano e Pesca 5");
            System.out.println("-------------------------------------------------------");
            System.out.print("Digita il numero della mossa: ");
            int sceltaMossa = scanner.nextInt();
            scanner.nextLine();
            controller.sceltaMossa(sceltaMossa);
            System.out.println("\n--- Fase Finale: Verifica Vittoria ---");
            controller.checkVittoria(p1); // Conta le classi uniche nel Party [cite: 84, 85]
            System.out.println("\nPA rimanenti per " + p1.getNome() + ": " + controller.getPaRimasti());
            System.out.println("=================================");
        }
   // }
}