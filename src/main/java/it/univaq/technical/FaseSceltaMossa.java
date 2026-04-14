package it.univaq.technical;

import it.univaq.entity.Tavolo;

public class FaseSceltaMossa implements Fase {
    
    private int step = 0;

    // NESSUNA GUI NELLA FIRMA DEL METODO!
    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {

        if (this.step == 0) {
            System.out.println("LOG: FaseSceltaMossa iniziata. Calcolo i PA disponibili...");

            int pa = turno.getPaRimasti();

            // SE NON HO PIU' PA, LA FASE FINISCE.
            if (pa == 0) {
                // Fine turno!
                return true;
            }

            // La fase NON stampa nulla. Dice semplicemente al Turno di cosa ha bisogno.
            this.step = 1;

            turno.setAttesa(TipoAttesa.SCELTA_MOSSA_PRINCIPALE);
            return false; // Restituisce false per fermare il while del Turno.
        }

        else if (this.step == 1) {
            System.out.println("LOG: FaseSceltaMossa risvegliata! Leggo l'input...");

            // Il controller ci ha appena iniettato l'input
            Integer mossaScelta = (Integer) turno.popInput();

            if (mossaScelta != null) {
                switch (mossaScelta) {
                    case 1:
                        turno.aggiungiFaseInCima(new FaseGiocaCartaEroe());
                        // Quando FaseGiocaCartaEroe finirà,
                        // il Turno tornerà qui e ripartirà dallo step 0 per un'altra mossa!
                        this.step = 0;
                        return false;

                    case 99: // Bottone "Passo il turno"
                        return true;   // Mi autodistruggo e chiudo il turno.

                    default:
                        // Se per qualche motivo arriva un numero errato, resetto e richiedo.
                        this.step = 0;
                        return false;
                }
            }
            return false;
        }
        return true;
    }
}