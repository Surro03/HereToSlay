package it.univaq.technical;

import it.univaq.entity.Carta;

public class FaseGiocaCarta extends Fase {

	private Carta CartaGiocata;

	/**
	 * 
	 * @param Carta
	 */

	public void salvaCartaGiocata(Carta Carta){
		CartaGiocata= Carta;
	}

}