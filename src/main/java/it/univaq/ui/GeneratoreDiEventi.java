package it.univaq.ui;

import it.univaq.technical.Fase;

import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;

import java.time.Instant;

import it.univaq.controller.HereToSlay;
import java.util.Timer;
import java.util.TimerTask;
public class GeneratoreDiEventi {
	private Timer timer;
	private HereToSlay controller;

	public GeneratoreDiEventi(HereToSlay controller) {
		this.controller = controller;
	}


	/**
	 * 
	 * @param fase
	 */
	public void generaEvento(Fase fase) {
		// TODO - implement GeneratoreDiEventi.generaEvento
		throw new UnsupportedOperationException();
	}

	public void messaggioErrorePA() {
		System.out.println("GeneratoreDiEventi: Errore, Punti Azione insufficienti per questa mossa!");
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
	public synchronized void startTimerL(Fase fase) {
        Instant scadenza = Instant.now().plusSeconds(SECONDI_DURATA);
        this.finestraTemporaleGenerata = new FinestraTemporale(scadenza);
        this.finestraTemporaleGenerata.getSecondiRimanenti();

        System.out.println("Timer fase: " + fase + "avviato. Scade alle: " + scadenza);
	}

	public synchronized void resetTimerL(Fase fase) {
        if (this.finestraTemporaleGenerata != null) {
            Instant nuovaScadenza = Instant.now().plusSeconds(SECONDI_DURATA);
            this.finestraTemporaleGenerata.setFine(nuovaScadenza);
            this.finestraTemporaleGenerata.getSecondiRimanenti();

            System.out.println("Timer resettato per: " + fase + ". Nuova scadenza: " + nuovaScadenza);
        }

		throw new UnsupportedOperationException();
	}

    /**
     *
     * @param Fase
     */
    public void startTimerG(int Fase) {
        resetTimerG();
        timer = new Timer();
        System.out.println("GeneratoreDiEventi: Timer avviato per " + Fase + " secondi. Avversari, potete lanciare una Sfida!");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTempo scaduto per la sfida!");
                // Chiama il controller per avvisarlo
                controller.timeout();
            }
        }, Fase * 1000L);

    }

	public void resetTimerG() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			System.out.println("GeneratoreDiEventi: Timer disattivato.");
		}
	}

	private FinestraTemporale finestraTemporaleGenerata;

}