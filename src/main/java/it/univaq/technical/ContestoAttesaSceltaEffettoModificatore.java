package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;

public record ContestoAttesaSceltaEffettoModificatore(int punteggioGiocatoreDiTurno, int punteggioSfidante, Player giocatoreDiTurno, Player sfidante, CartaModificatore mod) implements ContestoAttesa {
    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSceltaEffettoModificatore(punteggioGiocatoreDiTurno, punteggioSfidante, giocatoreDiTurno, sfidante, mod);
    }
}
