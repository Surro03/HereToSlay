package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.CartaEroe;

import java.util.ArrayList;
import java.util.List;

public abstract class Fase {

    List<Carta> carteGiocabili = new ArrayList<>();

    public void salvaCartaGiocabili(List<Carta> cartaGiocabili) {
        this.carteGiocabili = cartaGiocabili;
    }

    public List<Carta> getCarteGiocabili() {
        return this.carteGiocabili;
    }
}