package it.univaq.technical;

import java.time.Instant;
import java.time.Duration;

public class FinestraTemporale {

	private Instant fine;

    public FinestraTemporale(Instant fine) {
        this.fine = fine;
    }

    public Instant getFine() {
        return fine;
    }

    public void setFine(Instant fine) {
        this.fine = fine;
    }

    /**
     * Restituisce i secondi rimanenti.
     * Se il tempo è scaduto, restituisce 0.
     */
    public long getSecondiRimanenti() {
        if (fine == null) return 0;

        long secondi = Duration.between(Instant.now(), fine).toSeconds();
        return Math.max(0, secondi); // Evitiamo numeri negativi se è già scaduto
    }

    public boolean isAncoraValida() {
        return fine != null && Instant.now().isBefore(fine);
    }
}