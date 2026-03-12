package it.univaq.technical;

import java.time.Instant;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GeneratoreDiEventi {
	private List<FinestraTemporaleObserver> finestraTemporaleObservers;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> countdownTask; // Riferimento per fermare il countdown
    private FinestraTemporale finestraTemporaleGenerata;
    private final int SECONDI_DURATA;

	public GeneratoreDiEventi(int SECONDI_DURATA, List<FinestraTemporaleObserver> finestraTemporaleObservers) {
        this.SECONDI_DURATA = SECONDI_DURATA;
        this.finestraTemporaleObservers = finestraTemporaleObservers;
	}

    public void notifyStartTimer(int durata, Fase fase) {
        for (FinestraTemporaleObserver finestraTemporaleObserver : finestraTemporaleObservers ) {
            finestraTemporaleObserver.timerStarted(durata, fase);
        }
    }

    public void notifyRestartTimer(int durata){
        for (FinestraTemporaleObserver finestraTemporaleObserver : finestraTemporaleObservers ) {
            finestraTemporaleObserver.timerRestarting(durata);
        }
    }

    public void notifyStopTimer(Fase fase) {
        for (FinestraTemporaleObserver finestraTemporaleObserver : finestraTemporaleObservers ) {
            finestraTemporaleObserver.timerStopped(fase);
        }
    }

    public void notifyInterruptionTimer(Fase fase){
        for (FinestraTemporaleObserver finestraTemporaleObserver : finestraTemporaleObservers ) {
            finestraTemporaleObserver.timerInterrupted(fase);
        }
    }
	/**
	 * 
	 * @param fase
	 */
	public void generaEvento(Fase fase) {
		// TODO - implement GeneratoreDiEventi.generaEvento
		throw new UnsupportedOperationException();
	}


    public synchronized void startTimerL(Fase fase) {
        // 1. Se c'è un timer precedente ancora in attesa, lo annulliamo
        if (countdownTask != null) {
            countdownTask.cancel(false);
        }

        // 2. Messaggio iniziale (appare una volta sola)
        this.notifyStartTimer(SECONDI_DURATA, fase);


        // 3. Impostiamo la scadenza tecnica (per i controlli isAncoraValida())
        Instant scadenza = Instant.now().plusSeconds(SECONDI_DURATA);
        this.finestraTemporaleGenerata = new FinestraTemporale(scadenza);

        // 4. Programmiamo il messaggio di "Tempo Scaduto" tra X secondi
        // Questo messaggio apparirà solo quando il tempo è effettivamente finito

        countdownTask = scheduler.schedule(() -> this.notifyStopTimer(fase), SECONDI_DURATA, TimeUnit.SECONDS);
    }

    // AGGIUNGI QUESTO METODO: Fondamentale per fermare il timer se il giocatore è veloce!
    public synchronized void stopTimer(Fase fase) {
        if (countdownTask != null) {
            countdownTask.cancel(false);
            this.notifyInterruptionTimer(fase);

        }
    }

	public synchronized void resetTimerL(Fase fase) {
        if (this.finestraTemporaleGenerata != null) {
            // 1. CANCELLA il vecchio messaggio di scadenza programmato
            if (countdownTask != null) {
                countdownTask.cancel(false);
            }

            // 2. AGGIORNA la logica (la scadenza effettiva)
            Instant nuovaScadenza = Instant.now().plusSeconds(SECONDI_DURATA);
            this.finestraTemporaleGenerata.setFine(nuovaScadenza);

            // 3. AVVISA l'utente del reset
            this.notifyRestartTimer(SECONDI_DURATA);

            // 4. RIPROGRAMMA il messaggio di timeout per la nuova scadenza
            countdownTask = scheduler.schedule(() -> this.notifyStopTimer(fase), SECONDI_DURATA, TimeUnit.SECONDS);
        }
	}

    public boolean isTempoValido() {
        // Controlliamo che la finestra esista E che sia ancora valida
        return finestraTemporaleGenerata != null && finestraTemporaleGenerata.isAncoraValida();
    }

}