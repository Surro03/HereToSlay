package it.univaq.entity;

public class CartaModificatore extends Carta {

	private Integer Valore;
	private Float Valore1;
	private Float Valore2;
	private String Effetto;

    public CartaModificatore(Integer valore, Float valore1, Float valore2, String effetto) {
        Valore = valore;
        Valore1 = valore1;
        Valore2 = valore2;
        Effetto = effetto;
    }

    public Integer getValore() {
        return Valore;
    }

    public void setValore(Integer valore) {
        Valore = valore;
    }

    public Float getValore1() {
        return Valore1;
    }

    public void setValore1(Float valore1) {
        Valore1 = valore1;
    }

    public Float getValore2() {
        return Valore2;
    }

    public void setValore2(Float valore2) {
        Valore2 = valore2;
    }

    public String getEffetto() {
        return Effetto;
    }

    public void setEffetto(String effetto) {
        Effetto = effetto;
    }
}