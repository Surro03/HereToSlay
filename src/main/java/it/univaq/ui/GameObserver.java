package it.univaq.ui;

import it.univaq.entity.*;

import java.util.List;

public interface GameObserver {

    void menuSelezioneMossa(Player giocatoreAttivo, boolean isEroiEmpty, int paRimasti);

    void erroreSelezioneMossa(String errore);

    //void messaggioMossaSelezionata(int paRimasti, Boolean succeso, String mossa);

    void richiediSceltaCartaEroe(Mano mano);

    //<T extends Carta> T scegliCartaDaGiocare(String tipoCarta, List<T> carteDisponibili, String nomePlayer);

    void richiestaSfida();

    Boolean richiestaUtilizzoEffetto(String nomeCarta);

    void rispostaUtilizzoEffetto(Boolean risposta);

    void risultatoTiroDadi(int risultato);

    void punteggioIntermedio (Float punteggio);

    void punteggiDefinitivi(Float risultato, String nomePlayer);

    void esitoRequisito(Boolean esitoRequisito, String descrizioneEffetto);

    void messaggioFineTurno(String nomePlayer);

    void numClassiDiverse(int classiDiverse);

    void messaggioVittoria(String nomePlayer, String causa);

    void mostraMessaggio(String messaggio);

    Boolean chiediSeGiocareModificatore(Player giocatore, int numModificatori);

    CartaModificatore scegliModificatoreDaGiocare(List<CartaModificatore> disponibili);

    Float scegliSegnoModificatore(CartaModificatore carta);

    Boolean chiediConfermaFineFase();
}

//package it.univaq.ui;
//
//import it.univaq.entity.Carta;
//import it.univaq.entity.CartaEroe;
//import it.univaq.entity.CartaModificatore;
//import it.univaq.entity.Player;
//import it.univaq.technical.Fase;
//
//import java.util.List;
//
//public interface InterfacciaUtente {
//
//    // 1. Metodi passivi (Il backend ordina alla UI di mostrare roba, senza aspettare)
//    void mostraMessaggio(String messaggio);
//    void mostraTurnoGiocatore(String nomeGiocatore, int paRimasti);
//
//    // 2. Metodi attivi (Il backend ordina alla UI di accendere i bottoni e si mette a dormire)
//    // Passiamo i boolean così la UI sa quali bottoni rendere cliccabili!
//    void richiediSelezioneMossa(boolean puoGiocareEroe, boolean puoAttaccare, boolean puoPescare);
//
//    // (Più avanti qui aggiungeremo richiediTiroDadi, richiediSceltaCarta, ecc.)
//
//    /**
//     * Chiamato da FaseGiocaCartaEroe (Step 1)
//     * La UI deve mostrare un popup con scritto "Sì" o "No".
//     * Quando l'utente clicca, la UI chiamerà: riceviInputDaUI(true) oppure riceviInputDaUI(false)
//     */
//    void richiediConfermaEffetto(String messaggioDomanda);
//
//    /**
//     * Chiamato da FaseModificatori (Step 0)
//     * La UI deve mostrare il timer di 10 secondi e i bottoni per giocare i modificatori.
//     * Deve mostrare a schermo il "punteggioIniziale" (il risultato dei dadi).
//     */
//    void richiediGiocataModificatori(float punteggioIniziale);
//
//    /**
//     * Chiamato da FaseModificatori (Step 1 - quando qualcuno gioca una carta)
//     * La UI deve semplicemente aggiornare il numero mostrato a schermo e resettare il timer.
//     */
//    void aggiornaSchermataModificatori(float nuovoPunteggio);
//
//    /**
//     * Chiamato da FaseGiocaCartaEroe (Step 0)
//     */
//    void richiediSceltaCarta(List<CartaEroe> carteTraCuiScegliere);
//
//
//}
//
//
//
//

/*
public interface InterfacciaUtente {

    void mostraMenuInizioTurno(Fase fase, Player giocatoreAttivo, Boolean isEroiEmpty, int paRimasti);

    Integer chiediSelezioneMossa(Boolean errore);

    void messaggioMossaSelezionata(int paRimasti, Boolean succeso, String mossa);

    <T extends Carta> T scegliCartaDaGiocare(String tipoCarta, List<T> carteDisponibili, String nomePlayer);

    void richiestaSfida();

    Boolean richiestaUtilizzoEffetto(String nomeCarta);

    void rispostaUtilizzoEffetto(Boolean risposta);

    void risultatoTiroDadi(int risultato);

    void punteggioIntermedio (Float punteggio);

    void punteggiDefinitivi(Float risultato, String nomePlayer);

    void esitoRequisito(Boolean esitoRequisito, String descrizioneEffetto);

    void messaggioFineTurno(String nomePlayer);

    void numClassiDiverse(int classiDiverse);

    void messaggioVittoria(String nomePlayer, String causa);

    void mostraMessaggio(String messaggio);

    Boolean chiediSeGiocareModificatore(Player giocatore, int numModificatori);

    CartaModificatore scegliModificatoreDaGiocare(List<CartaModificatore> disponibili);

    Float scegliSegnoModificatore(CartaModificatore carta);

    Boolean chiediConfermaFineFase();

}
*/