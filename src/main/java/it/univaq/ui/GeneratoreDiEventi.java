package it.univaq.ui;

import it.univaq.technical.Fase;

import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;

import java.time.Instant;

public class GeneratoreDiEventi {

	/**
	 * 
	 * @param fase
	 */
	public void generaEvento(Fase fase) {
		// TODO - implement GeneratoreDiEventi.generaEvento
		throw new UnsupportedOperationException();
	}

	public void messaggioErrorePA() {
		// TODO - implement GeneratoreDiEventi.messaggioErrorePA
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fase
	 */
	public void aggiungiFase(Fase fase) {
		// TODO - implement GeneratoreDiEventi.aggiungiFase
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fase
	 */
	public synchronized void startTimer(Fase fase) {
        Instant scadenza = Instant.now().plusSeconds(SECONDI_DURATA);
        this.finestraTemporaleGenerata = new FinestraTemporale(scadenza);
        this.finestraTemporaleGenerata.getSecondiRimanenti();

        System.out.println("Timer fase: " + fase + "avviato. Scade alle: " + scadenza);
	}

	public synchronized void resetTimer(Fase fase) {
        if (this.finestraTemporaleGenerata != null) {
            Instant nuovaScadenza = Instant.now().plusSeconds(SECONDI_DURATA);
            this.finestraTemporaleGenerata.setFine(nuovaScadenza);
            this.finestraTemporaleGenerata.getSecondiRimanenti();

            System.out.println("Timer resettato per: " + fase + ". Nuova scadenza: " + nuovaScadenza);
        }

		throw new UnsupportedOperationException();
	}

	public void resetTimer() {
		// TODO - implement GeneratoreDiEventi.resetTimer
		throw new UnsupportedOperationException();
	}

	private FinestraTemporale finestraTemporaleGenerata;

}