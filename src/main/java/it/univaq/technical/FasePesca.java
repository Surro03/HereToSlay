package it.univaq.technical;

import it.univaq.entity.Tavolo;

public class FasePesca implements Fase {
    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {
        return true;
    }
}