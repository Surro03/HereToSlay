package it.univaq.technical;

import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;

import java.util.List;

public record ContestoAttesaModificatoriNormale(
        int punteggioAttuale,
        Player giocatoreCheHaTirato,
        Player giocatoreInterrogato,
        List<Integer> indiciCarteGiocabili
) implements ContestoAttesa {
    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSceltaCartaModificatore(punteggioAttuale, giocatoreCheHaTirato, indiciCarteGiocabili, giocatoreInterrogato);
    }
}
