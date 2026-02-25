package it.univaq.entity;

public class CartaOggettoDiSupporto extends CartaOggetto {

	private String Effetto;

    public CartaOggettoDiSupporto(String effetto) {
        Effetto = effetto;
    }

    public String getEffetto() {
        return Effetto;
    }

    public void setEffetto(String effetto) {
        Effetto = effetto;
    }
}