package it.univaq.ui;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;
import it.univaq.technical.Fase;

import java.util.List;

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
