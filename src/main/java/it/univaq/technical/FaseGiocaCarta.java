package it.univaq.technical;

import it.univaq.entity.Carta;

public class FaseGiocaCarta extends Fase {

	private Carta cartaGiocata;

	/**
	 * 
	 * @param carta
	 */

	public void salvaCartaGiocata(Carta carta){
		this.cartaGiocata= carta;
	}

}