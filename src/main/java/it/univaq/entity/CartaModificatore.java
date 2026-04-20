package it.univaq.entity;

public class CartaModificatore extends Carta {

	private Integer valorePositivo;
	private Integer valoreNegativo;
	private String effetto;

    public CartaModificatore(Integer valorePositivo, Integer valoreNegativo, String effetto) {
        super("ciao", "Carta Modificatore");
        this.valorePositivo = valorePositivo;
        this.valoreNegativo = valoreNegativo;
        this.effetto = effetto;
    }

    public CartaModificatore(Integer valorePositivo, String effetto) {
        super("ciao", "Carta Modificatore");
        this.valorePositivo = valorePositivo;
        this.valoreNegativo = null;
        this.effetto = effetto;
    }

public Integer getValoreScelto(boolean usaPositivo) {
    if (usaPositivo) {
        return this.valorePositivo;
    } else {
        return this.valoreNegativo;
    }
}


    public Integer getValorePositivo() {
        return valorePositivo;
    }

    public void setValorePositivo(Integer valorePositivo) {
        this.valorePositivo = valorePositivo;
    }

    public Integer getValoreNegativo() {
        return valoreNegativo;
    }

    public void setValoreNegativo(Integer valoreNegativo) {
        this.valoreNegativo = valoreNegativo;
    }

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }

}