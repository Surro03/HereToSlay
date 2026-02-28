package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaModificatore;
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
	public float calcoloPunteggio(Carta carta, Player target, int opzione) {
		if (carta instanceof CartaModificatore) {
            CartaModificatore c = (CartaModificatore) carta;
            c.getValore1();
            if (puntiModificatorePlayer2 != null) {
                puntiModificatorePlayer2 = puntiModificatorePlayer2 +  c.getValore1();
            } else  {
                puntiModificatorePlayer2 = c.getValore1();
            }
        }
	}

    /**
     * Salva il punteggio modificato per un determinato giocatore.
     * * @param punteggioP Il punteggio calcolato
     * @param playerId L'ID del giocatore
     */
    public void salvaPunteggio(float punteggioP, Integer playerId) {
        punteggiPlayer.put(playerId, punteggioP);
    }

	public Float ottieniPunteggi(Integer playerId) {
        return punteggiPlayer.getOrDefault(playerId, 0f);
    }

}