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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UITerminale implements GameObserver, FinestraTemporaleObserver {

    private final Scanner scanner = new Scanner(System.in);
    private final ControllerSubject controller;
    private final boolean giocoInEsecuzione = true;
    private final Map<Integer, Integer> mappaIndiciUniversali = new HashMap<>();
    private final Map<Integer, GiocataGiocatore> mappaEffettiModificatore = new HashMap<>();
    private StatoUI statoAttuale;
    private int numeroMassimoScelte;


    public UITerminale(ControllerSubject controller) {
        this.controller = controller;
    }

    public void avviaLoopInput() {
        while (giocoInEsecuzione) {

            // 1. Legge costantemente cosa digita l'utente
            String input = scanner.nextLine().trim();

            // 2. Switch sullo Stato della UI
            switch (this.statoAttuale) {

                case SCELTA_MOSSA:
                    gestisciInputMenuPrincipale(input);
                    break;

                case SCELTA_EROE:
                    gestisciInputSceltaEroe(input);
                    break;

                case SCELTA_MAGIA:
                    gestisciInputSceltaMagia(input);
                    break;

                case SCELTA_OGGETTO:
                    gestisciInputSceltaOggetto(input);
                    break;

                case CONFERMA_EFFETTO:
                    gestisciInputConfermaEffetto(input);
                    break;

                case RISPOSTA_SFIDA:
                    gestisciInputRispostaSfida(input);
                    break;

                case SCELTA_MODIFICATORE_SFIDA:
                    gestisciInputRispostaSceltaModificatoreSfida(input);
                    break;

                case SCELTA_MODIFICATORE:
                    gestisciInputRispostaSceltaModificatoreNormale(input);
                    break;

                case ATTESA_EFFETTO_MODIFICATORE:
                    gestisciInputAttesaEffettoModificatore(input);
                    break;

                default:
                    System.out.println("[!] Errore critico: Stato UI sconosciuto.");
                    break;
            }
        }
    }

    private void gestisciInputAttesaEffettoModificatore(String input) {
        try {
            int scelta = Integer.parseInt(input);

            if (scelta < 1 || scelta > this.numeroMassimoScelte) {
                System.out.print("[!] Digita un numero compreso tra 1 e " + this.numeroMassimoScelte + ": ");
                return;
            }

            // Vado a pescare il comando esatto associato a quel numero
            GiocataGiocatore giocata = this.mappaEffettiModificatore.get(scelta);

            // Lo invio al controller
            this.controller.sceltaBersaglioEffettoModificatore(giocata);

        } catch (NumberFormatException e) {
            System.out.print("[!] Devi inserire un NUMERO: ");
        }
    }

    private void gestisciInputRispostaSceltaModificatoreSfida(String input) {
        try {
            int scelta = Integer.parseInt(input);

            if (scelta < 0 || scelta > this.numeroMassimoScelte) {
                System.out.print("[!] Digita un numero compreso tra 0 e " + this.numeroMassimoScelte + ": ");
            } else {
                if (scelta == 0){
                    controller.scegliCarta(null);
                }else {
                    int indiceRealeNellaMano = mappaIndiciUniversali.get(scelta);
                    // Richiama il metodo esplicito per le carte
                    controller.scegliCarta(indiceRealeNellaMano);
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("[!] Devi inserire un NUMERO: ");
        }
    }

    private void gestisciInputRispostaSceltaModificatoreNormale(String input) {
        try {
            int scelta = Integer.parseInt(input);

            if (scelta < 0 || scelta > this.numeroMassimoScelte) {
                System.out.print("[!] Digita un numero compreso tra 0 e " + this.numeroMassimoScelte + ": ");
            } else {
                if (scelta == 0){
                    controller.scegliCarta(null);
                }else {
                    int indiceRealeNellaMano = mappaIndiciUniversali.get(scelta);
                    // Richiama il metodo esplicito per le carte
                    controller.scegliCarta(indiceRealeNellaMano);
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("[!] Devi inserire un NUMERO: ");
        }
    }



    private void gestisciInputSceltaOggetto(String input) {
    }

    private void gestisciInputSceltaMagia(String input) {
    }

    private void gestisciInputMenuPrincipale(String input) {
        try {
            int scelta = Integer.parseInt(input);
            controller.selezionaMossa(scelta);
        } catch (NumberFormatException e) {
            System.out.print("[!] Inserisci un numero valido: ");
        }
    }

    private void gestisciInputSceltaEroe(String input) {
        try {
            int scelta = Integer.parseInt(input);

            if (scelta < 1 || scelta > this.numeroMassimoScelte) {
                System.out.print("[!] Digita un numero compreso tra 1 e " + this.numeroMassimoScelte + ": ");
            } else {

                int indiceRealeNellaMano = mappaIndiciUniversali.get(scelta);

                this.statoAttuale = StatoUI.SCELTA_MOSSA;

                // Richiama il metodo esplicito per le carte
                controller.scegliCarta(indiceRealeNellaMano);
            }
        } catch (NumberFormatException e) {
            System.out.print("[!] Devi inserire un NUMERO: ");
        }
    }

    private void gestisciInputConfermaEffetto(String input) {

        if (input.equalsIgnoreCase("Si") || input.equalsIgnoreCase("No")) {
            // Input valido! Converto in boolean
            boolean vuoleAttivare = input.equalsIgnoreCase("Si");
            // 1. Ripristino il cartello principale
            this.statoAttuale = StatoUI.SCELTA_MOSSA;
            // 2. Invio la risposta definitiva al motore di gioco
            controller.confermaAttivazioneEffetto(vuoleAttivare);
        } else {
            System.out.print("[!] Input non valido, devi dire Si o No: ");
        }
    }

    private void gestisciInputRispostaSfida(String input) {
        try {
            int scelta = Integer.parseInt(input);

            if (scelta < 0 || scelta > this.numeroMassimoScelte) {
                System.out.print("[!] Digita un numero compreso tra 0 e " + this.numeroMassimoScelte + ": ");
            } else {
                if (scelta == 0){
                    controller.scegliCarta(null);
                }else {
                    int indiceRealeNellaMano = mappaIndiciUniversali.get(scelta);
                    // Richiama il metodo esplicito per le carte
                    controller.scegliCarta(indiceRealeNellaMano);
                }
            }
        } catch (NumberFormatException e) {
            System.out.print("[!] Devi inserire un NUMERO: ");
        }
    }

    @Override
    public void menuSelezioneMossa(Player giocatoreAttivo, boolean presenzaEroi, int paRimasti) {

        System.out.println("\n" + "=".repeat(50));
        System.out.println("Turno di: " + giocatoreAttivo.getNome() + " | PA disponibili: " + paRimasti);
        System.out.println(giocatoreAttivo.getMano());

        System.out.println("\n--- SCEGLI LA TUA MOSSA ---");
        System.out.printf("%-3s | %-30s | %-10s%n", "ID", "AZIONE", "COSTO PA");
        System.out.println("-".repeat(50));

        if (!presenzaEroi) {
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
        this.statoAttuale = StatoUI.SCELTA_MOSSA;
    }

    @Override
    public void erroreSelezioneMossa(String errore) {
        System.out.print("\n--- " + errore + " ---\n");
        System.out.print("> ");
        this.avviaLoopInput();
    }

    @Override
    public void menuSceltaCartaEroe(Mano mano) {
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
                this.mappaIndiciUniversali.put(sceltaVisiva, i);
                sceltaVisiva++;
            }
        }
        System.out.println("-".repeat(48));
        this.numeroMassimoScelte = sceltaVisiva - 1;
        System.out.print("> Scegli il numero dell'eroe (1-" + this.numeroMassimoScelte + "): ");
    }

    @Override
    public void menuSfida(Player giocatoreDiTurno, Carta cartaDaSfidare, Player giocatoreInterrogato, List<Integer> indiciCarteGiocabili) {
        // 1. Cambio lo stato per il Vigile Urbano
        this.statoAttuale = StatoUI.RISPOSTA_SFIDA;

        // 2. Stampo le informazioni generali
        System.out.println("\n--- ATTENZIONE: GIOCATA IN CORSO ---");
        System.out.println(giocatoreDiTurno.getNome() + " sta cercando di giocare: " + cartaDaSfidare.getNome());
        System.out.println("------------------------------------");

        // 3. Faccio la chiamata al giocatore di turno
        System.out.println("\n>> " + giocatoreInterrogato.getNome().toUpperCase() + ", tocca a te!");

        System.out.println("Ecco le tue carte in mano:");
        List<Carta> carteInMano = giocatoreInterrogato.getMano().getCarteMano();

        // Stampo TUTTA la mano a scopo informativo
        for (int i = 0; i < carteInMano.size(); i++) {
            Carta c = carteInMano.get(i);
            // Uso un formato semplice, visto che qui è solo visualizzazione
            System.out.printf("  %d | %s [%s]%n", (i + 1), c.getNome(), c.getClass().getSimpleName().replace("Carta", ""));
        }

        System.out.println("------------------------------------");
        System.out.println("\n--- SCEGLI SE GIOCARE UNA CARTA SFIDA ---");
        System.out.printf("%-4s | %-20s %n", "NUM.", "NOME CARTA");
        System.out.println("-".repeat(30));

        // Puliamo la mappa delle scelte precedenti
        this.mappaIndiciUniversali.clear();
        int sceltaVisiva = 1;

        // ---> IL MIRACOLO ARCHITETTURALE <---
        // Invece di scorrere tutta la mano e usare "instanceof",
        // scorro SOLO gli indici che il Backend mi ha autorizzato a usare!
        for (Integer indiceReale : indiciCarteGiocabili) {

            Carta c = carteInMano.get(indiceReale); // Pesco la carta esatta

            System.out.printf("[%d]  | %-20s %n", sceltaVisiva, c.getNome());

            // Salviamo la corrispondenza: Scelta a schermo -> Posizione reale nella mano
            this.mappaIndiciUniversali.put(sceltaVisiva, indiceReale);
            sceltaVisiva++;
        }

        System.out.println("-".repeat(30));
        System.out.println("[ 0 ]  | PASSA (Non usare sfide)");

        this.numeroMassimoScelte = sceltaVisiva - 1;

        // 4. La domanda finale
        System.out.print("\n> Scegli una Carta Sfida (oppure 0 per passare): ");
    }

    @Override
    public  void menuSceltaCartaModificatore(int punteggioGiocatoreDiTurno, int punteggioAvversario, Player giocatoreDiTurno, Player avversario, List<Integer> indiciCarteGiocabili, Player giocatoreInterrogato){
        // 1. Cambio lo stato per il Vigile Urbano
        this.statoAttuale = StatoUI.SCELTA_MODIFICATORE_SFIDA;

        // 3. Faccio la chiamata al giocatore di turno
        System.out.println("\n>> " + giocatoreInterrogato.getNome().toUpperCase() + ", tocca a te!");

        System.out.println("Ecco le tue carte in mano:");
        List<Carta> carteInMano = giocatoreInterrogato.getMano().getCarteMano();

        // Stampo TUTTA la mano a scopo informativo
        for (int i = 0; i < carteInMano.size(); i++) {
            Carta c = carteInMano.get(i);
            // Uso un formato semplice, visto che qui è solo visualizzazione
            System.out.printf("  %d | %s [%s]%n", (i + 1), c.getNome(), c.getClass().getSimpleName().replace("Carta", ""));
        }

        System.out.println("------------------------------------");
        System.out.println("\n--- SCEGLI SE GIOCARE UNA CARTA MODIFICATORE ---");
        System.out.printf("%-4s | %-15s | %-20s %n", "NUM.", "NOME CARTA", "VALORE MODIFICATORE");
        System.out.println("-".repeat(45));

        this.mappaIndiciUniversali.clear();
        int sceltaVisiva = 1;

        for (Integer indiceReale : indiciCarteGiocabili) {

            Carta c = carteInMano.get(indiceReale);

            CartaModificatore mod = (CartaModificatore) c;


            String valori = String.format("+%d / %d", mod.getValorePositivo(), mod.getValoreNegativo());

            // 4. Stampo Numero, Nome e Valori
            System.out.printf("[%d]  | %-15s | %-20s %n", sceltaVisiva, mod.getNome(), valori);

            // Salviamo la corrispondenza
            this.mappaIndiciUniversali.put(sceltaVisiva, indiceReale);
            sceltaVisiva++;
        }

        System.out.println("-".repeat(45));
        System.out.println("[0]  | PASSA (Non usare modificatori)");
        this.numeroMassimoScelte = sceltaVisiva - 1;

        // 4. La domanda finale
        System.out.print("\n> Scegli una Carta Modificatore (oppure 0 per passare): ");
    }

    @Override
    public void menuSceltaCartaModificatore(int punteggioAttuale, Player giocatoreDiTurno, List<Integer> indiciCarteGiocabili, Player giocatoreInterrogato){
        // 1. Cambio lo stato per il Vigile Urbano
        this.statoAttuale = StatoUI.SCELTA_MODIFICATORE;

        // 3. Faccio la chiamata al giocatore di turno
        System.out.println("\n>> " + giocatoreInterrogato.getNome().toUpperCase() + ", tocca a te!");

        System.out.println("Ecco le tue carte in mano:");
        List<Carta> carteInMano = giocatoreInterrogato.getMano().getCarteMano();

        // Stampo TUTTA la mano a scopo informativo
        for (int i = 0; i < carteInMano.size(); i++) {
            Carta c = carteInMano.get(i);
            // Uso un formato semplice, visto che qui è solo visualizzazione
            System.out.printf("  %d | %s [%s]%n", (i + 1), c.getNome(), c.getClass().getSimpleName().replace("Carta", ""));
        }

        System.out.println("------------------------------------");
        System.out.println("\n--- SCEGLI SE GIOCARE UNA CARTA MODIFICATORE ---");
        System.out.printf("%-4s | %-15s | %-20s %n", "NUM.", "NOME CARTA", "VALORE MODIFICATORE");
        System.out.println("-".repeat(45));

        this.mappaIndiciUniversali.clear();
        int sceltaVisiva = 1;

        for (Integer indiceReale : indiciCarteGiocabili) {

            Carta c = carteInMano.get(indiceReale);

            CartaModificatore mod = (CartaModificatore) c;


            String valori = String.format("+%d / %d", mod.getValorePositivo(), mod.getValoreNegativo());

            // 4. Stampo Numero, Nome e Valori
            System.out.printf("[%d]  | %-15s | %-20s %n", sceltaVisiva, mod.getNome(), valori);

            // Salviamo la corrispondenza
            this.mappaIndiciUniversali.put(sceltaVisiva, indiceReale);
            sceltaVisiva++;
        }

        System.out.println("-".repeat(45));
        System.out.println("[0]  | PASSA (Non usare modificatori)");
        this.numeroMassimoScelte = sceltaVisiva - 1;

        // 4. La domanda finale
        System.out.print("\n> Scegli una Carta Modificatore (oppure 0 per passare): ");
    }

    @Override
    public void menuSceltaEffettoModificatore(int punteggioAttuale, Player giocatoreDiTurno, CartaModificatore mod) {
        this.statoAttuale = StatoUI.ATTESA_EFFETTO_MODIFICATORE;

        // Pulisco la mappa dalla giocata precedente
        this.mappaEffettiModificatore.clear();
        int sceltaVisiva = 1;

        System.out.println("\n--- DETTAGLI MODIFICATORE ---");
        System.out.println("Hai giocato: " + mod.getNome());
        System.out.println("\nCome vuoi usare questa carta?");

        // Opzione: Buff
        if (mod.getValorePositivo() != null) {
            System.out.printf("[ %d ] Dai %+d al tiro di %s | Nuovo tiro: %d%n",
                    sceltaVisiva, mod.getValorePositivo(), giocatoreDiTurno.getNome(), (punteggioAttuale + mod.getValorePositivo()));

            // SALVO L'INTENZIONE NELLA MAPPA!
            mappaEffettiModificatore.put(sceltaVisiva, new GiocataSceltaModificatoreNormale(TipoEffetto.POSITIVO));
            sceltaVisiva++;
        }

        // Opzione: Debuff
        if (mod.getValoreNegativo() != null) {
            System.out.printf("[ %d ] Dai %+d al tiro di %s | Nuovo tiro: %d%n",
                    sceltaVisiva, mod.getValoreNegativo(), giocatoreDiTurno.getNome(), (punteggioAttuale + mod.getValoreNegativo()));

            mappaEffettiModificatore.put(sceltaVisiva, new GiocataSceltaModificatoreNormale(TipoEffetto.NEGATIVO));
            sceltaVisiva++;
        }
        this.numeroMassimoScelte = sceltaVisiva - 1;
        System.out.println("-".repeat(45));
        System.out.print("> Scegli l'effetto (1-" + this.numeroMassimoScelte + "): ");
    }

    @Override
    public void richiediConfermaEffetto() {
        // 1. Cambio lo stato per dire al ciclo principale cosa aspettarsi
        this.statoAttuale = StatoUI.CONFERMA_EFFETTO;

        // 2. Stampo semplicemente la domanda
        System.out.print("Vuoi usare l'effetto? (Si/No): ");
        // Il programma tornerà da solo a bloccarsi sullo scanner di avviaLoopInput().
    }

    @Override
    public void mostraMessaggio(String messaggio) {
        System.out.println(messaggio);
    }

    @Override
    public void menuSceltaEffettoModificatore(int punteggioGiocatoreDiTurno, int punteggioSfidante, Player giocatoreDiTurno, Player sfidante, CartaModificatore mod) {
        this.statoAttuale = StatoUI.ATTESA_EFFETTO_MODIFICATORE;

        // Pulisco la mappa dalla giocata precedente
        this.mappaEffettiModificatore.clear();
        int sceltaVisiva = 1;

        System.out.println("\n--- DETTAGLI MODIFICATORE ---");
        System.out.println("Hai giocato: " + mod.getNome());
        System.out.println("\nCome vuoi usare questa carta?");

        // Opzione: Buff a chi ha giocato la carta
        if (mod.getValorePositivo() != null) {
            System.out.printf("[ %d ] Dai %+d al tiro di %s | Nuovo tiro: %d%n",
                    sceltaVisiva, mod.getValorePositivo(), giocatoreDiTurno.getNome(), (punteggioGiocatoreDiTurno + mod.getValorePositivo()));

            // SALVO L'INTENZIONE NELLA MAPPA!
            mappaEffettiModificatore.put(sceltaVisiva, new GiocataSceltaModificatoreSfida(BersaglioModificatore.GIOCATORE_DI_TURNO, TipoEffetto.POSITIVO));
            sceltaVisiva++;
        }

        // Opzione: Debuff a chi ha giocato la carta
        if (mod.getValoreNegativo() != null) {
            System.out.printf("[ %d ] Dai %+d al tiro di %s | Nuovo tiro: %d%n",
                    sceltaVisiva, mod.getValoreNegativo(), giocatoreDiTurno.getNome(), (punteggioGiocatoreDiTurno + mod.getValoreNegativo()));

            mappaEffettiModificatore.put(sceltaVisiva, new GiocataSceltaModificatoreSfida(BersaglioModificatore.GIOCATORE_DI_TURNO, TipoEffetto.NEGATIVO));
            sceltaVisiva++;
        }

        // Opzione: Buff allo Sfidante
        if (mod.getValorePositivo() != null) {
            System.out.printf("[ %d ] Dai %+d al tiro di %s | Nuovo tiro: %d%n",
                    sceltaVisiva, mod.getValorePositivo(), sfidante.getNome(), (punteggioSfidante + mod.getValorePositivo()));

            mappaEffettiModificatore.put(sceltaVisiva, new GiocataSceltaModificatoreSfida(BersaglioModificatore.SFIDANTE, TipoEffetto.POSITIVO));
            sceltaVisiva++;
        }

        // Opzione: Debuff allo Sfidante
        if (mod.getValoreNegativo() != null) {
            System.out.printf("[ %d ] Dai %+d al tiro di %s | Nuovo tiro: %d%n",
                    sceltaVisiva, mod.getValoreNegativo(), sfidante.getNome(), (punteggioSfidante + mod.getValoreNegativo()));

            mappaEffettiModificatore.put(sceltaVisiva, new GiocataSceltaModificatoreSfida(BersaglioModificatore.SFIDANTE, TipoEffetto.NEGATIVO));
            sceltaVisiva++;
        }

        this.numeroMassimoScelte = sceltaVisiva - 1;
        System.out.println("-".repeat(45));
        System.out.print("> Scegli l'effetto (1-" + this.numeroMassimoScelte + "): ");
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

