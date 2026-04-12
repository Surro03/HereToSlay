package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.ui.InterfacciaUtente;

public class FaseGiocaCartaEroe implements Fase {
    
    private int step = 0;
    private CartaEroe cartaScelta;

    @Override
    public boolean eseguiFase(Turno turno, InterfacciaUtente gui) {
        
        // --- STEP 0: SELEZIONE DELLA CARTA ---
        if (this.step == 0) {
            gui.richiediSceltaCarta(turno.getGiocatoreDiTurno().getEroiInMano());
            
            this.step = 1;
            return false;
        } 
        
        // --- STEP 1: GIOCATA E CONSUMO PA ---
        else if (this.step == 1) {
            this.cartaScelta = (CartaEroe) turno.popInput();
            
            if (this.cartaScelta == null) return true; // L'utente ha annullato

            // Consumo il punto azione
            turno.consumaPA(1);
            
            // Qui ci andrà la logica per togliere la carta dalla mano e metterla sul tavolo
            // turno.getGiocatoreDiTurno().getMano().rimuoviCarta(cartaScelta);
            // tavolo.aggiungiEroe(turno.getGiocatoreDiTurno(), cartaScelta);
            
            gui.mostraMessaggio("Hai giocato: " + cartaScelta.getNome());
            
            gui.richiediConfermaEffetto("Vuoi lanciare i dadi per l'effetto di " + cartaScelta.getNome() + "?");
            
            this.step = 2;
            return false;
        }
        
        // --- STEP 2: DELEGA DELL'ATTIVAZIONE ---
        else if (this.step == 2) {
            Boolean vuoleAttivare = (Boolean) turno.popInput();
            
            if (vuoleAttivare) {
                // ---> CORREZIONE QUI! <---
                // Metto in cima la FaseEffetto e le do in pasto la carta giocata!
                // Sarà lei a gestire dadi, modificatori ed esecuzione.
                turno.aggiungiFaseInCima(new FaseEffetto(this.cartaScelta)); 
                
                this.step = 3;
                return false; 
            }
            
            return true; // Se non vuole attivare, la giocata dell'eroe finisce qui.
        }
        
        // --- STEP 3: CONCLUSIONE ---
        else if (this.step == 3) {
            // Qui arriviamo solo DOPO che FaseEffetto (e tutte le sue sotto-fasi) hanno finito.
            gui.mostraMessaggio("Risoluzione della carta Eroe completata.");
            return true; 
        }

        return true;
    }
}