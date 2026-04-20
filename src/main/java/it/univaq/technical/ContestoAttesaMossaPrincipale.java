package it.univaq.technical;

import it.univaq.entity.Mano;
import it.univaq.entity.Player;
import it.univaq.ui.GameObserver;

public record ContestoAttesaMossaPrincipale (Player playerDiTurno, boolean hasEroiInMano, Integer paRimasti) implements PayloadAttesa {

    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSelezioneMossa(playerDiTurno, hasEroiInMano, paRimasti);
    }
}
