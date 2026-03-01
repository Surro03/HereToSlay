package it.univaq.entity;

public class CartaSfida extends Carta {

	private Integer classe;

    public CartaSfida(Integer classe) {
        super("ciao");
        this.classe = classe;
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }
}