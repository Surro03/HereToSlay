package it.univaq.technical;

import it.univaq.entity.Carta;

import java.util.ArrayList;
import java.util.List;

public class Turno {
    private int paRimanenti;
    private boolean Successo;
    private List<Fase> pilaFasi=new ArrayList<Fase>();
    private Carta CartaCorrente;
    public record  Risultato (boolean successo, int PA) {
    }
    public Risultato verificaPA(int mossaSelezionata) {
        this.eseguiMossa(mossaSelezionata);
        return new Risultato(Successo, paRimanenti);
    }
    private void eseguiMossa(int mossaSelezionata) {
        if (mossaSelezionata == 1 || mossaSelezionata ==2) {
            // Gioca una carta o pesca una carta eroe, oggetto, magia o attivo un effetto(costo 1)
            if (paRimanenti >= 1) {
                paRimanenti--;
                Successo = true;
            } else {
                Successo = false;
            }
        } else if (mossaSelezionata == 5) {
            // Attacca un mostro (costo 2)
            if (paRimanenti >= 2) {
                paRimanenti = paRimanenti - 2;
                Successo = true;
            } else {
                Successo = false;
            }
        } else if (mossaSelezionata == 6) {
            // Pesca 5 carte (costo 3)
            if (paRimanenti >= 3) {
                paRimanenti = paRimanenti - 3;
                Successo = true;
            } else {
                Successo = false;
            }
        }
    }

    public Boolean iniziaFase(Fase faseMossaGiocata) {
    this.aggiungiFase(faseMossaGiocata);
    return true;
    }

    public void aggiungiFase(Fase faseMossaGiocata) {
        pilaFasi.add(faseMossaGiocata);
    }

    // 1.6: checkPaRimasti()
    public int checkPaRimasti() {
        // 1.7: paRimanenti (ritorno)
        return paRimanenti;
    }

    public Fase getFaseCorrente () {
        return pilaFasi.getLast();
    }

    public void salvaCartaGiocata(Carta Carta){
        CartaCorrente= Carta;
    }
}
