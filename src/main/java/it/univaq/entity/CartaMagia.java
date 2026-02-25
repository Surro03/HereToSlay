package it.univaq.entity;

public class CartaMagia extends Carta {

	private String Effetto;

    public CartaMagia(String effetto) {
        Effetto = effetto;
    }

    public String getEffetto() {
        return Effetto;
    }

    public void setEffetto(String effetto) {
        Effetto = effetto;
    }
}