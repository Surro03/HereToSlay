package it.univaq.entity;

import it.univaq.entity.Tavolo;

public class EffettoPescaLeaf implements EffettoStrategyComponent {

    // Stato interno dell'effetto: quante carte fa pescare questa specifica mossa?
    private final int numeroCarte;

    public EffettoPescaLeaf(int numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    @Override
    public void risolvi(Tavolo tavolo) {

        // Ad esempio, recuperare il giocatore di turno e fargli pescare dal mazzo.
        
        //System.out.println("-> Risoluzione Effetto: Il giocatore pesca " + numeroCarte + " carte.");
        
    }

    @Override
    public String toString(){
        return "Pesca " + numeroCarte +" carte. \n";
    }
}