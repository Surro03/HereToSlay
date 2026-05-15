package it.univaq.controller;

import it.univaq.entity.CartaModificatore;
import it.univaq.technical.GiocataGiocatore;
import it.univaq.technical.SceltaMossa;
import it.univaq.ui.GameObserver;

/**
 * Interfaccia che definisce le operazioni principali che il Controller (HereToSlay)
 * mette a disposizione per l'interfaccia utente (Views). Implementa il pattern Subject
 * per gestire la comunicazione verso gli observer.
 */
public interface ControllerSubject {

    /**
     * Registra un nuovo observer (tipicamente l'interfaccia utente) che riceverà
     * le notifiche degli eventi e dei cambiamenti di stato del gioco.
     *
     * @param observer L'observer da aggiungere.
     */
    void addObserver(GameObserver observer);

    /**
     * Rimuove un observer precedentemente registrato, in modo che non riceva più notifiche.
     *
     * @param observer L'observer da rimuovere.
     */
    void removeObserver(GameObserver observer);

    /**
     * Inizializza le condizioni di partenza della partita, imposta il primo giocatore
     * attivo e dà il via al primo turno.
     */
    void iniziaPartita();

    /**
     * Conclude il turno del giocatore attualmente attivo, esegue eventuali fasi di fine turno
     * e passa il controllo al giocatore successivo nell'ordine.
     */
    void prossimoTurno();

    /**
     * Segnala al controller che il giocatore ha scelto una determinata mossa (es. pescare,
     * giocare una carta). Il controller verificherà se l'azione è valida e ne scalerà
     * i relativi Punti Azione (PA).
     *
     * @param mossa La mossa selezionata dal giocatore.
     */
    void selezionaMossa(SceltaMossa mossa);

    /**
     * Segnala che il giocatore attuale decide di passare, rinunciando ad agire.
     * Viene utilizzato in fasi specifiche come la Finestra di Sfida o la Fase Modificatori.
     */
    void passa();

    /**
     * Riceve l'indice della carta che il giocatore desidera giocare, prelevata dalla sua mano.
     *
     * @param indiceRealeNellaMano La posizione (0-based) della carta all'interno della mano del giocatore.
     */
    void scegliCarta(Integer indiceRealeNellaMano);

    /**
     * Registra la decisione del giocatore in merito all'attivazione dell'effetto di una carta eroe
     * appena schierata nel proprio party.
     *
     * @param vuoleAttivare true se il giocatore vuole attivare l'effetto, false altrimenti.
     */
    void confermaAttivazioneEffetto(boolean vuoleAttivare);

    /**
     * Riceve i dettagli di una giocata specifica effettuata dal giocatore, tipicamente l'uso di una carta
     * modificatore con il relativo segno scelto (positivo/negativo) oppure la scelta di un bersaglio
     * per un effetto.
     *
     * @param giocata L'oggetto che contiene le informazioni sulla scelta del giocatore.
     */
    void sceltaBersaglioEffettoModificatore(GiocataGiocatore giocata);

    /**
     * Permette al giocatore di annullare l'azione corrente, per tornare al menù principale
     * o ad uno stato precedente di selezione (ove consentito dalle regole).
     */
    void annullaScelta();

}