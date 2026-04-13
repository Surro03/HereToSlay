package it.univaq.controller;

import it.univaq.entity.*;
import it.univaq.technical.*;
import it.univaq.ui.GameObserver;

import java.util.List;

public class HereToSlay implements ControllerSubject {

    private List<GameObserver> observers;
    private final List<Player> elencoGiocatori;
    private Player giocatoreAttivo;
    private final Tavolo tavolo;
    
    // Il nostro nuovo e fiammante motore a stati
    private Turno turnoAttuale; 

    public HereToSlay(List<Player> elencoGiocatori) {
        this.elencoGiocatori = elencoGiocatori;
        this.tavolo = new Tavolo(elencoGiocatori);
        // Niente più inizializzazione di PilaFasi qui dentro!
    }

    @Override
    public void addObserver(GameObserver gameObserver) {
        this.observers.add(gameObserver);
    }
    @Override
    public void removeObserver(GameObserver gameObserver) {
        this.observers.remove(gameObserver);
    }

    @Override
    public void iniziaPartita() {
        this.giocatoreAttivo = elencoGiocatori.getFirst();
        this.iniziaTurno(this.giocatoreAttivo);
    }

    // --- LA PRIMA SPINTA AL DOMINO ---
    private void iniziaTurno(Player giocatore) {
        gui.mostraMessaggio("\n--- INIZIA IL TURNO DI: " + giocatore.getNome() + " ---");
        
        // 1. Creo il motore per questo turno
        this.turnoAttuale = new Turno(giocatore);
        
        // 2. Inietto la fase iniziale
        this.turnoAttuale.aggiungiFaseInCima(new FaseSceltaMossa());
        
        // 3. Spingo il primo domino! (Passo la gui e me stesso come controller)
        this.turnoAttuale.avanzaMotoreFasi(this.gui, this);
    }

    // --- IL MACRO-CICLO: Ritorno della spinta ---
    // Questo metodo viene chiamato dal Turno quando la sua pila si svuota
    public void prossimoTurno() {
        
        // 1. Controlla se qualcuno ha vinto (Information Expert: lo sa il Tavolo)
        if (tavolo.checkVittoria(giocatoreAttivo.getId()).vittoria()) {
            gui.mostraMessaggio("VITTORIA! " + giocatoreAttivo.getNome() + " ha vinto la partita!");
            return; // IL GIOCO SI FERMA QUI. Niente più turni.
        }

        // 2. Calcola il prossimo giocatore (logica semplificata)
        int indiceAttuale = elencoGiocatori.indexOf(giocatoreAttivo);
        int prossimoIndice = (indiceAttuale + 1) % elencoGiocatori.size();
        this.giocatoreAttivo = elencoGiocatori.get(prossimoIndice);

        // 3. Nuova spinta per il nuovo giocatore!
        this.iniziaTurno(this.giocatoreAttivo);
    }

    // --- IL CENTRALINO: Comunicazione con la UI ---
    // Qualsiasi click faccia l'utente sulla UI, i ragazzi della grafica 
    // devono chiamare questo metodo passandoti il dato.
    public void riceviInputDaUI(Object datoInput) {
        
        System.out.println("LOG: HereToSlay riceve input dalla UI e lo passa al Turno.");
        
        // Il Controller non controlla l'input, fa solo da postino per il Turno!
        if (this.turnoAttuale != null) {
            this.turnoAttuale.riceviInput(datoInput, this.gui, this);
        }
    }

    // (Getter necessari)
    public Turno getTurnoAttuale() { return turnoAttuale; }
    public Player getGiocatoreAttivo() { return giocatoreAttivo; }
}