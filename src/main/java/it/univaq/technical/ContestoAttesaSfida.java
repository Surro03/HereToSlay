package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;
import java.util.List;

public record ContestoAttesaSfida(
        Player giocatoreDiTurno,
        Carta cartaDaSfidare,
        Player giocatoreInterrogato,
        List<Integer> indiciSfideGiocabili
) implements ContestoAttesa {

    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSfida(giocatoreDiTurno , cartaDaSfidare, giocatoreInterrogato, indiciSfideGiocabili);
    }

}
