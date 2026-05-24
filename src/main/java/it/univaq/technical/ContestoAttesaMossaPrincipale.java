package it.univaq.technical;

import it.univaq.entity.Player;

import java.util.List;

public record ContestoAttesaMossaPrincipale (
        Player playerDiTurno,
        List<VoceMenu> vociMenu,
        int pa
) implements ContestoAttesa {

    @Override
    public void notificaUI(GameObserver obs) {
        obs.menuSelezioneMossa(playerDiTurno, vociMenu, pa);
    }
}
