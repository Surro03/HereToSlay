package it.univaq.technical;

import it.univaq.entity.CartaMagia;
import it.univaq.entity.Player;
import it.univaq.entity.Tavolo;

public record SceltaUtilizzaEffettoEroe() implements SceltaMossa{
    @Override
    public boolean isDisponibile(Turno turno) {
        Tavolo tavolo = turno.getTavolo();
        // La mossa controlla da sola se il giocatore ha PA ed Eroi in mano!
        return turno.getPaRimasti() >= 1 && !tavolo.getParty(turno.getGiocatoreDiTurno().getId()).hasPartyEmpty();
    }

    @Override
    public Fase eseguiMossa(Turno turno) {
        return null;
    }

    @Override
    public String getNomeAzione() {
        return "Utilizza Effetto Eroe del Party";
    }

    @Override
    public int getCostoPA() {
        return 1;
    }
}
