package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Dado;

public class FaseEffetto implements Fase {

    private final CartaEroe carta;
    private int step = 0;
    private final Dado dado = new Dado(6);

    // GRASP (Information Expert): Inietto la carta direttamente alla nascita
    public FaseEffetto(CartaEroe carta) {
        this.carta = carta;
    }

    @Override
    public boolean eseguiFase(Turno turno) {
        
        // --- STEP 0: LANCIO I DADI E AVVIO MODIFICATORI ---
        if (this.step == 0) {
            System.out.println("LOG: FaseEffetto avviata per " + carta.getNome());
            
            // 1. Tiro i dadi
            int punteggioDadiBase = lanciaDadi(2);
            turno.addMessage("Hai tirato i dadi! Risultato base: " + punteggioDadiBase);
            
            // 2. Chiamo la fase dei Modificatori
            turno.aggiungiFaseInCima(new FaseModificatori(punteggioDadiBase));
            
            this.step = 1;
            return false; // Mi metto in pausa
        }
        
        // --- STEP 1: VERIFICA ED ESECUZIONE (Dopo i modificatori) ---
        else if (this.step == 1) {
            

            Float punteggioFinale = (Float) turno.popRisultatoSottoFase();
            
            turno.addMessage("Il punteggio finale, calcolati i modificatori, è: " + punteggioFinale);
            
            // 4. Controllo se l'effetto si attiva (passando il Float)
            if (carta.checkAttivazioneEffetto(punteggioFinale)) {
                
                turno.addMessage("Successo! Effetto attivato: " + carta.getEffetto());
                
                // [!] QUI ANDRÀ LA LOGICA REALE DELL'EFFETTO [!]
                
            } else {
                turno.addMessage("Fallito! Il punteggio " + punteggioFinale + " non basta.");
            }
            
            return true; // La Fase Effetto è conclusa!
        }
        
        return true;
    }

    // Metodo di servizio per simulare i dadi
    private int lanciaDadi(int numDadi) {
        int risultato = 0;
        for (int i = 0; i <= numDadi; i++) {
            risultato = risultato + dado.tiraDado();
        }
        return risultato;
    }
}