package it.univaq.technical;


import it.univaq.entity.CartaModificatore;

public class FaseModificatori implements Fase {
    
    private int step = 0;
    
    // CORREZIONE 1: Uso float al posto di int!
    private float punteggioAttuale; 

    // Riceve il punteggio dei dadi lanciati dalla FaseEffetto
    public FaseModificatori(float punteggioDiPartenza) {
        this.punteggioAttuale = punteggioDiPartenza;
    }

    @Override
    public boolean eseguiFase(Turno turno) {
        
        // --- STEP 0: INIZIO TIMER E RICHIESTA ---
        if (this.step == 0) {
            System.out.println("LOG: FaseModificatori iniziata. Avvio timer sfide...");
            
            //gui.richiediGiocataModificatori(punteggioAttuale);
            
            this.step = 1;
            return false; // Mi addormento
        }
        
        // --- STEP 1: RICEZIONE EVENTI (Carte o Timeout) ---
        else if (this.step == 1) {
            
            Object inputRicevuto = turno.popInput();
            
            // CASO A: Il timer è scaduto!
            if (inputRicevuto instanceof String && inputRicevuto.equals("TIMEOUT")) {
                System.out.println("LOG: Nessuno ha giocato modificatori. Tempo scaduto!");
                //gui.mostraMessaggio("Fase modificatori conclusa. Punteggio finale: " + punteggioAttuale);
                
                // ---> CORREZIONE 2 FONDAMENTALE <---
                // Appoggio il punteggio aggiornato nel turno, così la FaseEffetto lo trova!
                turno.salvaRisultatoSottoFase(this.punteggioAttuale);
                
                return true; // La fase è FINITA DEFINITIVAMENTE. Tolgo dalla pila.
            }
            
            // CASO B: Qualcuno ha giocato una carta Modificatore!
            else if (inputRicevuto instanceof GiocataModificatore) {
                
                GiocataModificatore giocata = (GiocataModificatore) inputRicevuto;
                CartaModificatore mod = giocata.carta();
                
                float valoreDaAggiungere = mod.getValoreScelto(giocata.usaPositivo());
                
                this.punteggioAttuale += valoreDaAggiungere; 
                
                System.out.println("LOG: Giocato modificatore! Nuovo punteggio: " + this.punteggioAttuale);
                
                //gui.aggiornaSchermataModificatori(this.punteggioAttuale);
                
                return false; 
            }
        }
        
        return true; // Sicurezza
    }
}