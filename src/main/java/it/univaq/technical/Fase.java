package it.univaq.technical;

import it.univaq.ui.InterfacciaUtente;

public interface Fase {
    /**
     * Esegue un pezzo di logica della fase.
     * @param turno Il turno attuale, usato per controllare i PA e la pila.
     * @param gui   L'interfaccia usata per inviare comandi asincroni.
     * @return TRUE se la fase ha finito (va tolta dalla pila), FALSE se si è messa in pausa.
     */
    boolean eseguiFase(Turno turno, InterfacciaUtente gui);
}