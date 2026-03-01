package it.univaq.entity;

public class Tavolo {

    private PilaScarti pilaScarti;

	/**
	 * 
	 * @param carta
	 */
	public void scartaCarta(Carta carta) {
        pilaScarti.aggiungiCarta(carta);
	}

	/**
	 * 
	 * @param player
	 */
	public void checkVittoria(int player) {
		// TODO - implement Tavolo.checkVittoria
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param carta
	 */
	public void ScartaCarta(Carta carta) {
		// TODO - implement Tavolo.ScartaCarta
		throw new UnsupportedOperationException();
	}

	public void operation() {
		// TODO - implement Tavolo.operation
		throw new UnsupportedOperationException();
	}

}