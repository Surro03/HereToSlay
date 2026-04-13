//package it.univaq.ui;
//
//import it.univaq.entity.CartaEroe;
//import it.univaq.entity.CartaModificatore;
//import it.univaq.controller.HereToSlay;
//import it.univaq.technical.GiocataModificatore;
//
//import java.util.List;
//import java.util.Scanner;
//
//public class UITerminale implements GameObserver {
//
//    private final Scanner scanner = new Scanner(System.in);
//
//    // Il nostro "cordone ombelicale" per mandare i dati al motore a stati!
//    private HereToSlay controller;
//
//    // Va chiamato subito dopo aver creato l'oggetto per collegarli
//    public void setController(HereToSlay controller) {
//        this.controller = controller;
//    }
//
//    @Override
//    public void mostraMessaggio(String messaggio) {
//        System.out.println(messaggio);
//    }
//
//    @Override
//    public void mostraTurnoGiocatore(String nomeGiocatore, int paRimasti) {
//        System.out.println("\n==================================");
//        System.out.println("TURNO DI: " + nomeGiocatore + " | PA RIMASTI: " + paRimasti);
//        System.out.println("==================================");
//    }
//
//    @Override
//    public void richiediSelezioneMossa(boolean puoGiocareEroe, boolean puoAttaccare, boolean puoPescare) {
//        System.out.println("\n--- SCEGLI LA TUA MOSSA ---");
//
//        if (puoGiocareEroe) System.out.println(" 1 | Gioca Carta Eroe (1 PA)");
//        if (puoAttaccare) System.out.println(" 6 | Attacca un Mostro (2 PA)");
//        if (puoPescare) System.out.println(" 7 | Pesca Carte (3 PA)");
//        System.out.println("99 | Passa il turno (Fine Turno)");
//
//        System.out.print("> Digita il numero: ");
//        int scelta = scanner.nextInt();
//        scanner.nextLine(); // Pulisce il buffer
//
//        // Invece di fare il return, SPINGIAMO l'input nel motore!
//        controller.riceviInputDaUI(scelta);
//    }
//
//    @Override
//    public void richiediSceltaCarta(List<CartaEroe> carteTraCuiScegliere) {
//        System.out.println("\n--- SCEGLI L'EROE DA GIOCARE ---");
//
//        if (carteTraCuiScegliere.isEmpty()) {
//            System.out.println("Non hai carte eroe in mano! Annullamento...");
//            controller.riceviInputDaUI(null);
//            return;
//        }
//
//        for (int i = 0; i < carteTraCuiScegliere.size(); i++) {
//            CartaEroe e = carteTraCuiScegliere.get(i);
//            System.out.printf("[%d] | %-18s | %-10s | %d+%n", (i + 1), e.getNome(), e.getClasseEroe(), e.getRequisito());
//        }
//        System.out.println("[0] | ANNULLA E TORNA INDIETRO");
//
//        System.out.print("> Scelta: ");
//        int scelta = scanner.nextInt();
//        scanner.nextLine();
//
//        if (scelta == 0) {
//            controller.riceviInputDaUI(null); // Segnale di annullamento
//        } else {
//            // Mando la carta vera e propria!
//            controller.riceviInputDaUI(carteTraCuiScegliere.get(scelta - 1));
//        }
//    }
//
//    @Override
//    public void richiediConfermaEffetto(String messaggioDomanda) {
//        System.out.println("\n[?] " + messaggioDomanda + " (Si/No)");
//        System.out.print("> ");
//        String scelta = scanner.nextLine().trim();
//
//        boolean vuoleAttivare = scelta.equalsIgnoreCase("Si");
//
//        // Mando un booleano al motore!
//        controller.riceviInputDaUI(vuoleAttivare);
//    }
//
//    @Override
//    public void richiediGiocataModificatori(float punteggioIniziale) {
//        System.out.println("\n=============================================");
//        System.out.println("⏳ FINESTRA MODIFICATORI APERTA! Punteggio dadi: " + punteggioIniziale);
//        System.out.println("Tutti i giocatori possono lanciare carte modificatore.");
//        System.out.println("=============================================");
//
//        // Essendo in console non possiamo fare veri timer simultanei senza impazzire.
//        // Simuliamo l'attesa con un menù manuale!
//        System.out.println("1 | Qualcuno gioca un modificatore");
//        System.out.println("2 | Nessuno gioca nulla (Scade il tempo / TIMEOUT)");
//        System.out.print("> Scelta: ");
//
//        int scelta = scanner.nextInt();
//        scanner.nextLine();
//
//        if (scelta == 2) {
//            // Simuliamo il timeout!
//            controller.riceviInputDaUI("TIMEOUT");
//        } else {
//            // Qui in un gioco completo mostreresti la lista delle carte di quel giocatore.
//            // Per ora creiamo una simulazione per far capire al motore cosa fare:
//            System.out.print("Simulazione: Quanto vale il modificatore? (+2, -1, ecc): ");
//            float valoreSimulato = scanner.nextFloat();
//            scanner.nextLine();
//
//            // Creo una carta finta solo per il test
//            CartaModificatore modGiocato = new CartaModificatore((int)valoreSimulato, Math.abs(valoreSimulato), "Mod Test");
//
//            boolean usaPositivo = valoreSimulato >= 0;
//
//            // Creo il pacchetto e lo invio!
//            GiocataModificatore pacchetto = new GiocataModificatore(modGiocato, usaPositivo);
//            controller.riceviInputDaUI(pacchetto);
//        }
//    }
//
//    @Override
//    public void aggiornaSchermataModificatori(float nuovoPunteggio) {
//        System.out.println(">>> AGGIORNAMENTO: Il punteggio ora è " + nuovoPunteggio + "!");
//        System.out.println("Il timer viene resettato a 10 secondi...");
//
//        // Richiamo manualmente lo stesso menù per simulare il loop del timer
//        richiediGiocataModificatori(nuovoPunteggio);
//    }
//}
package it.univaq.ui;

