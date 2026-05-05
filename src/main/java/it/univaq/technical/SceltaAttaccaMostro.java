package it.univaq.technical;

import it.univaq.entity.Player;

public record SceltaAttaccaMostro() implements SceltaMossa {
    @Override
    public boolean isDisponibile(Turno turno) {
        // La mossa controlla da sola se il giocatore ha PA ed Eroi in mano!
        //return turno.getPaRimasti() >= 3;
        return false;
    }

    @Override
    public Fase eseguiMossa(Turno turno) {
        return null;
    }

    @Override
    public String getNomeAzione() {
        return "Attacca un Mostro";
    }

    @Override
    public int getCostoPA() {
        return 3;
    }
}
