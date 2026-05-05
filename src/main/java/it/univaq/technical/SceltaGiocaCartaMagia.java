package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.CartaMagia;
import it.univaq.entity.Player;

public record SceltaGiocaCartaMagia() implements SceltaMossa{
    @Override
    public boolean isDisponibile(Turno turno) {
        Player attivo = turno.getGiocatoreDiTurno();
        // La mossa controlla da sola se il giocatore ha PA ed Eroi in mano!
        return turno.getPaRimasti() >= 1 && attivo.haCarteDiTipoInMano(CartaMagia.class);
    }

    @Override
    public Fase eseguiMossa(Turno turno) {
        return null;
    }

    @Override
    public String getNomeAzione() {
        return "Gioca una Carta Magia";
    }

    @Override
    public int getCostoPA() {
        return 1;
    }
}
