package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.ui.Player;

public class FaseEffetto extends Fase {

	private Carta carta;

	/**
	 * 
	 * @param carta
	 */
	public void salvaCarta(Carta carta) {
		this.carta = carta;
	}

	/**
	 * 
	 * @param punteggioDefinitivo
	 */
	public Boolean checkAttivazioneEffetto(int punteggioDefinitivo) {
		// TODO - implement FaseEffetto.checkAttivazioneEffetto
		throw new UnsupportedOperationException();
	}

	public void ottieniEffetto() {
		// TODO - implement FaseEffetto.ottieniEffetto
		throw new UnsupportedOperationException();
	}
}