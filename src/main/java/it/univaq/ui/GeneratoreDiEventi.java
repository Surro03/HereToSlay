package it.univaq.ui;

import it.univaq.technical.Fase;

import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;

import java.time.Duration;
import java.time.Instant;

import it.univaq.controller.HereToSlay;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GeneratoreDiEventi {
	private Timer timer;
	private HereToSlay controller;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> countdownTask; // Riferimento per fermare il countdown
    private FinestraTemporale finestraTemporaleGenerata;
    private static final int SECONDI_DURATA = 30;

	public GeneratoreDiEventi() {
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

/*
	/**
	 * 
	 * @param fase
	 */
	/*public synchronized void startTimerL(Fase fase) {

        Instant scadenza = Instant.now().plusSeconds(SECONDI_DURATA);
        this.finestraTemporaleGenerata = new FinestraTemporale(scadenza);
        this.finestraTemporaleGenerata.getSecondiRimanenti();

        System.out.println("Timer fase: " + fase + "avviato. Scade alle: " + scadenza);
	}*/



    public synchronized void startTimerL(Fase fase) {
        // 1. Se c'è un timer precedente ancora in attesa, lo annulliamo
        if (countdownTask != null) {
            countdownTask.cancel(false);
        }

        // 2. Messaggio iniziale (appare una volta sola)
        System.out.println("\n>>> [SISTEMA] Hai " + SECONDI_DURATA + " secondi per giocare una carta...");

        // 3. Impostiamo la scadenza tecnica (per i controlli isAncoraValida())
        Instant scadenza = Instant.now().plusSeconds(SECONDI_DURATA);
        this.finestraTemporaleGenerata = new FinestraTemporale(scadenza);

        // 4. Programmiamo il messaggio di "Tempo Scaduto" tra X secondi
        countdownTask = scheduler.schedule(() -> {
            // Questo messaggio apparirà solo quando il tempo è effettivamente finito
            System.out.println("\n[!] TEMPO SCADUTO per la fase " + fase.getClass().getSimpleName() + "!");
            System.out.print("> "); // Ristampa il prompt per non lasciare la riga vuota
        }, SECONDI_DURATA, TimeUnit.SECONDS);
    }

    // AGGIUNGI QUESTO METODO: Fondamentale per fermare il timer se il giocatore è veloce!
    public synchronized void stopTimer() {
        if (countdownTask != null) {
            countdownTask.cancel(false);
            // System.out.println("[DEBUG] Timer interrotto.");
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
            System.out.println("\n[!] Timer RESETTATO! Altri " + SECONDI_DURATA + " secondi per rispondere...");
            System.out.print("> "); // Ripristina il cursore per l'input

            // 4. RIPROGRAMMA il messaggio di timeout per la nuova scadenza
            countdownTask = scheduler.schedule(() -> {
                System.out.println("\n[!] TEMPO SCADUTO per la fase " + fase.getClass().getSimpleName() + "!");
                System.out.print("> ");
            }, SECONDI_DURATA, TimeUnit.SECONDS);
        }
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

    public boolean isTempoValido() {
        // Controlliamo che la finestra esista E che sia ancora valida
        return finestraTemporaleGenerata != null && finestraTemporaleGenerata.isAncoraValida();
    }

}