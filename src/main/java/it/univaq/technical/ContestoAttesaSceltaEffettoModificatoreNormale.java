package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;

public record ContestoAttesaSceltaEffettoModificatoreNormale(
        int punteggioAttuale,
        Player giocatoreCheHaTirato,
        CartaModificatore cartaModificatoreScelta) implements PayloadAttesa{
    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSceltaEffettoModificatore(punteggioAttuale, giocatoreCheHaTirato, cartaModificatoreScelta);
    }
}
