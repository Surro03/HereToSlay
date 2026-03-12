package it.univaq.ui;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaEroe;
import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;
import it.univaq.technical.*;

import java.util.List;
import java.util.Scanner;

public class UITerminale implements InterfacciaUtente, FinestraTemporaleObserver {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostraMenuInizioTurno(Fase faseAttuale, Player giocatoreAttivo, Boolean isEroiEmpty, int paRimasti) {
        {
            System.out.println("Turno di: " + giocatoreAttivo.getNome() + " | PA disponibili: " + paRimasti);
            System.out.println(giocatoreAttivo.getMano());
            if (faseAttuale instanceof FaseScelta) {
                System.out.println("\n--- Fase 1: Richiesta Mossa ---");
            }
            System.out.println("\n--- SCEGLI LA TUA MOSSA ---");
            System.out.printf("%-3s | %-30s | %-10s%n", "ID", "AZIONE", "COSTO PA");
            System.out.println("-".repeat(50));

            if (isEroiEmpty) {
                System.out.println("   Non hai Eroi da giocare nella tua mano   ");
            } else {
                System.out.println(" 1  | Gioca Carta Eroe               | 1 PA");
            }
            System.out.println(" 2  | Gioca Carta Oggetto            | 1 PA");
            System.out.println(" 3  | Gioca Carta Magia              | 1 PA");
            System.out.println(" 4  | Pesca Carta dal Mazzo          | 1 PA");
            System.out.println(" 5  | Utilizza Effetto Eroe          | 1 PA");
            System.out.println(" 6  | Attacca un Mostro              | 2 PA");
            System.out.println(" 7  | Scarta Mano e Pesca 5          | 3 PA");
            System.out.println("-".repeat(50));
        }
    }

    @Override
    public Integer chiediSelezioneMossa(Boolean errore) {
        int sceltaMossa;
        if (errore) {
            System.out.print("Devi scegliere una mossa valida.\n");
            sceltaMossa = scanner.nextInt();
            scanner.nextLine();
            return sceltaMossa;
        } else {
            System.out.print("Digita il numero della mossa: ");
            sceltaMossa = scanner.nextInt();
            scanner.nextLine();
            return sceltaMossa;
        }
    }

    @Override
    public void messaggioMossaSelezionata(int paRimasti, Boolean succeso, String mossa) {

        if (succeso) {
            System.out.println("\n--- "+ mossa+" eseguita con successo. ---");
        } else {
            System.out.println("\n--- Hai " + paRimasti + " Punti Azione, sono insufficienti per questa mossa!---");
        }

    }

    @Override
    public <T extends Carta> T scegliCartaDaGiocare(String tipoCarta, List<T> tipoCartaInMano, String nomePlayer) {
        T cartaScelta = null;
        if (tipoCarta.equals("CartaEroe")) {
            System.out.println("\n");
            System.out.println("SCEGLI L'EROE DA GIOCARE");
            System.out.println("-".repeat(40));
            System.out.printf("%-4s | %-18s | %-10s | %-5s%n", "NUMERO", "NOME", "CLASSE", "REQ.");
            System.out.println("-".repeat(40));
            for (int i = 0; i < tipoCartaInMano.size(); i++) {
                CartaEroe e = (CartaEroe) tipoCartaInMano.get(i);
                System.out.printf("[%d]    | %-18s | %-10s | %d+%n",
                        (i + 1),
                        e.getNome(),
                        e.getClasseEroe(),
                        e.getRequisito());
            }
            System.out.println("-".repeat(40));
            while (true) {
                System.out.print("Scegli il numero dell'eroe da giocare (1-" + tipoCartaInMano.size() + "): ");
                int sceltaCarta = scanner.nextInt();
                scanner.nextLine();
                if (sceltaCarta < 1 || sceltaCarta > tipoCartaInMano.size()) {
                    System.out.println("Devi digitare un numero valido compreso tra 1 e " + tipoCartaInMano.size() + ".");
                } else {
                    cartaScelta = tipoCartaInMano.get(sceltaCarta - 1);
                    System.out.println("\n"+ nomePlayer+ " gioca la carta: " + cartaScelta.getNome());
                    break;
                }
            }
        }
        return cartaScelta;
    }

    @Override
    public void richiestaSfida() {
        //In teoria qui andrebbe la fase sfida
        System.out.println("\n--- Fase 2: Finestra di Sfida ---");
        System.out.println("In attesa di reazioni dagli avversari...");
        // Simuliamo che nessuno giochi una CartaSfida, quindi scatta il timeout
        System.out.println("Ricevuto timeout! Nessuno ha giocato una carta Sfida.");
        System.out.println("La carta Eroe entra in gioco senza ostacoli.");
    }

    @Override
    public Boolean richiestaUtilizzoEffetto(String nomeCarta) {
        String scelta;
        while (true) {
            System.out.println("Vuoi provare ad attivare l'effetto di " + nomeCarta + "? (Si/No)");
            scelta = this.scanner.nextLine().trim();
            if (scelta.equalsIgnoreCase("Si")) {
                return true;
            }
            if (scelta.equalsIgnoreCase("No")) {
                return false;
            }
            System.out.println("[!] Input non valido, devi dire Si o No.");
        }
    }

