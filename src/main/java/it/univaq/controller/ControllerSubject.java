package it.univaq.controller;

import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;

public interface ControllerSubject {
    void addObserver(GameObserver observer);
    void removeObserver(GameObserver observer);

    void iniziaPartita();

    Player prossimoTurno();

    void iniziaFlussoGiocaEroe();

    void giocaCartaDaMano(int indiceAssolutoCarta);

    void giocaEroeSpecifico(int indiceCartaSelezionata);


    void rispostaUtilizzoEffetto(boolean vuoleUsare);
}
