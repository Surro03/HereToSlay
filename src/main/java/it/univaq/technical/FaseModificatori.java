package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;
import it.univaq.entity.Tavolo;
import java.util.List;

public class FaseModificatori implements Fase {

    private int step = 0;
    private int punteggioAttuale;
    private final Player giocatoreCheHaTirato;

    private int indiceGiocatoreAttuale = 0;
    private int indiceSkip = 0;
    private Player giocatoreInterrogato;
    private CartaModificatore cartaModificatoreScelta;

    // Costruttore: riceve il punteggio di partenza e chi ha fatto il tiro
    public FaseModificatori(int punteggioDiPartenza, Player coluiCheHaTirato) {
        this.punteggioAttuale = punteggioDiPartenza;
        this.giocatoreCheHaTirato = coluiCheHaTirato;
    }

    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {
        int numeroGiocatori = turno.getListaGiocatori().size();

        if (this.step == 0) {
            // 1. Controllo di Fine Fase: Tutti hanno passato?
            if (this.indiceSkip >= numeroGiocatori) {
                turno.addMessage("Fase Modificatori terminata. Punteggio finale: " + this.punteggioAttuale);
                turno.salvaRisultatoSottoFase(new RisultatoFaseModificatoriNormale(this.punteggioAttuale));
                return true;
            }
            this.step = 1;

            // 2. Calcolo chi deve parlare
            int indiceSicuro = this.indiceGiocatoreAttuale % numeroGiocatori;
            this.giocatoreInterrogato = turno.getListaGiocatori().get(indiceSicuro);

            // 3. Skip automatico se non ha carte
            if (this.giocatoreInterrogato.getMano().getCarteMano().isEmpty()) {
                turno.addMessage(giocatoreInterrogato.getNome() + " ha 0 carte e passa in automatico.");
                this.indiceGiocatoreAttuale++;
                this.indiceSkip++;

                return this.eseguiFase(turno, tavolo);
            }
            // 4. Mando l'attesa alla UI
            List<Integer> indiciGiocabili = this.giocatoreInterrogato.getMano().getIndiciCarteDiTipo(CartaModificatore.class);
            ContestoAttesa contesto = new ContestoAttesaModificatoriNormale(this.punteggioAttuale, this.giocatoreCheHaTirato, this.giocatoreInterrogato, indiciGiocabili);
            turno.setAttesa(TipoAttesa.ATTESA_SCELTA_MODIFICATORI, contesto);
            return false;

        } else if (this.step == 1) {
            Object input = turno.popInput();
            if (input != null && !(input instanceof Integer)) {
                return false;
            }
            Integer indiceCartaScelta = (Integer) input;

            if (indiceCartaScelta == null) {
                this.indiceGiocatoreAttuale++;
                this.indiceSkip++;
                this.step = 0;
                return this.eseguiFase(turno, tavolo);

            } else {

                this.cartaModificatoreScelta = (CartaModificatore) this.giocatoreInterrogato.getMano().rimuoviCarta(indiceCartaScelta);

                ContestoAttesa contestoEffetto = new ContestoAttesaSceltaEffettoModificatoreNormale(this.punteggioAttuale, this.giocatoreCheHaTirato, this.cartaModificatoreScelta);
                turno.setAttesa(TipoAttesa.ATTESA_SCELTA_EFFETTO_MODIFICATORI, contestoEffetto);

                this.step = 2;
                return false;
            }

        } else if (this.step == 2) {
            Object input = turno.popInput();
            if (input != null && !(input instanceof GiocataSceltaModificatoreNormale)) {
                return false; // Torno a dormire
            }
            // 1. Prendo il Comando di tipo Singolo (Positivo o Negativo)
            GiocataSceltaModificatoreNormale giocata = (GiocataSceltaModificatoreNormale) input;

            // 2. Applico la matematica sul punteggio del tiratore
            assert giocata != null;
            int valoreDaApplicare = (giocata.tipoEffetto() == TipoEffetto.POSITIVO)
                    ? this.cartaModificatoreScelta.getValorePositivo()
                    : this.cartaModificatoreScelta.getValoreNegativo();

            this.punteggioAttuale += valoreDaApplicare;
            turno.addMessage("Modificatore applicato! Il nuovo tiro di " + this.giocatoreCheHaTirato.getNome() + " è: " + this.punteggioAttuale);
            // 3. Scarto la carta e chiudo il giro
            tavolo.aggiungiCartaPilaScarti(this.cartaModificatoreScelta);
            this.cartaModificatoreScelta = null;
            this.indiceGiocatoreAttuale++;
            this.indiceSkip = 0; // Qualcuno ha agito, il giro riparte da zero.
            this.step = 0;

            return this.eseguiFase(turno, tavolo);
        }

        throw new IllegalStateException("Errore Critico: Step sconosciuto (" + this.step + ") nella FaseModificatori");
    }
}