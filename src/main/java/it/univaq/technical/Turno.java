package it.univaq.technical;

import it.univaq.ui.Player;
import it.univaq.entity.Carta;

import java.util.ArrayList;
import java.util.List;

public class Turno {
    private int paRimanenti;
    private boolean Successo;
    private List<Fase> pilaFasi = new ArrayList<Fase>();
    private Player giocatoreDiTurno;
    private Fase faseAttuale;

    public record Risultato(boolean successo, int PA) {
    }

    public Turno( List<Fase> pilaFasi, Player giocatoreDiTurno) {
        this.pilaFasi = pilaFasi;
        this.giocatoreDiTurno = giocatoreDiTurno;
        this.faseAttuale = pilaFasi.getFirst();
        this.paRimanenti = 3;
    }

    public Risultato verificaPA(int mossaSelezionata) {
        this.eseguiMossa(mossaSelezionata);
        return new Risultato(Successo, paRimanenti);
    }

    private void eseguiMossa(int mossaSelezionata) {
        if (mossaSelezionata == 1 || mossaSelezionata == 2 || mossaSelezionata == 3 || mossaSelezionata == 4 || mossaSelezionata == 5) {
            // Gioca una carta eroe, oggetto, magia o pesca o attivo un effetto(costo 1)
            if (paRimanenti >= 1) {
                paRimanenti--;
                Successo = true;
            } else {
                Successo = false;
            }
        } else if (mossaSelezionata == 6) {
            // Attacca un mostro (costo 2)
            if (paRimanenti >= 2) {
                paRimanenti = paRimanenti - 2;
                Successo = true;
            } else {
                Successo = false;
            }
        } else if (mossaSelezionata == 7) {
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

    public Fase getFaseCorrente () {
        return pilaFasi.getFirst();
    }

    public int checkPaRimasti() {
        // 1.7: paRimanenti (ritorno)
        return paRimanenti;
    }

    public void fineFaseAttuale() {
        if (!pilaFasi.isEmpty()) {
            Fase faseDaChiudere = pilaFasi.removeFirst();
            System.out.println("Turno: terminaFaseAttuale(). Chiusa la fase: " + faseDaChiudere.getClass().getSimpleName());
        }
    }

    public void timeout() {
        // TODO - implement Turno.timeout
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param fase
     */
    public void resetTimer(Fase fase) {
        // TODO - implement Turno.resetTimer
        throw new UnsupportedOperationException();
    }


	public void getPaRimasti() {
		// TODO - implement Turno.checkPARimasti
		throw new UnsupportedOperationException();
	}

}