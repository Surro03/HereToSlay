package it.univaq.controller;

import it.univaq.ui.GameObserver;

public interface ControllerSubject {
    void addObserver(GameObserver observer);

    void removeObserver(GameObserver observer);

    void iniziaPartita();

    void prossimoTurno();

    void selezionaMossa(int mossa);

    void scegliCarta(int indiceRealeNellaMano);

    void confermaAttivazioneEffetto(boolean vuoleAttivare);

    void annullaScelta();
}
