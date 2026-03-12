package it.univaq.technical;

public interface FinestraTemporaleObserver {

    void timerStarted(int durata, Fase fase);

    void timerRestarting(int durata);

    void timerStopped(Fase fase);

    void timerInterrupted(Fase fase);
}
