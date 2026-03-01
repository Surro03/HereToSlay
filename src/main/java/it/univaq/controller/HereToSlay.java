package it.univaq.controller;
import it.univaq.entity.*;
import it.univaq.technical.Fase;
import it.univaq.technical.*;
import it.univaq.technical.FaseModificatori;
import it.univaq.entity.*;
import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;
import it.univaq.technical.FaseModificatori;
import it.univaq.ui.FinestraTemporale;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.technical.Turno;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.ui.Player;
import org.jetbrains.annotations.NotNull;
import it.univaq.technical.Turno.Risultato;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HereToSlay {

	private Integer maxGiocatori;
	private Integer idPartita;
	private Integer opzioni;
	private List<Player> elencoGiocatori;
	private Player giocatoreAttivo;
	private Tavolo tavolo;
	private Turno turnoAttuale;
	private Dado dado;
	private Carta cartaAttiva;
    private FaseModificatori faseModificatori;
    private GeneratoreDiEventi generatoreDiEventi;
    private FinestraTemporale  finestraTemporale;
	private Turno turnoCorrente;
	private Fase FaseCorrente;
	Scanner tastiera =new Scanner(System.in);

	/**
	 * 
	 * @param carta
	 * @param tipo
	 * @param target
	 * @param opzione
	 */
	public void giocaCarta(Carta carta, Integer tipo, Player target, Integer opzione) {
        switch (carta) {
            case CartaEroe cartaEroe: System.out.println("da fare");
                break;

            case CartaMagia cartaMagia: System.out.println("da fare");
                break;

            case CartaOggettoMaledetto cartaOggettoMaledetto:  System.out.println("da fare");
                break;

            case CartaOggettoDiSupporto cartaOggettoDiSupporto:  System.out.println("da fare");
                break;

            case CartaOggetto cartaOggetto: System.out.println("da fare");
                break;

            case CartaSfida cartaSfida:
                turnoAttuale.iniziaFase(faseModificatori);
                generatoreDiEventi.startTimerL(faseModificatori);
                faseModificatori.salvaPunteggio(giocatoreAttivo.getId(), 2.0F);
                break;

            case CartaModificatore cartaModificatore:  {
                while (finestraTemporale.isAncoraValida()){
                    generatoreDiEventi.resetTimerL(faseModificatori);
                    turnoAttuale.getFaseCorrente();
                    faseModificatori.calcoloPunteggio(carta, giocatoreAttivo, 0 );
                    faseModificatori.ottieniPunteggi(giocatoreAttivo.getId());
                }
                faseModificatori.ottieniPunteggi(giocatoreAttivo.getId());
                turnoAttuale.fineFaseAttuale();
            }
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + carta);
        }


        if (carta.getClass().equals(CartaModificatore.class)) {

        }
		throw new UnsupportedOperationException();
	}

	public void giocoCarta(Carta Carta){
		FaseCorrente= turnoCorrente.getFaseCorrente();
		if (FaseCorrente instanceof FaseGiocaCarta faseGiocaCarta) {
			faseGiocaCarta.salvaCartaGiocata(Carta);
			System.out.println("Carta salvata correttamente nella fase.");
		} else {
			System.out.println("Errore di flusso: La fase corrente non è una FaseGiocaCarta!");
		}
		//FaseSfida Fasesfida = new FaseSfida();
		//turnoCorrente.iniziaFase(Fasesfida);
		tavolo.aggiungiCartaParty(Carta, giocatoreAttivo);
		//System.out.println("Vuoi attivare l'effetto?");
		//boolean valore= tastiera.nextBoolean();
		tavolo.checkVittoria(giocatoreAttivo);
	}

	public void timeout() {


		System.out.println("HereToSlay: Ricevuto timeout! Nessuno ha giocato una carta Sfida.");

		// 2.1: fineFaseAttuale() -> Chiude la FaseSfida
		turnoCorrente.fineFaseAttuale();

		System.out.println("HereToSlay: La carta Eroe entra in gioco senza ostacoli.");
		// ... Qui proseguirà il diagramma (es. richiestaUtilizzoEffetto) ...
	}

    public String rispostaUtente(@NotNull String scelta) {
        switch (scelta) {
            case "Si":
                // 1.Gestione Fase Effetto
                FaseEffetto faseEffetto = new FaseEffetto();
                faseEffetto.salvaCarta(cartaAttiva);
                this.turnoAttuale.aggiungiFase(faseEffetto);
                // 2.Tiro dadi
                Integer valoreDadi = this.tiraDadi();
                // 3.Gestione Fase Modificatori
                FaseModificatori faseModificatori = new FaseModificatori();
                faseModificatori.salvaPunteggio(giocatoreAttivo.getId(), valoreDadi);
                this.turnoAttuale.aggiungiFase(faseModificatori);
                return "Il valore attuale del tiro è: " + valoreDadi + ", inizio fase modificatori";

            case "No":
                return "Fine Punto Azione";

            default:
                return "Scelta non valida";
        }
    }



    public String checkAttivazioneEffetto(int punteggioDefinitivo) {
        Fase faseEffetto = this.turnoAttuale.getFaseCorrente();
        if (faseEffetto instanceof FaseEffetto faseEffetto1) {
            Boolean attivazione = faseEffetto1.checkAttivazioneEffetto(punteggioDefinitivo);
            if (attivazione){
                faseEffetto1.ottieniEffetto();
                return "Fine Punto Azione";
            }
            else{
                return "Non puoi attivare l'effetto";
            }
        }
        return "Non puoi attivare l'effetto";
    }

	/**
	 * 
	 * @param mossaSelezionata
	 */
	public void richiestaMossa(int mossaSelezionata) {
		Risultato risultato = this.turnoCorrente.verificaPA(mossaSelezionata);
		if (!risultato.successo()) { // [successo == False]
			// 1.2: messaggioErrorePa() chiamato su GeneratoreDiEventi
			generatoreDiEventi.messaggioErrorePA();
			// 1.3: messaggioMossaSelezionata (ritorno al chiamante)
			System.out.println("Errore: PA insufficienti.");
		} else {
			// 1.4: iniziaFase(faseMossaGiocata) chiamato su Turno
			if (mossaSelezionata == 1) {
				FaseGiocaCarta Fasegiocacarta = new FaseGiocaCarta();
				turnoCorrente.iniziaFase(Fasegiocacarta);
			}
		switch (mossaSelezionata) {
			case 1:
				System.out.println("Gioca Carta Eroe");
				break;
			case 2:
				System.out.println("Gioca Carta Oggetto");
				break;
			case 3:
				System.out.println("Gioca Carta Magia");
				break;
			case 4:
				System.out.println("Pesca Carta dal Mazzo");
				break;
			case 5:
				System.out.println("Utilizza effetto Eroe");
				break;
			case 6:
				System.out.println("Attacca Un Mostro");
				break;
			case 7:
				System.out.println("Scarta Mano");

				break;
			default:
				System.out.println("Mossa non valida");
				break;
		}
		// 1.6 e 1.7: checkPaRimasti() -> ritorna paRimanenti
		int paRimasti = turnoCorrente.checkPaRimasti();
		//se non ci sono più PA, il turno finisce (1.8)
		if (paRimasti <= 0) {
			System.out.println("MessaggioFineTurno - Il tuo turno è terminato.");
			}
		}
	}

	/**
	 * 
	 * @param carta
	 */
	public void utilizzaEffetto(Carta carta) {
		// TODO - implement HereToSlay.utilizzaEffetto
		throw new UnsupportedOperationException();
	}

	public Integer tiraDadi() {
        Random random = new Random();
        int n = 0;
        Integer valoreDadi = 0;
        while (n < 2) {
            valoreDadi = 1 + random.nextInt(12);
            n++;
        }
        return valoreDadi;
	}


}