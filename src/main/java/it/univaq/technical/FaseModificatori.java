package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaModificatore;
import it.univaq.ui.Player;

import java.util.HashMap;
import java.util.Map;

public class FaseModificatori extends Fase {

    private Map<Integer, Float> punteggiPlayer = new HashMap<>();

	/**
	 * 
	 * @param valoreCarta
	 * @param target
	 */
	public float calcoloPunteggio(float valoreCarta, Player target) {
        punteggiPlayer.merge(target.getId(), valoreCarta, Float::sum);
        return punteggiPlayer.get(target.getId());
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