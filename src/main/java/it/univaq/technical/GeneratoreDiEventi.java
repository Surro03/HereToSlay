package it.univaq.technical;

import it.univaq.ui.GameObserver;

import java.time.Instant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class GeneratoreDiEventi {
	private List<FinestraTemporaleObserver> finestraTemporaleObservers = new ArrayList<>();

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> countdownTask; // Riferimento per fermare il countdown
    private FinestraTemporale finestraTemporaleGenerata;
    private final int SECONDI_DURATA;


	public GeneratoreDiEventi(int SECONDI_DURATA) {
        this.SECONDI_DURATA = SECONDI_DURATA;
	}

    public void addObserver(FinestraTemporaleObserver finestraTemporaleObserver) {
        finestraTemporaleObservers.add(finestraTemporaleObserver);
    }

    public void removeObserver(FinestraTemporaleObserver finestraTemporaleObserver) {
        finestraTemporaleObservers.remove(finestraTemporaleObserver);
    }

    private void notificaTutti(Consumer<FinestraTemporaleObserver> action) {
        for (FinestraTemporaleObserver obs : finestraTemporaleObservers) {
            action.accept(obs);
        }
    }
	/**
	 * 
	 * @param fase
	 */
//	public void generaEvento(ContestoEvento contestoEvento) {
//		// TODO
//	}


    public synchronized void startTimer(Fase fase) {
        // 1. Se c'è un timer precedente ancora in attesa, lo annulliamo
        if (countdownTask != null) {
            countdownTask.cancel(false);
        }

        // 2. Messaggio iniziale (appare una volta sola)
        this.notificaTutti(finestraTemporaleObserver -> finestraTemporaleObserver.timerStarted(SECONDI_DURATA, fase));


        // 3. Impostiamo la scadenza tecnica (per i controlli isAncoraValida())
        Instant scadenza = Instant.now().plusSeconds(SECONDI_DURATA);
        this.finestraTemporaleGenerata = new FinestraTemporale(scadenza);

        // 4. Programmiamo il messaggio di "Tempo Scaduto" tra X secondi
        // Questo messaggio apparirà solo quando il tempo è effettivamente finito

        countdownTask = scheduler.schedule(() -> this.notificaTutti(finestraTemporaleObserver -> finestraTemporaleObserver.timerStopped(fase)), SECONDI_DURATA, TimeUnit.SECONDS);
    }

    // Arresto manuale (quando il giocatore risponde o passa)
    public synchronized void stopTimerGiocatore(Fase fase) {
        if (countdownTask != null && !countdownTask.isDone()) {
            countdownTask.cancel(false);
            this.notificaTutti(finestraTemporaleObserver -> finestraTemporaleObserver.timerInterrupted(fase));
        }
    }

	public synchronized void resetTimer(Fase fase) {
        if (this.finestraTemporaleGenerata != null) {
            // 1. CANCELLA il vecchio messaggio di scadenza programmato
            if (countdownTask != null) {
                countdownTask.cancel(false);
            }

            // 2. AGGIORNA la logica (la scadenza effettiva)
            Instant nuovaScadenza = Instant.now().plusSeconds(SECONDI_DURATA);
            this.finestraTemporaleGenerata.setFine(nuovaScadenza);

            // 3. AVVISA l'utente del reset
            this.notificaTutti(finestraTemporaleObserver -> finestraTemporaleObserver.timerRestarting(SECONDI_DURATA));

            // 4. RIPROGRAMMA il messaggio di timeout per la nuova scadenza
            countdownTask = scheduler.schedule(() -> this.notificaTutti(obs -> obs.timerStarted(SECONDI_DURATA,fase)), SECONDI_DURATA, TimeUnit.SECONDS);
        }
	}

    public boolean isTempoValido() {
        // Controlliamo che la finestra esista E che sia ancora valida
        return finestraTemporaleGenerata != null && finestraTemporaleGenerata.isAncoraValida();
    }

}