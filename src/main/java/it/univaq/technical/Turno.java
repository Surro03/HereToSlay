package it.univaq.technical;

import it.univaq.entity.Player;
import it.univaq.entity.Tavolo;

import java.util.ArrayDeque;
import java.util.Deque;

public class Turno {
    private int paRimanenti;
    private final Player giocatoreDiTurno;
    private Object risultatoSottoFase;
    private TipoAttesa tipoAttesa;
    // Usiamo Deque per una VERA struttura a Pila
    private Deque<Fase> pilaFasi;
    
    // Variabile "Posta in arrivo" per i dati che ci manda la UI
    private Object inputInSospeso;

    public Turno(Player giocatoreDiTurno) {
        this.giocatoreDiTurno = giocatoreDiTurno;
        this.paRimanenti = 3;
        this.pilaFasi = new ArrayDeque<>();
        this.inputInSospeso = null;
    }

    // --- GRASP: INFORMATION EXPERT (Gestione PA) ---
    public boolean consumaPA(int costo) {
        if (this.paRimanenti >= costo) {
            this.paRimanenti -= costo;
            return true;
        }
        return false;
    }

    public int getPaRimasti() {
        return this.paRimanenti;
    }

    public Player getGiocatoreDiTurno() {
        return this.giocatoreDiTurno;
    }

    // --- GESTIONE DELLA PILA E INPUT (Il Segnalibro) ---
    public void aggiungiFaseInCima(Fase nuovaFase) {
        this.pilaFasi.push(nuovaFase);
    }

    // Metodo che le Fasi usano per leggere il dato mandato dalla UI
    public Object popInput() {
        Object temp = this.inputInSospeso;
        this.inputInSospeso = null; // Svuoto la "posta in arrivo"
        return temp;
    }

    // Metodo per comunicare con le fasi nel turno
    public void riceviInput(Object input) {
        this.inputInSospeso = input;

        // Risveglio il motore!
        this.avanzaMotoreFasi();
    }

    // --- IL MOTORE A STATI ASINCRONO ---
    public void avanzaMotoreFasi() {
        if (this.isTerminato()) return;

        // Guardo chi c'è in cima adesso
        Fase faseInCima = pilaFasi.peek();
        assert faseInCima != null;
        boolean faseConclusa = faseInCima.eseguiFase(this);

        if (faseConclusa) {
            pilaFasi.pop(); 
            this.avanzaMotoreFasi();
        }
        // ---> LA PATCH DEL MOTORE <---
        else if (pilaFasi.peek() != faseInCima) {
            // La fase ha fatto return false, MA ha inserito una nuova sotto-fase!
            // Non mi addormento, ma processo subito la nuova arrivata!
            this.avanzaMotoreFasi();
        }
        
        // Se non entra in nessuno dei due if, il motore si addormenta serenamente.
    }

    public boolean isTerminato() {
        if (pilaFasi.isEmpty()) {
            System.out.println("Turno di " + giocatoreDiTurno.getNome() + " terminato.");
            return true;
        }
        return false;
    }

    public void salvaRisultatoSottoFase(Object risultato) {
        this.risultatoSottoFase = risultato;
    }

    public Object popRisultatoSottoFase() {
        Object temp = this.risultatoSottoFase;
        this.risultatoSottoFase = null;
        return temp;
    }

    public void setAttesa(TipoAttesa tipoAttesa) {this.tipoAttesa = tipoAttesa;}

    public TipoAttesa getAttesa() {return this.tipoAttesa;}

}


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
     /*
    public class Turno {
    private int paRimanenti;
    private boolean Successo;
    private List<Fase> pilaFasi;
    private Player giocatoreDiTurno;
    private Fase faseAttuale;
    public record Risultato(boolean successo, int PA) {
    }
    
   
    public Turno(List<Fase> pilaFasi, Player giocatoreDiTurno) {
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
        return pilaFasi.get(pilaFasi.size()-1);
    }

    public int getPaRimasti() {
        // 1.7: paRimanenti (ritorno)
        return paRimanenti;
    }

    public void fineFaseAttuale() {
        if (!pilaFasi.isEmpty()) {
            Fase faseDaChiudere = pilaFasi.removeLast();
            System.out.println("Turno: terminaFaseAttuale(). Chiusa la fase: " + faseDaChiudere.getClass().getSimpleName());
        }
    }

    public void timeout() {
        // TODO - implement Turno.timeout
        throw new UnsupportedOperationException();
    }

  
    public void resetTimer(Fase fase) {
        // TODO - implement Turno.resetTimer
        throw new UnsupportedOperationException();
    }


} 
*/