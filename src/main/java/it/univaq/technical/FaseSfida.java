package it.univaq.technical;

import it.univaq.entity.*;

import java.util.List;

public class FaseSfida implements Fase {

    private int step = 0;
    private final Carta cartaGiocata;
    private int indiceAvversarioAttuale = 0;
    private Player avversarioAttuale;

    public FaseSfida(Carta cartaGiocata) {
        this.cartaGiocata = cartaGiocata;
    }

    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {

        // STEP 0: Chiedo se qualcuno vuole lanciare una sfida
        if (this.step == 0) {


            if (this.indiceAvversarioAttuale >= turno.getAvversari().size()) {
                turno.addMessage("Nessuno ha sfidato la carta!");
                turno.salvaRisultatoSottoFase(new RisultatoFaseSfida(true)); // Sopravvissuta = true
                return true; // La fase Sfida finisce
            }

            List<Player> avversari = turno.getAvversari();
            Player giocatoreInterrogato = avversari.get(this.indiceAvversarioAttuale);
            //Se la mano è totalmente vuota si salta il giocatore
            if (giocatoreInterrogato.getMano().getCarteMano().isEmpty()) {

                // Log per avvisare gli altri giocatori del salto automatico
                //turno.inviaEvento(new NotificaMessaggio(giocatoreInterrogato.getNome() + " ha 0 carte in mano e passa in automatico."));
                turno.addMessage(giocatoreInterrogato.getNome() + " ha 0 carte e passa in automatico.");

                // Incremento l'indice e riavvio il loop.
                this.indiceAvversarioAttuale++;
                return this.eseguiFase(turno, tavolo);
            }
            List<Integer> indiciCarteGiocabili = giocatoreInterrogato.getMano().getIndiciCarteDiTipo(CartaSfida.class);
            this.step = 1;
            ContestoAttesa payload = new ContestoAttesaSfida(turno.getGiocatoreDiTurno(), cartaGiocata, giocatoreInterrogato, indiciCarteGiocabili);
            turno.setAttesa(TipoAttesa.ATTESA_SFIDA, payload);
            turno.avviaTimer(this);
            return false;
        }

        //STEP 1: Verifico se il giocatore vuole tirare una sfida e la scarto dalla sua mano
        if (this.step == 1) {
            Object input = turno.popInput();
            if (input != null && !(input instanceof Integer)) {
                return false; // Ignora gli "Invio" a vuoto
            }
            Integer risposta = (Integer) input;
            if (risposta == null) {
                // PASSO MANUALE
                turno.fermaTimerGiocatore(this);
                indiceAvversarioAttuale++;
                this.step = 0;
                return this.eseguiFase(turno, tavolo);

            } else if (risposta == -1) {
                // TIMEOUT
                indiceAvversarioAttuale++;
                this.step = 0;
                return this.eseguiFase(turno, tavolo);
            }
            else{
                turno.fermaTimerGiocatore(this);
                this.avversarioAttuale = turno.getAvversari().get(indiceAvversarioAttuale);
                Carta cartaDaScartare = this.avversarioAttuale.getMano().rimuoviCarta(risposta);
                tavolo.aggiungiCartaPilaScarti(cartaDaScartare);
                turno.addMessage(" --- " + this.avversarioAttuale.getNome() + " HA LANCIATO UNA SFIDA! ---");

                // Tiriamo i dadi
                int valorePlayer = tavolo.lanciaDadi(2);
                int valoreSfidante = tavolo.lanciaDadi(2);

                turno.addMessage("\n--- RISULTATI INIZIALI SFIDA ---\n"+ "Tiro di " + turno.getGiocatoreDiTurno().getNome() + ": " + valorePlayer + " |\n" + "Tiro di " + avversarioAttuale.getNome() + ": " + valoreSfidante +" |");
                // Lancio la fase modificatori
                turno.aggiungiFaseInCima(new FaseModificatoriSfida(valorePlayer, valoreSfidante, turno.getGiocatoreDiTurno(), avversarioAttuale));

                this.step = 2;
                return false;
            }
        }

        // STEP 2: Calcolo il vincitore dopo i modificatori
        else if (this.step == 2) {
            // Recupero i valori finali
            RisultatoFaseModificatoriSfida punteggiFinali = (RisultatoFaseModificatoriSfida) turno.popRisultatoSottoFase();
            int punteggioFinaleGiocatoreDiTurno = punteggiFinali.punteggioFinaleGiocatoreDiTurno();
            int punteggioFinaleSfidante = punteggiFinali.punteggioFinaleSfidante();
            turno.addMessage("Risultato Finale | " + turno.getGiocatoreDiTurno().getNome() + ": " + punteggioFinaleGiocatoreDiTurno+ " | " + this.avversarioAttuale.getNome() + ": " + punteggioFinaleSfidante );

            // In caso di parità, vince lo sfidante!
            if (punteggioFinaleGiocatoreDiTurno > punteggioFinaleSfidante) {
                turno.addMessage(turno.getGiocatoreDiTurno().getNome() + " vince la sfida! La carta entra in gioco.");
                turno.salvaRisultatoSottoFase(new RisultatoFaseSfida(true)); // Sopravvissuta
            } else {
                turno.addMessage(this.avversarioAttuale.getNome() + " vince la sfida! La carta " + cartaGiocata.getNome() + " viene distrutta.");
                turno.salvaRisultatoSottoFase(new RisultatoFaseSfida(false)); // Distrutta
            }
            return true;
        }

        return true;
    }
}