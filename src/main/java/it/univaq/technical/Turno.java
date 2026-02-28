package it.univaq.technical;

import it.univaq.ui.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Turno {

	private List<Fase> pilaFasi;
	private Player giocatoreDiTurno;

    public Turno(@NotNull List<Fase> pilaFasi, Player giocatoreDiTurno ) {
        this.pilaFasi = pilaFasi;
        this.giocatoreDiTurno = giocatoreDiTurno;
    }

    /**
	 * 
	 * @param Fase
	 */
	public void resetTimer(int Fase) {
		// TODO - implement Turno.resetTimer
		throw new UnsupportedOperationException();
	}

	public Fase getFaseCorrente() {
		return pilaFasi.getFirst();
	}

	public void timeout() {
		// TODO - implement Turno.timeout
		throw new UnsupportedOperationException();
	}

	public void terminaFaseAttuale() {
		// TODO - implement Turno.terminaFaseAttuale
		throw new UnsupportedOperationException();
	}

	public void cartaGiocata() {
		// TODO - implement Turno.cartaGiocata
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fase
	 */
	public Boolean iniziaFase(Fase fase) {
        this.aggiungiFase(fase);
        return true;
	}

	/**
	 *
	 * @param fase
	 */
	public void aggiungiFase(Fase fase) {
        pilaFasi.add(fase);
	}

	public void fineFaseAttuale() {
		// TODO - implement Turno.fineFaseAttuale
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param mossaSelezionata
	 */
	public void verificaPA(int mossaSelezionata) {
		// TODO - implement Turno.verificaPA
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param mossaSelezionata
	 */
	public void eseguiMossa(int mossaSelezionata) {
		// TODO - implement Turno.eseguiMossa
		throw new UnsupportedOperationException();
	}

	public void checkPARimasti() {
		// TODO - implement Turno.checkPARimasti
		throw new UnsupportedOperationException();
	}

}