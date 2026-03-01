package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.ui.Player;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class FaseModificatori extends Fase {

    private Map<Integer, Float> punteggiPlayer = new HashMap<>();

	/**
	 * 
	 * @param carta
	 * @param target
	 * @param opzione
	 */
	public void calcoloPunteggio(Carta carta, Player target, Integer opzione) {
		// TODO - implement FaseModificatori.calcoloPunteggio
		throw new UnsupportedOperationException();
	}

    /**
     * Salva il punteggio modificato per un determinato giocatore.
     * * @param punteggioP Il punteggio calcolato
     * @param playerId L'ID del giocatore
     */
    public void salvaPunteggio(Integer playerId, float punteggioP) {
        punteggiPlayer.put(playerId, punteggioP);
    }

	public Float ottieniPunteggi(Integer playerId) {
        return punteggiPlayer.getOrDefault(playerId, 0f);
    }

}