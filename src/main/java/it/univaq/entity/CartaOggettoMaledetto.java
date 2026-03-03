package it.univaq.entity;

public class CartaOggettoMaledetto extends CartaOggetto {

	private String effetto;

    public CartaOggettoMaledetto(String effetto) {
        this.effetto = effetto;
    }

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }
}