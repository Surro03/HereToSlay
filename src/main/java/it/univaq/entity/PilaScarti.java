package it.univaq.entity;

import java.util.ArrayList;
import java.util.List;

public class PilaScarti extends Mazzo {

    private List<Carta> pilaScarti;

    public PilaScarti() {
        this.pilaScarti = new ArrayList<>();
    }


    /**
	 * 
	 * @param carta
	 */
	public void aggiungiCarta(Carta carta) {
		pilaScarti.add(carta);
	}

}