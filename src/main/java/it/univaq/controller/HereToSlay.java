package it.univaq.controller;

import it.univaq.entity.CartaModificatore;
import it.univaq.technical.*;
import it.univaq.entity.Carta;
import it.univaq.entity.Dado;
import it.univaq.entity.Tavolo;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.ui.Player;
import it.univaq.technical.Turno.Risultato;

import java.util.List;

public class HereToSlay {

	private Integer MaxGiocatori;
	private int IdPartita;
	private int Opzioni;
	private List<Player> ElencoGiocatori;
	private Player GiocatoreAttivo;
	private Tavolo Tavolo;
	private FaseEffetto.Turno TurnoAttuale;
	private Dado Dado;
	private Carta CartaAttiva;
	private Turno turnoCorrente;
	private GeneratoreDiEventi generatoreDiEventi;
	private Fase FaseCorrente;

	/**
	 * 
	 * @param Carta
	 * @param Tipo
	 * @param Target
	 * @param Opzione
	 */
	public void giocaCarta(Carta Carta, int Tipo, Player Target, int Opzione) {
		// TODO - implement HereToSlay.giocaCarta
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
		FaseSfida Fasesfida = new FaseSfida();
		turnoCorrente.iniziaFase(Fasesfida);
	}

	public void timeout() {
		// TODO - implement HereToSlay.timeout
		throw new UnsupportedOperationException();
	}

	public void rispostaUtente() {
		// TODO - implement HereToSlay.rispostaUtente
		throw new UnsupportedOperationException();
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
	 * @param Carta
	 */
	public void utilizzaEffetto(Carta Carta) {
		// TODO - implement HereToSlay.utilizzaEffetto
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param carta
	 * @param Target
	 */
	public void giocaCarta(CartaModificatore carta, Player Target) {
		// TODO - implement HereToSlay.utilizzaEffetto
		throw new UnsupportedOperationException();
	}

	public void tiraDadi() {
		// TODO - implement HereToSlay.tiraDadi
		throw new UnsupportedOperationException();
	}

}