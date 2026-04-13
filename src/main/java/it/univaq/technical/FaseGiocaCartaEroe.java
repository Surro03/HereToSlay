package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Tavolo;

import static it.univaq.technical.TipoAttesa.*;

public class FaseGiocaCartaEroe implements Fase {
    
    private int step = 0;
    private CartaEroe cartaScelta;

    @Override
    public boolean eseguiFase(Turno turno) {
        
        // --- STEP 0: SELEZIONE DELLA CARTA ---
        if (this.step == 0) {
//            gui.richiediSceltaCarta(turno.getGiocatoreDiTurno().getEroiInMano());
            turno.setAttesa(SCELTA_CARTA_EROE);
            
            this.step = 1;
            return false;
        } 
        
        // --- STEP 1: CONSUMO PA ---
        else if (this.step == 1) {

            //Rimuove la carta dalla mano del giocatore e la salva nella fase
            this.cartaScelta = (CartaEroe) turno.getGiocatoreDiTurno().getMano().ottieniCarta((int) turno.popInput());

            if (this.cartaScelta == null) return true; // L'utente ha annullato

            // Consumo il punto azione
            turno.consumaPA(1);
            this.step = 2;
            turno.setAttesa(RICHIESTA_TAVOLO);
            return false;
        }
        //Ottenimento tavolo dall'Information Expert (il controllore)
        else if (this.step == 2) {

            // Mettiamo la carta sul tavolo
            Tavolo tavolo = (Tavolo) turno.popInput();
            tavolo.aggiungiCartaParty(cartaScelta, turno.getGiocatoreDiTurno().getId());
            
//            gui.mostraMessaggio("Hai giocato: " + cartaScelta.getNome());
//
//            gui.richiediConfermaEffetto("Vuoi lanciare i dadi per l'effetto di " + cartaScelta.getNome() + "?");
            turno.setAttesa(CONFERMA_EFFETTO);
            this.step = 3;
            return false;
        }
        
        // --- STEP 2: DELEGA DELL'ATTIVAZIONE ---
        else if (this.step == 3) {
            Boolean vuoleAttivare = (Boolean) turno.popInput();
            
            if (vuoleAttivare) {
                // ---> CORREZIONE QUI! <---
                // Metto in cima la FaseEffetto e le do in pasto la carta giocata!
                // Sarà lei a gestire dadi, modificatori ed esecuzione.
                turno.aggiungiFaseInCima(new FaseEffetto(this.cartaScelta)); 
                
                this.step = 4;
                return false; 
            }
            
            return true; // Se non vuole attivare, la giocata dell'eroe finisce qui.
        }
        
        // --- STEP 3: CONCLUSIONE ---
        else if (this.step == 4) {
            // Qui arriviamo solo DOPO che FaseEffetto (e tutte le sue sotto-fasi) hanno finito.
            gui.mostraMessaggio("Risoluzione della carta Eroe completata.");
            return true; 
        }

        return true;
    }
}