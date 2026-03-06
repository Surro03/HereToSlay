package it.univaq.ui;

import it.univaq.entity.Carta;

import java.util.ArrayList;
import java.util.List;

public class Mano {

    private List<Carta> carteMano;
	private Integer numeroCarte;

    public Mano(List<Carta> carteMano) {
        this.carteMano = carteMano;
    }

    public List<Carta> getCarteMano() {
        return this.carteMano;
    }

    public void setCarteMano(List<Carta> carteMano) {
        this.carteMano = carteMano;
    }

    public Integer getNumeroCarte() {
        return carteMano.size();
    }

    public void setNumeroCarte(Integer numeroCarte) {
        this.numeroCarte = numeroCarte;
    }
}