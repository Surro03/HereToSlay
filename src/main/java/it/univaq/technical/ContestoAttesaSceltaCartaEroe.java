package it.univaq.technical;

import it.univaq.entity.Mano;
import it.univaq.ui.GameObserver;

public record ContestoAttesaSceltaCartaEroe(Mano manoGiocatoreDiTurno) implements ContestoAttesa {

    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSceltaCartaEroe(manoGiocatoreDiTurno);
    }
}
