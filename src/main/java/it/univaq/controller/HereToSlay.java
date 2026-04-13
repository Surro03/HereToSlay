package it.univaq.controller;

import it.univaq.entity.*;
import it.univaq.technical.*;
import it.univaq.ui.GameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class HereToSlay implements ControllerSubject{

    private List<GameObserver> observers;
    private final List<Player> elencoGiocatori;
    private Player giocatoreAttivo;
    private final Tavolo tavolo;
    private Turno turnoAttuale;


    public HereToSlay(List<Player> elencoGiocatori) {
        this.elencoGiocatori = elencoGiocatori;
        this.tavolo = new Tavolo(elencoGiocatori);
        // FIX: Inizializziamo la lista, altrimenti crasha!
        this.observers = new ArrayList<>();
    }

    //Gestione Observers
    @Override
    public void addObserver(GameObserver gameObserver) { this.observers.add(gameObserver); }
    @Override
    public void removeObserver(GameObserver gameObserver) { this.observers.remove(gameObserver); }

    private void notificaTutti(Consumer<GameObserver> action) {
        for (GameObserver obs : observers) { action.accept(obs); }
    }


    @Override
    public void iniziaPartita() {
        this.giocatoreAttivo = elencoGiocatori.getFirst();
        this.iniziaTurno(this.giocatoreAttivo);
    }

    private void iniziaTurno(Player giocatore) {
        this.notificaTutti(obs -> obs.mostraMessaggio("\n--- INIZIA IL TURNO DI: " + giocatore.getNome() + " ---"));

        this.turnoAttuale = new Turno(giocatore);
        this.turnoAttuale.aggiungiFaseInCima(new FaseSceltaMossa());

        // Diamo la "prima spinta" a vuoto per far partire la prima fase
        this.inoltraAlTurno(null);
    }

    @Override
    public void prossimoTurno() {
        // 1. Controlla Vittoria
        if (tavolo.checkVittoria(giocatoreAttivo.getId()).vittoria()) {
            notificaTutti(obs -> obs.mostraMessaggio("VITTORIA! " + giocatoreAttivo.getNome() + " ha vinto!"));
            // Non facciamo ripartire il turno. Il gioco finisce qui.
            return;
        }

        // 2. Calcola il prossimo e riparte
        int indiceAttuale = elencoGiocatori.indexOf(giocatoreAttivo);
        int prossimoIndice = (indiceAttuale + 1) % elencoGiocatori.size();
        this.giocatoreAttivo = elencoGiocatori.get(prossimoIndice);

        this.iniziaTurno(this.giocatoreAttivo);
    }

    // ==========================================================
    // IL MOTORE CENTRALE: TRADUZIONE E NOTIFICA
    // ==========================================================
    private void inoltraAlTurno(Object dato) {
        // 1. Spinge il dato. Il Turno fa i suoi giri e poi si addormenta.
        this.turnoAttuale.riceviInput(dato);

        // 2. Controllo: Il turno è finito?
        if (this.turnoAttuale.isTerminato()) {
            this.prossimoTurno();
            return;
        }

        //3. Controlla se ci sono messaggi da mandare alla UI
        while (!this.turnoAttuale.getMessages().isEmpty()) {
            notificaTutti(obs -> obs.mostraMessaggio(this.turnoAttuale.getFirstMessage()));
        }

        // 4. Controllo: La fase si è fermata. Cosa aspetta?
        switch (this.turnoAttuale.getAttesa()) {
            case SCELTA_MOSSA_PRINCIPALE:
                notificaTutti(obs -> obs.menuSelezioneMossa(this.giocatoreAttivo,this.verificaTipoDiCarteInMano(CartaEroe.class) ,turnoAttuale.getPaRimasti()));
                break;
            case SCELTA_CARTA_EROE:
                notificaTutti(obs -> obs.menuSceltaCartaEroe(giocatoreAttivo.getMano()));
                break;
            case CONFERMA_EFFETTO:
                notificaTutti(GameObserver::richiediConfermaEffetto);
                break;
            case RICHIESTA_TAVOLO:
                this.inoltraAlTurno(this.tavolo);
                break;
        }
    }

    public boolean verificaTipoDiCarteInMano(Class<? extends Carta> classeCercata) {
        return giocatoreAttivo.getMano().getCarteMano().stream()
                .anyMatch(classeCercata::isInstance);
    }

    // ==========================================================
    // CHIAMATE SPECIFICHE DALLA UI
    // ==========================================================
    @Override
    public void selezionaMossa(int mossa) {
        boolean presenzaEroi = this.verificaTipoDiCarteInMano(CartaEroe.class);
        if (mossa == 1 && !presenzaEroi) {
            notificaTutti(obs -> obs.erroreSelezioneMossa("Non hai Eroi da giocare nella tua mano"));
            return; // Esce dal metodo, il controller torna in attesa di un nuovo input!
            }

        if (mossa < 1 || mossa > 7) {
            notificaTutti(obs -> obs.erroreSelezioneMossa("Devi scegliere una mossa fra 1 e 7"));
            return;
            }

        this.inoltraAlTurno(mossa);
    }

    @Override
    public void scegliCarta(int indiceRealeNellaMano) { this.inoltraAlTurno(indiceRealeNellaMano); }

    @Override
    public void annullaScelta() { this.inoltraAlTurno(null); }

    @Override
    public void confermaAttivazioneEffetto(boolean vuoleAttivare) { this.inoltraAlTurno(vuoleAttivare); }

}