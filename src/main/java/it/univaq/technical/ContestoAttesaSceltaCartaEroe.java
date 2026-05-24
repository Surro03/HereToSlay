package it.univaq.technical;

import it.univaq.entity.Mano;

public record ContestoAttesaSceltaCartaEroe(Mano manoGiocatoreDiTurno) implements ContestoAttesa {

    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSceltaCartaEroe(manoGiocatoreDiTurno);
    }
}
