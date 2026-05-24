package it.univaq.technical;


public record ContestoAttesaConfermaEffetto() implements ContestoAttesa {
    @Override
    public void notificaUI(GameObserver gameObserver) {
        gameObserver.richiediConfermaEffetto();
    }
}
