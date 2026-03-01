package it.univaq.entity;

import java.util.List;

public class PilaScarti extends Mazzo {

    private List<Carta> pilaScarti;

	/**
	 * 
	 * @param carta
	 */
	public void aggiungiCarta(Carta carta) {
		pilaScarti.add(carta);
	}

}