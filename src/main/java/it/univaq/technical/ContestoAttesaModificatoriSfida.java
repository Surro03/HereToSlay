package it.univaq.technical;

import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;

import java.util.List;

public record ContestoAttesaModificatoriSfida(
        int punteggioGiocatoreDiTurno,
        int punteggioAvversario,
        List<Integer> indiciCarteGiocabili,
        Player giocatoreDiTurno,
        Player avversario,
        Player giocatoreInterrogato) implements ContestoAttesa {

    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSceltaCartaModificatore(punteggioGiocatoreDiTurno,
                punteggioAvversario,
                giocatoreDiTurno,
                avversario,
                indiciCarteGiocabili,
                giocatoreInterrogato);
    }
}
