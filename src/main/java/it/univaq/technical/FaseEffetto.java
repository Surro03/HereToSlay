package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.ui.InterfacciaUtente;

public class FaseEffetto implements Fase {

    private CartaEroe carta;
    private int step = 0;
    private int punteggioDadiBase;

    // GRASP (Information Expert): Inietto la carta direttamente alla nascita
    public FaseEffetto(CartaEroe carta) {
        this.carta = carta;
    }

    @Override
    public boolean eseguiFase(Turno turno, InterfacciaUtente gui) {
        
        // --- STEP 0: LANCIO I DADI E AVVIO MODIFICATORI ---
        if (this.step == 0) {
            System.out.println("LOG: FaseEffetto avviata per " + carta.getNome());
            
            // 1. Tiro i dadi
            this.punteggioDadiBase = lanciaDueDadi(); 
            gui.mostraMessaggio("Hai tirato i dadi! Risultato base: " + this.punteggioDadiBase);
            
            // 2. Chiamo la fase dei Modificatori
            turno.aggiungiFaseInCima(new FaseModificatori(this.punteggioDadiBase));
            
            this.step = 1;
            return false; // Mi metto in pausa
        }
        
        // --- STEP 1: VERIFICA ED ESECUZIONE (Dopo i modificatori) ---
        else if (this.step == 1) {
            
            // ---> CORREZIONE QUI: Recupero un Float invece di un Integer! <---
            Float punteggioFinale = (Float) turno.popRisultatoSottoFase();
            
            gui.mostraMessaggio("Il punteggio finale, calcolati i modificatori, è: " + punteggioFinale);
            
            // 4. Controllo se l'effetto si attiva (passando il Float)
            if (carta.checkAttivazioneEffetto(punteggioFinale)) {
                
                gui.mostraMessaggio("Successo! Effetto attivato: " + carta.getEffetto());
                
                // [!] QUI ANDRÀ LA LOGICA REALE DELL'EFFETTO [!]
                
            } else {
                gui.mostraMessaggio("Fallito! Il punteggio " + punteggioFinale + " non basta.");
            }
            
            return true; // La Fase Effetto è conclusa!
        }
        
        return true;
    }

    // Metodo di servizio per simulare i dadi
    private int lanciaDueDadi() {
        int dado1 = (int)(Math.random() * 6) + 1;
        int dado2 = (int)(Math.random() * 6) + 1;
        return dado1 + dado2;
    }
}