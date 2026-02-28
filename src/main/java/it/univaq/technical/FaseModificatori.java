package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaModificatore;
import it.univaq.ui.Player;

public class FaseModificatori extends Fase {

	private Float ValorePlayer1;
	private Float ValorePlayer2;


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
	 * 
	 * @param punteggioP
     * @param punteggioS
	 */
	public boolean salvaPunteggio(Float punteggioP, Float punteggioS) {
		punteggioP = ValorePlayer1;
        punteggioS = ValorePlayer2;
        return true;

	}

	public void ottieniPunteggi() {
		// TODO - implement FaseModificatori.ottieniPunteggi
		throw new UnsupportedOperationException();
	}

}