package it.univaq.ui;

import it.univaq.entity.*;
import it.univaq.technical.FinestraTemporaleObserver;
import it.univaq.technical.VoceMenu;

import java.util.List;

public interface GameObserver extends FinestraTemporaleObserver {

    void menuSelezioneMossa(Player giocatoreAttivo, List<VoceMenu> catalogoMosse, int paRimasti);

    void erroreSelezioneMossa(String errore);

    void menuSceltaCartaEroe(Mano mano);

    void menuSfida(Player giocatoreAttivo, Carta cartaGiocata, Player giocatoreInterrogato, List<Integer> indiciCarteGiocabili);

    void menuSceltaEffettoModificatore(int punteggioAttuale, Player giocatoreDiTurno, CartaModificatore mod);

    void richiediConfermaEffetto();

    void menuSceltaCartaModificatore(int punteggioGiocatoreDiTurno, int punteggioAvversario, Player giocatoreDiTurno, Player avversario, List<Integer> indiciCarteGiocabili, Player giocatoreInterrogato);

    void menuSceltaCartaModificatore(int punteggioAttuale, Player giocatoreDiTurno, List<Integer> indiciCarteGiocabili, Player giocatoreInterrogato);

    void mostraMessaggio(String messaggio);

    void menuSceltaEffettoModificatore(int punteggioGiocatoreDiTurno, int punteggioSfidante, Player giocatoreDiTurno, Player sfidante, CartaModificatore mod);

}
