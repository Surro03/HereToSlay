package it.univaq.entity;

import java.util.ArrayList;
import java.util.List;

public class MazzoMostri extends PilaDiCarte<CartaMostro> {

    public MazzoMostri(List<CartaMostro> carteIniziali) {
        super(carteIniziali); // Passa la lista al padre
    }

    public MazzoMostri() {
        super(); // Passa la lista al padre
    }

    public List<CartaMostro> pescaCarte(int numero) {
        List<CartaMostro> pescate = new ArrayList<>();
        for(int i = 0; i < numero; i++) {
            CartaMostro pescata = this.pescaDallaCima();
            if (pescata != null) {
                pescate.add(pescata);
            }
        }
        return pescate;
    }

}