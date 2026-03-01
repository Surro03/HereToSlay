package it.univaq.entity;

public class CartaMagia extends Carta {

	private String effetto;

    public CartaMagia(String effetto) {
        super("ciao");
        this.effetto = effetto;
    }

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }
}