    @Override
    public void rispostaUtilizzoEffetto(Boolean risposta) {
        if (risposta) {
            System.out.println("\n--- Fase 3: Finestra Modificatori ---");
        }else{
            System.out.println("Effetto non attivato, fine utilizzo PA");
        }
    }

    @Override
    public void risultatoTiroDadi(int risultato) {
        System.out.println("Il valore attuale del tiro è: " + risultato + ", inizio fase modificatori");
    }

    @Override
    public void punteggioIntermedio(Float punteggio) {
        System.out.println("Modificatore applicato! Nuovo punteggio provvisorio: " + punteggio);
    }

    @Override
    public void punteggiDefinitivi(Float risultato, String nomePlayer) {
        System.out.printf("Valore finale del tiro di %s: %+.0f%n", nomePlayer, risultato);
    }

    @Override
    public void esitoRequisito(Boolean esitoRequisito, String descrizioneEffetto) {
        System.out.println("\n--- Fase 4: Verifica Requisiti ---");
        if (esitoRequisito) {
            System.out.println("--- Esito positivo, puoi attivare l'effetto della carta! ---");
        }else{
            System.out.println("--- Esito negativo, non puoi attivare l'effetto");
        }
    }

    @Override
    public void messaggioFineTurno(String nomePlayer) {
        System.out.println("--- " + nomePlayer + "il tuo turno è terminato.");
    }

    @Override
    public void numClassiDiverse(int classiDiverse) {
        System.out.println("Classi uniche presenti nel party: " + classiDiverse);
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println(messaggio);
    }

    @Override
    public Boolean chiediSeGiocareModificatore(Player giocatore, int numModificatori) {
        String scelta;
        while (true) {
            System.out.println("\n" + giocatore.getNome() + ", hai " + numModificatori + " modificatore/i. Vuoi giocarne uno? (Si/No)");
            scelta = scanner.nextLine().trim();
            if (scelta.equalsIgnoreCase("Si")) return true;
            if (scelta.equalsIgnoreCase("No")) return false;
            System.out.println("[!] Errore: Devi rispondere Si o No.");
        }
    }

    @Override
    public CartaModificatore scegliModificatoreDaGiocare(List<CartaModificatore> disponibili) {
        System.out.println("\n--- SCEGLI UN MODIFICATORE DA GIOCARE ---");
        System.out.printf("%-8s | %-18s | %-10s | %-10s%n", "NUMERO", "TIPO", "VALORE +", "VALORE -");
        System.out.println("-".repeat(55));

        for (int i = 0; i < disponibili.size(); i++) {
            CartaModificatore mod = disponibili.get(i);
            String vPos = (mod.getValorePositivo() != null) ? String.format("%+.0f", mod.getValorePositivo()) : " / ";
            String vNeg = (mod.getValoreNegativo() != null) ? String.format("%.0f", mod.getValoreNegativo()) : " / ";
            System.out.printf("[%d]      | %-18s | %-10s | %-10s%n", (i + 1), "Modificatore", vPos, vNeg);
        }
        System.out.println("-".repeat(55));

        System.out.print("Digita il numero del modificatore: ");
        int sceltaCartaModif = scanner.nextInt();
        scanner.nextLine();

        return disponibili.get(sceltaCartaModif - 1);
    }

    @Override
    public Float scegliSegnoModificatore(CartaModificatore carta) {
        System.out.println("Scegli il valore della carta modificatore");
        System.out.println("1 Applica: " + carta.getValorePositivo());
        System.out.println("2 Applica: " + carta.getValoreNegativo());
        int sceltaSegno = scanner.nextInt();
        scanner.nextLine();
        while(true) {
            if (sceltaSegno == 1) return carta.getValorePositivo();
            if (sceltaSegno == 2) return carta.getValoreNegativo();
            else{
                System.out.println("Input non valido, inserire il numero del segno che si vuole applicare");
                System.out.print("> ");
            }
        }
    }

    @Override
    public Boolean chiediConfermaFineFase() {
        String confermaTermina;
        do {
            System.out.println("\n[?] Tutti hanno passato. Terminare la fase modificatori? (Si/No)");
            confermaTermina = scanner.nextLine().trim();
        } while (!confermaTermina.equalsIgnoreCase("Si") && !confermaTermina.equalsIgnoreCase("No"));

        return confermaTermina.equalsIgnoreCase("Si");
    }

    @Override
    public void messaggioVittoria(String nomePlayer, String causa) {
        System.out.println("--- " + nomePlayer +" vince per "+ causa+"! ---");
    }

    @Override
    public void timerStarted(int durata, Fase fase) {
        System.out.println("\n--- Avete " + durata + " secondi a testa per giocare una carta, avanti il prossimo!--- ");
    }

    @Override
    public void timerRestarting(int durata) {
        System.out.println("\n--- Timer resettato! Hai " + durata + " secondi per rispondere ---");
        System.out.print("> ");
    }

    @Override
    public void timerStopped(Fase fase) {
        System.out.println("\n---Tempo scaduto per la fase " + fase.getClass().getSimpleName() + "! ---");
        System.out.print("> ");
    }

    @Override
    public void timerInterrupted(Fase fase) {
        System.out.println("\n--- Timer interrotto dai giocatori, "+fase.getClass().getSimpleName()+" terminata ---");
    }
}

