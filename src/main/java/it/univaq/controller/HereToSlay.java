package it.univaq.controller;

import it.univaq.entity.CartaModificatore;
import it.univaq.technical.FaseEffetto;
import it.univaq.entity.Carta;
import it.univaq.entity.Dado;
import it.univaq.entity.Tavolo;
import it.univaq.ui.Player;

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
		// TODO - implement HereToSlay.richiestaMossa
		throw new UnsupportedOperationException();
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
		// TODO - implement HereToSlay.giocaCarta
		throw new UnsupportedOperationException();
	}

	public void tiraDadi() {
		// TODO - implement HereToSlay.tiraDadi
		throw new UnsupportedOperationException();
	}

}