package it.univaq.technical;

public interface FinestraTemporaleObserver {

    public void timerStarted(int durata);

    public void timerRestarting(int durata);

    public void timerStopped();

    public void timerInterrupted();
}
