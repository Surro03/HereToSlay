package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Tavolo;

public class FaseEffetto implements Fase {

    private final CartaEroe carta;
    private int step = 0;

    // GRASP (Information Expert): Inietto la carta direttamente alla nascita
    public FaseEffetto(CartaEroe carta) {
        this.carta = carta;
    }

    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {
        
        // --- STEP 0: LANCIO I DADI E AVVIO MODIFICATORI ---
        if (this.step == 0) {
            System.out.println("LOG: FaseEffetto avviata per " + carta.getNome());
            
            // 1. Tiro i dadi
            int punteggioDadiBase = tavolo.lanciaDadi(2);
            turno.addMessage("\n--- RISULTATO TIRO---\n"+ "Il valore del tiro di " + turno.getGiocatoreDiTurno().getNome() + " è : " + punteggioDadiBase + " |\n");
            
            // 2. Chiamo la fase dei Modificatori
            turno.aggiungiFaseInCima(new FaseModificatori(punteggioDadiBase, turno.getGiocatoreDiTurno()));
            
            this.step = 1;
            return false; // Mi metto in pausa
        }
        
        // --- STEP 1: ESITO FASE MODIFICATORI ---
        else if (this.step == 1) {
            RisultatoFaseModificatoriNormale punteggioFinale = (RisultatoFaseModificatoriNormale) turno.popRisultatoSottoFase();
            turno.addMessage("Il punteggio finale, calcolati i modificatori, è: " + punteggioFinale.punteggioFinale());
            if (carta.checkAttivazioneEffetto(punteggioFinale.punteggioFinale())) {
                turno.addMessage("Successo! Effetto attivato: " + carta.getEffetto());
                
                // [!] QUI ANDRÀ LA LOGICA REALE DELL'EFFETTO [!]
                
            } else {
                turno.addMessage("Fallito! Il punteggio " + punteggioFinale + " non basta.");
            }
            return true; // La Fase Effetto è conclusa!
        }
        
        return true;
    }
}