package it.univaq.entity;

import it.univaq.entity.Tavolo; // Importa il tuo Tavolo reale

public interface EffettoStrategyComponent{
    
    /**
     * Risolve l'effetto della carta applicando le modifiche al dominio di gioco.
     *  @param tavolo Il dominio del gioco con tutti i giocatori, mazzi e carte a terra.
     */
    void risolvi(Tavolo tavolo);
}