package it.univaq.entity;

import it.univaq.entity.Tavolo; // Importa il tuo Tavolo reale
import it.univaq.technical.Turno;  // Importa il tuo Turno reale

public interface EffettoStrategyComponent{
    
    /**
     * Risolve l'effetto della carta applicando le modifiche al dominio di gioco.
     * * @param tavolo Il dominio del gioco con tutti i giocatori, mazzi e carte a terra.
     * @param turno  Il mediatore logico corrente che gestisce le fasi e i messaggi.
     */
    void risolvi(Tavolo tavolo, Turno turno);
}