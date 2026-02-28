package it.univaq.controller;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Carta;
import it.univaq.entity.Dado;
import it.univaq.entity.Tavolo;
import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;
import it.univaq.technical.FaseModificatori;
import it.univaq.entity.*;
import it.univaq.technical.Fase;
import it.univaq.technical.FaseEffetto;
import it.univaq.technical.FaseModificatori;
import it.univaq.ui.FinestraTemporale;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.technical.Turno;
import it.univaq.ui.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

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

    public String rispostaUtente(@NotNull String scelta) {
        switch (scelta) {
            case "Si":
                // 1.Gestione Fase Effetto
                FaseEffetto nuovaFaseEffetto = new FaseEffetto();
                nuovaFaseEffetto.salvaCarta(cartaAttiva);
                this.turnoAttuale.aggiungiFase(nuovaFaseEffetto);
                // 2.Tiro dadi
                Integer valoreDadi = this.tiraDadi();
                // 3.Gestione Fase Modificatori
                FaseModificatori nuovaFaseModificatori = new FaseModificatori();
                nuovaFaseModificatori.salvaPunteggio(valoreDadi, giocatoreAttivo.getId());
                this.turnoAttuale.aggiungiFase(nuovaFaseModificatori);
                return "Il valore attuale del tiro è: " + valoreDadi + ", inizio fase modificatori";

            case "No":
                return "Fine Punto Azione";

            default:
                return "Scelta non valida";
        }
    }

    public Fase getFaseAttuale() {
        return this.turnoAttuale.getFaseCorrente();
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
	public void utilizzaEffetto(Carta carta) {
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

	public Integer tiraDadi() {
        Random random = new Random();
        int n = 0;
        Integer valoreDadi = 0;
        while (n < 2) {
            valoreDadi = 1 + random.nextInt(6);
            n++;
        }
        return valoreDadi;
	}

}