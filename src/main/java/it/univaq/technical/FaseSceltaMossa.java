package it.univaq.technical;

import it.univaq.entity.CartaEroe;
import it.univaq.entity.Player;
import it.univaq.entity.Tavolo;

public class FaseSceltaMossa implements Fase {
    
    private int step = 0;

    // NESSUNA GUI NELLA FIRMA DEL METODO!
    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {

        if (this.step == 0) {

            int pa = turno.getPaRimasti();

            // SE NON HO PIU' PA, LA FASE FINISCE.
            if (pa == 0) {
                // Fine turno!
                return true;
            }

            // La fase NON stampa nulla. Dice semplicemente al Turno di cosa ha bisogno.
            this.step = 1;
            Player attivo = turno.getGiocatoreDiTurno();
            boolean haEroi = attivo.verificaTipoDiCarteInMano(CartaEroe.class);

            ContestoAttesa payload = new ContestoAttesaMossaPrincipale(attivo, haEroi, pa);

            turno.setAttesa(TipoAttesa.SCELTA_MOSSA_PRINCIPALE, payload);
            return false; // Restituisce false per fermare il while del Turno.
        }

        else if (this.step == 1) {
            Object input = turno.popInput();

            if (input != null && !(input instanceof SceltaMossa)) {
                return false;
            }

            //Ora il cast è blindato
            SceltaMossa mossaScelta = (SceltaMossa) input;

            if (mossaScelta != null) {
                Fase faseSuccessiva = mossaScelta.eseguiMossa(turno);
                this.step = 0;
                if (faseSuccessiva == null) {
                    //Richiedo la mossa perché i PA non bastavano
                    turno.addMessage("PA insufficienti, scegliere un'altra mossa.");
                    return this.eseguiFase(turno, tavolo);
                } else {
                    turno.aggiungiFaseInCima(faseSuccessiva);
                    //Mi resetto allo step 0, così quando mi sveglierò ricalcolerò i PA
                    return false;
                }
            }

            // Se l'input era nullo o errato, richiedo semplicemente
            this.step = 0;
            return this.eseguiFase(turno, tavolo);
        }
        return true;
    }
}