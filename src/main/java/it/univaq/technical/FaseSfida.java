package it.univaq.technical;

import it.univaq.ui.InterfacciaUtente;

public class FaseSfida implements Fase {

	private Integer idSfidante;
	private Integer idPlayer;
	private Float valorePlayer;
	private Float valoreSfidante;

	/**
	 * 
	 * @param idSfidante
	 * @param idPlayer
	 */
	public void aggiungi(int idSfidante, int idPlayer) {
		// TODO - implement FaseSfida.aggiungi
		throw new UnsupportedOperationException();
	}

	public void CalcolaVincitore() {
		// TODO - implement FaseSfida.CalcolaVincitore
		throw new UnsupportedOperationException();
	}

    @Override
    public boolean eseguiFase(Turno turno, InterfacciaUtente gui) {
        return true;
    }

}