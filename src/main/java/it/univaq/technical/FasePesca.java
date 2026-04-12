package it.univaq.technical;
import it.univaq.ui.InterfacciaUtente;

public class FasePesca implements Fase {
    @Override
    public boolean eseguiFase(Turno turno, InterfacciaUtente gui) {
        return true;
    }
}