package it.univaq.entity;

import java.util.ArrayList;
import java.util.List;

public class MazzoPesca extends PilaDiCarte<Carta> {

    public List<Carta> pescaCarteIniziali(int numero) {
        List<Carta> pescate = new ArrayList<>();
        for(int i = 0; i < numero; i++) {
            pescate.add(this.pescaDallaCima());
        }
        return pescate;
    }

}