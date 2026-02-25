package it.univaq.entity;

public class CartaSfida extends Carta {

	private Integer Classe;

    public CartaSfida(Integer classe) {
        Classe = classe;
    }

    public Integer getClasse() {
        return Classe;
    }

    public void setClasse(Integer classe) {
        Classe = classe;
    }
}