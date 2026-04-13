package it.univaq.controller;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;

public interface ControllerSubject {
    void addObserver(GameObserver observer);

    void removeObserver(GameObserver observer);

    void iniziaPartita();

    void prossimoTurno();

    void selezionaMossa(int mossa);

    void scegliCartaEroe(int indiceRealeNellaMano);

    void annullaScelta();
}
