package it.univaq.ui;
import java.time.Instant;

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
}