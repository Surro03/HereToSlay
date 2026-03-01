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
	 * @param carta
	 * @param target
	 * @param opzione
	 */
	public float calcoloPunteggio(Carta carta, Player target, int opzione) {
		if (carta instanceof CartaModificatore) {
            CartaModificatore c = (CartaModificatore) carta;
            c.getValore1();
            if (punteggiPlayer.get(target.getId()) == null ) {
                punteggiPlayer.put(target.getId(), c.getValore1());
            } else {
                punteggiPlayer.put(target.getId(), punteggiPlayer.get(target.getId()) + c.getValore1());
            }
        }
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