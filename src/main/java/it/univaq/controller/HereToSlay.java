package it.univaq.controller;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Carta;
import it.univaq.entity.Dado;
import it.univaq.entity.Tavolo;
import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;
import it.univaq.technical.Turno;
import it.univaq.ui.Player;

import java.util.List;

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

	/**
	 * 
	 * @param carta
	 * @param tipo
	 * @param target
	 * @param opzione
	 */
	public void giocaCarta(Carta carta, Integer tipo, Player target, Integer opzione) {
		// TODO - implement HereToSlay.giocaCarta
		throw new UnsupportedOperationException();
	}

	public void timeout() {
		// TODO - implement HereToSlay.timeout
		throw new UnsupportedOperationException();
	}

	public void rispostaUtente(String scelta) {
		// TODO - implement HereToSlay.rispostaUtente
        switch (scelta) {
            case "Si":
                HereToSlay.utilizzaEffetto(cartaAttiva);
                turnoAttuale.aggiungiFase(new FaseEffetto());
        }
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param mossaSelezionata
	 */
	public void richiestaMossa(String mossaSelezionata) {
		// TODO - implement HereToSlay.richiestaMossa
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param carta
	 */
	public static void utilizzaEffetto(Carta carta) {
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