package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaEroe;

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
	public Boolean checkAttivazioneEffetto(float punteggioDefinitivo) {
		return carta.checkAttivazioneEffetto(punteggioDefinitivo);
	}

	public String ottieniEffetto() {
		if (carta instanceof CartaEroe cartaEroe) {
            return cartaEroe.getEffetto();
        } else throw new IllegalArgumentException("Carta non valida");
	}
}