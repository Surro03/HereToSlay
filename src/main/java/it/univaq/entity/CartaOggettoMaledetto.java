package it.univaq.entity;

public class CartaOggettoMaledetto extends CartaOggetto {

	private String Effetto;

    public CartaOggettoMaledetto(String effetto) {
        Effetto = effetto;
    }

    public String getEffetto() {
        return Effetto;
    }

    public void setEffetto(String effetto) {
        Effetto = effetto;
    }
}