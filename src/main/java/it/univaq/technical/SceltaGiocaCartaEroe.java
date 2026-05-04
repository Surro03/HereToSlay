package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Player;

public record SceltaGiocaCartaEroe() implements SceltaMossa{

    @Override
    public boolean isDisponibile(Turno turno) {
        Player attivo = turno.getGiocatoreDiTurno();
        // La mossa controlla da sola se il giocatore ha PA ed Eroi in mano!
        return turno.getPaRimasti() >= 1 && attivo.haCarteDiTipoInMano(CartaEroe.class);
    }

    @Override
    public Fase eseguiMossa(Turno turno) {
        turno.consumaPA(1);
        return new FaseGiocaCartaEroe();
    }

    @Override public String getNomeAzione() { return "Gioca Carta Eroe"; }
    @Override public int getCostoPA() { return 1; }
}
