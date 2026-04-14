package it.univaq.technical;

import it.univaq.entity.Carta;
import it.univaq.entity.Player;
import it.univaq.entity.Tavolo;

public class FaseSfida implements Fase {

    private int step = 0;
    private Carta cartaGiocata;
    private Player giocatoreAttivo;

    private Player sfidante;
    private float valorePlayer;
    private float valoreSfidante;

    public FaseSfida(Carta cartaGiocata, Player giocatoreAttivo) {
        this.cartaGiocata = cartaGiocata;
        this.giocatoreAttivo = giocatoreAttivo;
    }

    @Override
    public boolean eseguiFase(Turno turno, Tavolo tavolo) {

        // STEP 0: Chiedo se qualcuno vuole lanciare una sfida
        if (this.step == 0) {
            this.step = 1;
            gui.richiediGiocataSfida(giocatoreAttivo, cartaGiocata);
            return false;
        }

        // STEP 1: Ricevo la sfida (o il timeout se nessuno fa nulla)
        else if (this.step == 1) {
            Object input = turno.popInput();

            if (input instanceof String && input.equals("TIMEOUT")) {
                gui.mostraMessaggio("Nessuno ha sfidato la giocata.");
                turno.salvaRisultatoSottoFase(true); // Sopravvissuta
                return true;
            }
            else if (input instanceof GiocataSfida giocata) {
                this.sfidante = giocata.sfidante();
                this.sfidante.getMano().rimuoviCarta(giocata.carta()); // Rimuovo la Sfida dalla mano!

                gui.mostraMessaggio("⚔️ " + sfidante.getNome() + " HA LANCIATO UNA SFIDA!");

                // Tiriamo i dadi
                this.valorePlayer = lanciaDueDadi();
                this.valoreSfidante = lanciaDueDadi();

                gui.mostraMessaggio("Tiro Base | " + giocatoreAttivo.getNome() + ": " + valorePlayer + " | " + sfidante.getNome() + ": " + valoreSfidante);

                // Lancio la fase modificatori DOPPIA!
                turno.aggiungiFaseInCima(new FaseModificatoriSfida(valorePlayer, valoreSfidante, giocatoreAttivo, sfidante));
                this.step = 2;
                return false;
            }
        }

        // STEP 2: Calcolo il vincitore dopo i modificatori
        else if (this.step == 2) {
            // Recupero un array con i due punteggi finali
            float[] punteggiFinali = (float[]) turno.popRisultatoSottoFase();
            float finalPlayer = punteggiFinali[0];
            float finalSfidante = punteggiFinali[1];

            gui.mostraMessaggio("Risultato Finale | " + giocatoreAttivo.getNome() + ": " + finalPlayer + " | " + sfidante.getNome() + ": " + finalSfidante);

            // In caso di parità, vince lo sfidante! (Regola di HTS)
            if (finalPlayer > finalSfidante) {
                gui.mostraMessaggio("✅ " + giocatoreAttivo.getNome() + " vince la sfida! La carta entra in gioco.");
                turno.salvaRisultatoSottoFase(true); // Sopravvissuta
            } else {
                gui.mostraMessaggio("❌ " + sfidante.getNome() + " vince la sfida! La carta " + cartaGiocata.getNome() + " viene distrutta.");
                turno.salvaRisultatoSottoFase(false); // Distrutta
            }
            return true;
        }

        return true;
    }

    private int lanciaDueDadi() {
        return (int)(Math.random() * 6) + 1 + (int)(Math.random() * 6) + 1;
    }
}