package it.univaq.entity;

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

    public Carta rimuoviCarta(int numeroCarta) {
        Carta carta = carteMano.get(numeroCarta);
        carteMano.remove(numeroCarta);
        return carta;

    }

    public Carta getCarta(int numeroCarta) {
        Carta carta = carteMano.get(numeroCarta);
        carteMano.remove(numeroCarta);
        return carta;

    }

    public List<Integer> getIndiciCarteDiTipo(Class<?> tipoCarta) {
        List<Integer> indici = new ArrayList<>();
        List<Carta> carte = this.getCarteMano();

        for (int i = 0; i < carte.size(); i++) {
            if (tipoCarta.isAssignableFrom(carte.get(i).getClass())) {
                indici.add(i);
            }
        }
        return indici;
    }

    public void setNumeroCarte(Integer numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    @Override
    public String toString() {
        if (carteMano == null || carteMano.isEmpty()) {
            return "La mano è vuota.";
        }

        StringBuilder sb = new StringBuilder("--- TUA MANO ---\n");
        for (int i = 0; i < carteMano.size(); i++) {
            // [1] Nome Carta
            // [2] Nome Carta ...
            if(carteMano.get(i).getNome() != null) {
                sb.append("[").append(i + 1).append("] ")
                        .append(carteMano.get(i).getNome())
                        .append("\n");
            }
            else
                sb.append("[").append(i + 1).append("] ")
                        .append(carteMano.get(i))
                        .append("\n");
        }
        return sb.toString();
    }
}