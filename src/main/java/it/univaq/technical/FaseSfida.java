package it.univaq.technical;



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
    public boolean eseguiFase(Turno turno) {
        return true;
    }

}