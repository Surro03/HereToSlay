package it.univaq.technical;


import it.univaq.ui.GameObserver;

public record ContestoAttesaConfermaEffetto() implements ContestoAttesa {
    @Override
    public void notificaUI(GameObserver gameObserver) {
        gameObserver.richiediConfermaEffetto();
    }
}
