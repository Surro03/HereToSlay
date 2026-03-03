package it.univaq.entity;

public class CartaModificatore extends Carta {

	private Float valorePositivo;
	private Float valoreNegativo;
	private String effetto;

    public CartaModificatore(Float valorePositivo, Float valoreNegativo, String effetto) {
        super("ciao");
        this.valorePositivo = valorePositivo;
        this.valoreNegativo = valoreNegativo;
        this.effetto = effetto;
    }

    public CartaModificatore(Integer valore, Float valorePositivo, String effetto) {
        super("ciao");
        this.valorePositivo = valorePositivo;
        this.valoreNegativo = null;
        this.effetto = effetto;
    }

    public Float getValorePositivo() {
        return valorePositivo;
    }

    public void setValorePositivo(Float valorePositivo) {
        this.valorePositivo = valorePositivo;
    }

    public Float getValoreNegativo() {
        return valoreNegativo;
    }

    public void setValoreNegativo(Float valoreNegativo) {
        this.valoreNegativo = valoreNegativo;
    }

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }
}