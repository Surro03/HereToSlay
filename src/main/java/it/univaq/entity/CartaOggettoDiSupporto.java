package it.univaq.entity;

public class CartaOggettoDiSupporto extends CartaOggetto {

	private String effetto;

    public CartaOggettoDiSupporto(String effetto) {
        this.effetto = effetto;
    }

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }
}