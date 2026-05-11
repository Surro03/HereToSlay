package it.univaq.technical;

import it.univaq.entity.Carta;

public record SceltaPescaCarta() implements SceltaMossa{

    @Override
    public boolean isDisponibile(Turno turno) {
        //return turno.getPaRimasti() >= 1;
        return false;
    }

    @Override
    public Fase eseguiMossa(Turno turno) {
        return null;
    }

    @Override
    public String getNomeAzione() {
        return "Pesca una Carta dal PilaDiCarte";
    }

    @Override
    public int getCostoPA() {
        return 1;
    }
}
