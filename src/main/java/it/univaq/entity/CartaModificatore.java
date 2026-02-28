package it.univaq.entity;

public class CartaModificatore extends Carta {

	private Integer valore;
	private Float valore1;
	private Float valore2;
	private String effetto;

    public CartaModificatore(Integer valore, Float valore1, Float valore2, String effetto) {
        this.valore = valore;
        this.valore1 = valore1;
        this.valore2 = valore2;
        this.effetto = effetto;
    }

    public Integer getValore() {
        return valore;
    }

    public void setValore(Integer valore) {
        this.valore = valore;
    }

    public Float getValore1() {
        return valore1;
    }

    public void setValore1(Float valore1) {
        this.valore1 = valore1;
    }

    public Float getValore2() {
        return valore2;
    }

    public void setValore2(Float valore2) {
        this.valore2 = valore2;
    }

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }
}