package it.univaq.controller;

import it.univaq.ui.GameObserver;

public interface ControllerSubject {
    void addObserver(GameObserver observer);
    void removeObserver(GameObserver observer);

    void iniziaPartita();
    void verificaMossa(int mossaSelezionata);

    void iniziaFlussoGiocaEroe();

    void giocaCartaDaMano(int indiceAssolutoCarta);

    void giocaEroeSpecifico(int indiceCartaSelezionata);


    void rispostaUtilizzoEffetto(boolean vuoleUsare);
}
