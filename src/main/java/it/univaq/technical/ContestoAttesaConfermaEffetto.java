package it.univaq.technical;


import it.univaq.ui.GameObserver;

public record ContestoAttesaConfermaEffetto() implements PayloadAttesa {
    @Override
    public void notificaUI(GameObserver gameObserver) {
        gameObserver.richiediConfermaEffetto();
    }
}
