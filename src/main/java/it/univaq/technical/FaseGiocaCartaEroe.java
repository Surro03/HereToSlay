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
            ContestoAttesa contestoAttesaSceltaCartaEroe = new ContestoAttesaSceltaCartaEroe(turno.getGiocatoreDiTurno().getMano());
            turno.setAttesa(SCELTA_CARTA_EROE, contestoAttesaSceltaCartaEroe);
            return false;
        } 
        
        // --- STEP 1: CONSUMO PA ---
        else if (this.step == 1) {

            //Rimuove la carta dalla mano del giocatore e la salva nella fase
            this.cartaScelta = (CartaEroe) turno.getGiocatoreDiTurno().getMano().rimuoviCarta((int) turno.popInput());

            //if (this.cartaScelta == null) return true; // L'utente ha annullato


            turno.aggiungiFaseInCima(new FaseSfida(this.cartaScelta));
            this.step = 2;
            return false;
        }

        // --- STEP 2: CONTROLLO ESITO SFIDA ---
        else if (this.step == 2) {
            Object input = turno.popRisultatoSottoFase();
            if (input != null && !(input instanceof Boolean)) {
                return false;
            }
            Boolean sopravvissuta = (Boolean) input;
            if (Boolean.FALSE.equals(sopravvissuta)) {
                // La carta è stata distrutta dalla sfida. Il turno finisce qui.
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
            ContestoAttesa contestoAttesaConfermaEffetto = new ContestoAttesaConfermaEffetto();
            turno.setAttesa(CONFERMA_EFFETTO, contestoAttesaConfermaEffetto);
            return false;
        }

        
        // --- STEP 3: RICHIESTA EFFETTO ---
        else if (this.step == 3) {
            Object input = turno.popInput();
            if (input != null && !(input instanceof Boolean)) {
                return false;
            }
            Boolean vuoleAttivare = (Boolean) input;
            
            if (Boolean.TRUE.equals(vuoleAttivare)) {
                turno.aggiungiFaseInCima(new FaseEffetto(this.cartaScelta)); 
                
                this.step = 4;
                return false; 
            }

            this.step = 0;
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