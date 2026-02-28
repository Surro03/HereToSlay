package it.univaq.controller;

import it.univaq.entity.*;
import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;
import it.univaq.technical.FaseModificatori;
import it.univaq.ui.FinestraTemporale;
import it.univaq.ui.GeneratoreDiEventi;
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
    private FaseModificatori faseModificatori;
    private GeneratoreDiEventi generatoreDiEventi;
    private FinestraTemporale  finestraTemporale;

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
                generatoreDiEventi.startTimer(faseModificatori);
                faseModificatori.salvaPunteggio(2.0F,5.0F);
                break;

            case CartaModificatore cartaModificatore:  {
                while (finestraTemporale.isAncoraValida()){
                    generatoreDiEventi.resetTimer(faseModificatori);
                    turnoAttuale.getFaseCorrente();
                }

            }
            break;

            default:
                throw new IllegalStateException("Unexpected value: " + carta);
        }


        if (carta.getClass().equals(CartaModificatore.class)) {

        }
		throw new UnsupportedOperationException();
	}

	public void timeout() {
		// TODO - implement HereToSlay.timeout
		throw new UnsupportedOperationException();
	}

	public void rispostaUtente(String scelta) {
		// TODO - implement HereToSlay.rispostaUtente

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
	public static void utilizzaEffetto(Carta Carta) {
		// TODO - implement HereToSlay.utilizzaEffetto
		throw new UnsupportedOperationException();
	}

	public void tiraDadi() {
		// TODO - implement HereToSlay.tiraDadi
		throw new UnsupportedOperationException();
	}

}