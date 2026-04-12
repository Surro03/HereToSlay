package it.univaq.technical;

import it.univaq.ui.InterfacciaUtente;

public class FaseSceltaMossa implements Fase {
    
    private int step = 0; 

    @Override
    public boolean eseguiFase(Turno turno, InterfacciaUtente gui) {
        
        if (step == 0) {
            System.out.println("LOG: FaseSceltaMossa iniziata. Calcolo i PA disponibili...");
            
            int pa = turno.getPaRimasti();
            
            // SE NON HO PIU' PA, IL TURNO DEVE FINIRE FORZATAMENTE!
            if (pa == 0) {
                gui.mostraMessaggio("Non hai più Punti Azione. Fine del turno!");
                return true; // Autodistruzione: la pila si svuota e il turno finisce.
            }
            
            boolean puoGiocareEroe = pa >= 1;
            boolean puoAttaccare = pa >= 2;
            boolean puoPescare = pa >= 3;

            gui.richiediSelezioneMossa(puoGiocareEroe, puoAttaccare, puoPescare);

            step = 1;
            return false; 
        }
        
        else if (step == 1) {
            System.out.println("LOG: FaseSceltaMossa risvegliata! Leggo l'input...");
            
            Integer mossaScelta = (Integer) turno.popInput();
            
            if (mossaScelta != null) {
                switch (mossaScelta) {
                    case 1:
                        turno.aggiungiFaseInCima(new FaseGiocaCartaEroe());
                        
                        // ---> LA MAGIA DEL LOOP <---
                        this.step = 0; // Mi resetto, pronto per la prossima mossa!
                        return false;  // Ritorno false per rimanere vivo sotto la FaseGiocaEroe!
                        
                    case 99: // Ipotetico bottone "Passo il turno"
                        return true;   // Mi autodistruggo e chiudo il turno in anticipo.
                        
                    default:
                        gui.mostraMessaggio("Scelta non valida.");
                        this.step = 0; // Resetto e richiedo
                        return false;
                }
            }
            return false; 
        }
        return true; 
    }
}