import it.univaq.controller.ControllerSubject;
import it.univaq.entity.*;
import it.univaq.technical.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UITerminale implements GameObserver, FinestraTemporaleObserver {

    private final Scanner scanner = new Scanner(System.in);
    private final ControllerSubject controller;
    private boolean giocoInEsecuzione = true;
    private Map<Integer, Integer> mappaIndiciUniversali;
    private StatoUI statoAttuale;
    private int numeroMassimoScelte;


    // Definiamo gli stati possibili della nostra interfaccia
    private enum StatoUI {
        MENU_PRINCIPALE,
        SCELTA_EROE,
        SCELTA_MAGIA,
        SCELTA_OGGETTO,
        SCELTA_EFFETTO,
    }

    public UITerminale(ControllerSubject controller) {
        this.controller = controller;
    }

    public void avviaLoopInput() {
        while (giocoInEsecuzione) {
            // Legge costantemente cosa digita l'utente
            String input = scanner.nextLine().trim();
            // In base allo stato o all'input, invia comandi al motore.
            if (statoAttuale == StatoUI.MENU_PRINCIPALE) {
                try {
                    int scelta = Integer.parseInt(input);
                    controller.verificaMossa(scelta);
                    switch (scelta) {
                        case 1:
                            controller.iniziaFlussoGiocaEroe();
                            break;
                        case 2:
                            controller.eseguiGiocaOggetto();
                            break;
                        case 3:
                            controller.eseguiGiocaMagia();
                            break;
                        case 4:
                            controller.eseguiPescaCarta();
                            break;
                        case 5:
                            controller.eseguiUtilizzaEffettoEroe();
                            break;
                        case 6:
                            controller.iniziaFlussoAttaccoMostro();
                            break;
                        case 7:
                            controller.eseguiScartaManoEPesca();
                            break;
                        default:
                            System.out.println("[!] Mossa non valida. Scegli un numero tra 1 e 7.");
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            } else if (this.statoAttuale == StatoUI.SCELTA_EROE) {
                int scelta = Integer.parseInt(input);
                if (scelta < 1 || scelta > this.numeroMassimoScelte) {
                    System.out.print("Digita un numero compreso tra 1 e " + this.numeroMassimoScelte + ": ");
                } else {
                    // Torna al menu principale
                    this.statoAttuale = StatoUI.MENU_PRINCIPALE;
                    // RECUPERA L'INDICE ASSOLUTO DALLA MAPPA!
                    int indiceRealeNellaMano = mappaIndiciUniversali.get(scelta);
                    // Passa l'indice universale al motore di gioco
                    controller.giocaCartaGenerale(indiceRealeNellaMano);
                }
            }
        }
    }

    @Override
    public void avviaPartita() {
        this.avviaLoopInput();
    }

    @Override
    public void inizioTurno(Player giocatoreAttivo, boolean isEroiEmpty, int paRimasti) {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Turno di: " + giocatoreAttivo.getNome() + " | PA disponibili: " + paRimasti);
        System.out.println(giocatoreAttivo.getMano());

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
        System.out.print("> Digita il numero della mossa: ");
    }

    @Override
    public void erroreSelezioneMossa(String errore) {
        System.out.print("\n--- " + errore + " ---\n");
        System.out.print("> ");
        this.avviaLoopInput();
    }

    @Override
    public void richiediSceltaCartaEroe(Mano mano) {
        // 1. Cambiamo lo stato della UI per bloccare i comandi del menu principale
        this.statoAttuale = StatoUI.SCELTA_EROE;
        System.out.println("\n--- SCEGLI L'EROE DA GIOCARE ---");
        System.out.printf("%-4s | %-18s | %-10s | %-5s%n", "NUM.", "NOME", "CLASSE", "REQ.");
        System.out.println("-".repeat(48));
        List<Carta> tutteLeCarte = mano.getCarteMano();

        // Puliamo la mappa delle scelte precedenti
        this.mappaIndiciUniversali.clear();
        int sceltaVisiva = 1;
        for (int i = 0; i < tutteLeCarte.size(); i++) {
            Carta c = tutteLeCarte.get(i);
            // Se la carta è un Eroe, la stampiamo e la mappiamo
            if (c instanceof CartaEroe e) {

                System.out.printf("[%d]  | %-18s | %-10s | %d+%n",
                        sceltaVisiva,
                        e.getNome(),
                        e.getClasseEroe(),
                        e.getRequisito());

                // Salviamo la corrispondenza: Scelta a schermo -> Posizione reale nella mano
                mappaIndiciUniversali.put(sceltaVisiva, i);
                sceltaVisiva++;
            }
        }
        System.out.println("-".repeat(48));
        this.numeroMassimoScelte = sceltaVisiva - 1;
        System.out.print("> Scegli il numero dell'eroe (1-" + this.numeroMassimoScelte + "): ");
    }

//    @Override
//    public <T extends Carta> T scegliCartaDaGiocare(String tipoCarta, List<T> tipoCartaInMano, String nomePlayer) {
//        T cartaScelta = null;
//        if (tipoCarta.equals("CartaEroe")) {
//            System.out.println("\n");
//            System.out.println("SCEGLI L'EROE DA GIOCARE");
//            System.out.println("-".repeat(40));
//            System.out.printf("%-4s | %-18s | %-10s | %-5s%n", "NUMERO", "NOME", "CLASSE", "REQ.");
//            System.out.println("-".repeat(40));
//            for (int i = 0; i < tipoCartaInMano.size(); i++) {
//                CartaEroe e = (CartaEroe) tipoCartaInMano.get(i);
//                System.out.printf("[%d]    | %-18s | %-10s | %d+%n",
//                        (i + 1),
//                        e.getNome(),
//                        e.getClasseEroe(),
//                        e.getRequisito());
//            }
//            System.out.println("-".repeat(40));
//            while (true) {
//                System.out.print("Scegli il numero dell'eroe da giocare (1-" + tipoCartaInMano.size() + "): ");
//                int sceltaCarta = scanner.nextInt();
//                scanner.nextLine();
//                if (sceltaCarta < 1 || sceltaCarta > tipoCartaInMano.size()) {
//                    System.out.println("Devi digitare un numero valido compreso tra 1 e " + tipoCartaInMano.size() + ".");
//                } else {
//                    cartaScelta = tipoCartaInMano.get(sceltaCarta - 1);
//                    System.out.println("\n"+ nomePlayer+ " gioca la carta: " + cartaScelta.getNome());
//                    break;
//                }
//            }
//        }
//        return cartaScelta;
//    }

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

