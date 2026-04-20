package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;
import it.univaq.entity.Tavolo;

import java.util.List;

public class FaseModificatoriSfida implements Fase {

    private int step = 0;
    private int punteggioGiocatoreDiTurno;
    private int punteggioSfidante;
    private final Player giocatoreAttivo;
    private final Player sfidante;
    private int indiceGiocatoreAttuale = 0;
    private int indiceSkip = 0;
    private Player giocatoreInterrogato;
    private CartaModificatore cartaModificatoreScelta;

    public FaseModificatoriSfida(int ptAttivo, int ptSfidante, Player att, Player sfid) {
        this.punteggioGiocatoreDiTurno = ptAttivo;
        this.punteggioSfidante = ptSfidante;
        this.giocatoreAttivo = att;
        this.sfidante = sfid;
    }

    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {

        if (this.step == 0) {
            if (this.indiceSkip >= turno.getListaGiocatori().size()) {
                turno.addMessage("Fase Terminata");
                RisultatoFase risultatoFaseModificatoriSfida = new RisultatoFaseModificatoriSfida(this.punteggioGiocatoreDiTurno, this.punteggioSfidante);
                turno.salvaRisultatoSottoFase(risultatoFaseModificatoriSfida);
                return true; // La fase Sfida finisce!
            }
            this.step = 1;
            // Se indiceGiocatoreAttuale arriva a 3 (e i giocatori sono 3), 3 % 3 = 0. Riparte da solo!
            int indiceSicuro = this.indiceGiocatoreAttuale % turno.getListaGiocatori().size();
            this.giocatoreInterrogato = turno.getListaGiocatori().get(indiceSicuro);
            if (giocatoreInterrogato.getMano().getCarteMano().isEmpty()) {

                // Log per avvisare gli altri giocatori del salto automatico
                //turno.inviaEvento(new NotificaMessaggio(giocatoreInterrogato.getNome() + " ha 0 carte in mano e passa in automatico."));
                turno.addMessage(giocatoreInterrogato.getNome() + " ha 0 carte in mano e passa in automatico.");

                // Incremento l'indice e riavvio il loop istantaneamente!
                this.indiceGiocatoreAttuale++;
                this.indiceSkip++;
                return this.eseguiFase(turno, tavolo);
            }
            List<Integer> indiceCarteGiocabili = this.giocatoreInterrogato.getMano().getIndiciCarteDiTipo(CartaModificatore.class);
            PayloadAttesa contestoAttesaModificatoriSfida = new ContestoAttesaModificatoriSfida(this.punteggioGiocatoreDiTurno, this.punteggioSfidante, indiceCarteGiocabili, this.giocatoreAttivo, this.sfidante, giocatoreInterrogato);
            turno.setAttesa(TipoAttesa.ATTESA_SCELTA_MODIFICATORI, contestoAttesaModificatoriSfida);
            return false;

        } else if (this.step == 1) {
            //Rimuove la carta dalla mano del giocatore e la salva nella fase
            Integer indiceCartaScelta = (Integer) turno.popInput();
            if (indiceCartaScelta == null) {
                this.indiceGiocatoreAttuale++; // Passo al prossimo
                this.indiceSkip++;             // Registro lo skip!
                this.step = 0;
                return this.eseguiFase(turno, tavolo);
            } else {
                this.cartaModificatoreScelta = (CartaModificatore) this.giocatoreInterrogato.getMano().rimuoviCarta(indiceCartaScelta);
                PayloadAttesa contestoSceltaEffettoModificatore = new ContestoAttesaSceltaEffettoModificatore(this.punteggioGiocatoreDiTurno, this.punteggioSfidante, this.giocatoreAttivo, this.sfidante, this.cartaModificatoreScelta);
                turno.setAttesa(TipoAttesa.ATTESA_SCELTA_EFFETTO_MODIFICATORI, contestoSceltaEffettoModificatore);
                this.step = 2;
                return false;
            }

        } else if (this.step == 2) {
            // 1. Prendo il Payload
            GiocataSceltaModificatore giocata = (GiocataSceltaModificatore) turno.popInput();

            // 2. Capisco QUALE valore devo usare dalla carta in sospeso
            int valoreDaApplicare = (giocata.tipoEffetto() == TipoEffetto.POSITIVO)
                    ? this.cartaModificatoreScelta.getValorePositivo()
                    : this.cartaModificatoreScelta.getValoreNegativo();

            // 3. Lo applico al BERSAGLIO corretto
            if (giocata.bersaglio() == BersaglioModificatore.GIOCATORE_DI_TURNO) {
                this.punteggioGiocatoreDiTurno += valoreDaApplicare;
            } else {
                this.punteggioSfidante += valoreDaApplicare;
            }

            // Log per avvisare tutti!
            turno.addMessage("I nuovi punteggi sono -> " + turno.getGiocatoreDiTurno().getNome() + ": " + this.punteggioGiocatoreDiTurno + " | " + sfidante.getNome() + ": " + this.punteggioSfidante);

            // Azzero la carta e riparto
            tavolo.aggiungiCartaPilaScarti(cartaModificatoreScelta);
            this.cartaModificatoreScelta = null;
            this.indiceGiocatoreAttuale++; // Passo la palla al prossimo giocatore per farlo rispondere!
            this.indiceSkip = 0;           // Qualcuno ha agito! Il contatore dei "passo" si azzera!
            this.step = 0;
            return this.eseguiFase(turno, tavolo);
        }
        throw new IllegalStateException("Errore Critico: Step sconosciuto (" + this.step + ") nella FaseModificatoriSfida");
    }
}