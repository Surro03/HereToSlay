package it.univaq.controller;

import it.univaq.entity.CartaModificatore;
import it.univaq.technical.GiocataGiocatore;
import it.univaq.technical.SceltaMossa;
import it.univaq.ui.GameObserver;

public interface ControllerSubject {

    void addObserver(GameObserver observer);

    void removeObserver(GameObserver observer);

    void iniziaPartita();

    void prossimoTurno();

    void selezionaMossa(SceltaMossa mossa);

    void scegliCarta(Integer indiceRealeNellaMano);

    void confermaAttivazioneEffetto(boolean vuoleAttivare);

    void sceltaBersaglioEffettoModificatore(GiocataGiocatore giocata);

    void annullaScelta();



}
