package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Tavolo;

import static it.univaq.technical.TipoAttesa.*;

public class FaseGiocaCartaEroe implements Fase {
    
    private int step = 0;
    private CartaEroe cartaScelta;

    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {
        
        // --- STEP 0: SELEZIONE DELLA CARTA ---
        if (this.step == 0) {
            this.step = 1;
            turno.setAttesa(SCELTA_CARTA_EROE);
            return false;
        } 
        
        // --- STEP 1: CONSUMO PA ---
        else if (this.step == 1) {

            //Rimuove la carta dalla mano del giocatore e la salva nella fase
            this.cartaScelta = (CartaEroe) turno.getGiocatoreDiTurno().getMano().ottieniCarta((int) turno.popInput());

            if (this.cartaScelta == null) return true; // L'utente ha annullato

            // Consumo il punto azione
            turno.consumaPA(1);
            // ---> INIETTO LA SFIDA NELLA PILA! <---
            turno.aggiungiFaseInCima(new FaseSfida(this.cartaScelta, turno.getGiocatoreDiTurno()));
            this.step = 2;
            turno.setAttesa(RICHIESTA_TAVOLO);
            return false;
        }

        // --- STEP 2: CONTROLLO ESITO SFIDA ---
        else if (this.step == 2) {
            Boolean sopravvissuta = (Boolean) turno.popRisultatoSottoFase();
            if (!sopravvissuta) {
                // La carta è stata distrutta dalla sfida. Il turno finisce qui, niente effetti!
                tavolo.aggiungiCartaPilaScarti(cartaScelta);
                cartaScelta = null;
                return true;
            }

            // Se è sopravvissuta, la aggiungo al Party e vado avanti con la richiesta dell'effetto
            // Mettiamo la carta sul tavolo
            tavolo.aggiungiCartaParty(cartaScelta, turno.getGiocatoreDiTurno().getId());
            turno.addMessage(("Hai giocato: " + cartaScelta.getNome()));
            turno.addMessage("Vuoi lanciare i dadi per l'effetto di " + cartaScelta.getNome() + "?");
            this.step = 3;
            turno.setAttesa(CONFERMA_EFFETTO);
            return false;
        }

        
        // --- STEP 3: RICHIESTA EFFETTO ---
        else if (this.step == 3) {
            Boolean vuoleAttivare = (Boolean) turno.popInput();
            
            if (vuoleAttivare) {
                turno.aggiungiFaseInCima(new FaseEffetto(this.cartaScelta)); 
                
                this.step = 4;
                return false; 
            }
            
            return true; // Se non vuole attivare, la giocata dell'eroe finisce qui.
        }
        
        // --- STEP 4: CONCLUSIONE ---
        else if (this.step == 4) {
            // Qui arriviamo solo DOPO che FaseEffetto (e tutte le sue sotto-fasi) hanno finito.
            //gui.mostraMessaggio("Risoluzione della carta Eroe completata.");
            return true; 
        }

        return true;
    }
}