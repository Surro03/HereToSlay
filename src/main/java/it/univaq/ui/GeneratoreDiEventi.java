package it.univaq.ui;
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
	 * @param Fase
	 */
	public void generaEvento(int Fase) {
		// TODO - implement GeneratoreDiEventi.generaEvento
		throw new UnsupportedOperationException();
	}

	public void messaggioErrorePA() {
		System.out.println("GeneratoreDiEventi: Errore, Punti Azione insufficienti per questa mossa!");
	}

	/**
	 * 
	 * @param Fase
	 */
	public void aggiungiFase(int Fase) {
		// TODO - implement GeneratoreDiEventi.aggiungiFase
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Fase
	 */
	public void startTimer(int Fase) {
		resetTimer();
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

	public void resetTimer() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			System.out.println("GeneratoreDiEventi: Timer disattivato.");
		}
	}

	private FinestraTemporale finestraTemporaleGenerata;

}