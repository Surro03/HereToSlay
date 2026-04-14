package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;

public class FaseModificatoriSfida implements Fase {

    private int step = 0;
    private float punteggioAttivo;
    private float punteggioSfidante;
    private Player giocatoreAttivo;
    private Player sfidante;

    public FaseModificatoriSfida(float ptAttivo, float ptSfidante, Player att, Player sfid) {
        this.punteggioAttivo = ptAttivo;
        this.punteggioSfidante = ptSfidante;
        this.giocatoreAttivo = att;
        this.sfidante = sfid;
    }

    @Override
    public boolean eseguiFase(Turno turno) {

        if (this.step == 0) {
            this.step = 1;
            gui.richiediGiocataModificatoriSfida(punteggioAttivo, punteggioSfidante, giocatoreAttivo, sfidante);
            return false;
        }

        else if (this.step == 1) {
            Object inputRicevuto = turno.popInput();

            if (inputRicevuto instanceof String && inputRicevuto.equals("TIMEOUT")) {
                // Impacchetto i due risultati finali e li restituisco alla FaseSfida
                turno.salvaRisultatoSottoFase(new float[]{this.punteggioAttivo, this.punteggioSfidante});
                return true;
            }

            else if (inputRicevuto instanceof GiocataModificatoreSfida giocata) {
                float valore = giocata.carta().getValoreScelto(giocata.usaPositivo());

                // Applica il modificatore a chi è stato scelto come bersaglio!
                if (giocata.bersaglio().equals(giocatoreAttivo)) {
                    this.punteggioAttivo += valore;
                } else {
                    this.punteggioSfidante += valore;
                }

                gui.aggiornaSchermataModificatoriSfida(this.punteggioAttivo, this.punteggioSfidante);
                return false;
            }
        }
        return true;
    }
}