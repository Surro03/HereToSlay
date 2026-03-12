package it.univaq.technical;

import it.univaq.entity.Carta;

public class FaseGiocaCarta extends Fase {

	private Carta cartaGiocata;

	public FaseGiocaCarta(Carta cartaScelta) {
		super();
		this.cartaGiocata = cartaScelta;
	}

	/**
	 * 
	 * @param carta
	 */

	public void salvaCartaGiocata(Carta carta){
		this.cartaGiocata= carta;
	}

	public Carta getCartaGiocata() {return this.cartaGiocata;}



}