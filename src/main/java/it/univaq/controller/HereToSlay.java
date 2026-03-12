package it.univaq.controller;
import it.univaq.entity.*;
import it.univaq.technical.*;
import it.univaq.technical.FinestraTemporale;
import it.univaq.technical.GeneratoreDiEventi;
import it.univaq.entity.Player;

import it.univaq.technical.Turno.Risultato;
import it.univaq.ui.InterfacciaUtente;

import java.util.*;

public class HereToSlay{

	private InterfacciaUtente gui;
	private Integer maxGiocatori;
	private Integer idPartita;
	private Integer opzioni;
	private final List<Player> elencoGiocatori;
	private Player giocatoreAttivo;
	private final Tavolo tavolo;
	private final Turno turnoAttuale;
	private final Dado dado;
	private GeneratoreDiEventi generatoreDiEventi;
	private FinestraTemporale finestraTemporale;
	Scanner scanner = new Scanner(System.in);

	public HereToSlay(Integer maxGiocatori, Integer idPartita, Integer opzioni, List<Player> elencoGiocatori, InterfacciaUtente interfacciaUtente) {
		this.maxGiocatori = maxGiocatori;
		this.idPartita = idPartita;
		this.opzioni = opzioni;
		List<Fase> pilaFasi = new ArrayList<>();
		pilaFasi.add(new FaseScelta());
		this.elencoGiocatori = elencoGiocatori;
		this.giocatoreAttivo = elencoGiocatori.getFirst();
		this.tavolo = new Tavolo(elencoGiocatori);
		this.turnoAttuale = new Turno(pilaFasi, this.giocatoreAttivo);
		this.dado = new Dado(6);
		this.gui = interfacciaUtente;
	}

	public Player getGiocatoreAttivo() {
		return this.giocatoreAttivo;
	}

	public Fase getFaseAttuale() {
		return this.turnoAttuale.getFaseCorrente();
	}

	public void setGeneratoreDiEventi(GeneratoreDiEventi generatore) {
		this.generatoreDiEventi = generatore;
	}

	public void iniziaPartita() {
		while (this.checkPaRimasti()) {
			this.iniziaTurno();
			int mossa = this.richiestaMossa();
			this.sceltaMossa(mossa);
			this.checkVittoriaPerClassi(this.getGiocatoreAttivo());
		}
	}

	public void iniziaTurno() {
		List<CartaEroe> eroiInMano = this.giocatoreAttivo.getMano().getCarteMano().stream()
				.filter(c -> c instanceof CartaEroe)
				.map(c -> (CartaEroe) c)
				.toList();
		gui.mostraMenuInizioTurno(this.turnoAttuale.getFaseCorrente(), this.giocatoreAttivo, eroiInMano.isEmpty(), this.turnoAttuale.getPaRimasti());
	}

	public int richiestaMossa() {
		int sceltaMossa;
		List<CartaEroe> eroiInMano = this.giocatoreAttivo.getMano().getCarteMano().stream()
				.filter(c -> c instanceof CartaEroe)
				.map(c -> (CartaEroe) c)
				.toList();
		while (true) {
			sceltaMossa = gui.chiediSelezioneMossa(false);
			if (sceltaMossa == 1 && eroiInMano.isEmpty()) {
				gui.chiediSelezioneMossa(true);
			} else if (sceltaMossa > 0 && sceltaMossa < 7) {
				break;
			} else {
				gui.chiediSelezioneMossa(true);
			}
		}
		return sceltaMossa;
	}

	/**
	 *
	 * @param mossaSelezionata
	 */
	public void sceltaMossa(int mossaSelezionata) {
		Risultato risultato = this.turnoAttuale.verificaPA(mossaSelezionata);
		if (!risultato.successo()) { // [successo == False]
			// 1.2: messaggioErrorePa() chiamato su GeneratoreDiEventi
			gui.messaggioMossaSelezionata(risultato.PA(), false, "Mossa");
			// 1.3: messaggioMossaSelezionata (ritorno al chiamante)
		} else {
			// 1.4: iniziaFase(faseMossaGiocata) chiamato su Turno
			switch (mossaSelezionata) {
				case 1:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Gioca Carta Eroe");
					this.sceltaEroe();
					break;
				case 2:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Gioca Carta Oggetto");
					System.out.println("Gioca Carta Oggetto");
					break;
				case 3:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Gioca Carta Magia");
					break;
				case 4:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Pesca Carta dal Mazzo");
					break;
				case 5:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Utilizza effetto Eroe");
					break;
				case 6:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Attacca Un Mostro");
					break;
				case 7:
					gui.messaggioMossaSelezionata(risultato.PA(), true, "Scarta Mano e Pesca 5");
					break;
			}
		}

	}

	private void sceltaEroe() {
		List<CartaEroe> eroiInMano = this.giocatoreAttivo.getMano().getCarteMano().stream()
				.filter(c -> c instanceof CartaEroe)
				.map(c -> (CartaEroe) c)
				.toList();
		CartaEroe cartaScelta = gui.scegliCartaDaGiocare(CartaEroe.class.getSimpleName(), eroiInMano, this.giocatoreAttivo.getNome());
		FaseGiocaCarta faseGiocaCarta = new FaseGiocaCarta(cartaScelta);
		this.turnoAttuale.aggiungiFase(faseGiocaCarta);
		this.giocaCarta(cartaScelta); //Aggiunge la carta al party e verifica la condizione di vittoria della partita
		this.richiestaSceltaEffetto(cartaScelta); //Richiesta di attivazione dell'effetto della carta
	}

	//Gioca carta Oggetto
	public void giocaCarta(CartaOggetto cartaOggetto, CartaEroe cartaEroe) {
		System.out.println("da fare");
	}

	//Gioca carta Eroe
	public void giocaCarta(CartaEroe cartaEroe) {
		//In teoria qui andrebbe la fase sfida
		this.gui.richiestaSfida();
		this.tavolo.aggiungiCartaParty(cartaEroe, this.giocatoreAttivo.getId());
		this.giocatoreAttivo.getMano().getCarteMano().remove(cartaEroe);
		this.checkVittoriaPerClassi(this.giocatoreAttivo);
	}

	//Gioca carta Sfida
	public void giocaCarta(CartaSfida cartaSfida) {
		System.out.println("da fare");
		// TODO

	}



	public void richiestaSceltaEffetto(CartaEroe cartaScelta) {
		Boolean scelta = gui.richiestaUtilizzoEffetto(cartaScelta.getNome());
		if (scelta) {
			// 1. Fase Effetto
			this.gui.rispostaUtilizzoEffetto(true);
			FaseEffetto faseEffetto = new FaseEffetto();
			faseEffetto.salvaCarta(cartaScelta);
			this.turnoAttuale.aggiungiFase(faseEffetto);
			// 2. Tiro dadi
			Integer valoreDadi = this.tiraDadi();
			this.gui.risultatoTiroDadi(valoreDadi);
			// 3. Fase Modificatori
			FaseModificatori faseModificatori = new FaseModificatori();
			faseModificatori.salvaPunteggio(this.giocatoreAttivo.getId(), valoreDadi);
			this.turnoAttuale.aggiungiFase(faseModificatori);
			generatoreDiEventi.startTimerL(this.turnoAttuale.getFaseCorrente());

			// ---> INIEZIONE DELLE DIPENDENZE: Passiamo gli oggetti, la Fase fa il resto <---
			Float valoreTiroFinale = faseModificatori.eseguiFase(
					this.elencoGiocatori,
					this.tavolo,
					this.generatoreDiEventi,
					this.gui,
					this.turnoAttuale.getFaseCorrente()
			);
			// 4. Conclusione
			this.gui.punteggiDefinitivi(valoreTiroFinale, giocatoreAttivo.getNome());
			this.turnoAttuale.fineFaseAttuale(); // Fine Fase Modificatori
			Boolean esitoRequisito = this.checkAttivazioneEffetto(valoreTiroFinale);
			this.gui.esitoRequisito(esitoRequisito, "descrizioneEffetto");


			this.turnoAttuale.fineFaseAttuale(); //Fine Fase Gioco Carta
		}
		else{
			this.gui.rispostaUtilizzoEffetto(false);
			this.turnoAttuale.fineFaseAttuale(); //Fine Fase Gioco Carta
		}
	}

	public Boolean checkAttivazioneEffetto(float punteggioDefinitivo) {
		turnoAttuale.fineFaseAttuale();
		Fase faseEffetto = this.turnoAttuale.getFaseCorrente();
		if (faseEffetto instanceof FaseEffetto faseEffetto1) {
			Boolean attivazione = faseEffetto1.checkAttivazioneEffetto(punteggioDefinitivo);
			if (attivazione) {
				faseEffetto1.ottieniEffetto();
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public Boolean checkPaRimasti() {
		// 1.6 e 1.7: checkPaRimasti() -> ritorna paRimanenti
		int paRimasti = turnoAttuale.getPaRimasti();
		//se non ci sono più PA, il turno finisce
		if (paRimasti <= 0) {
			this.gui.messaggioFineTurno(this.giocatoreAttivo.getNome());
			return false;
		} else
			return true;
	}

	/**
	 *
	 * @param carta
	 */
	public void utilizzaEffetto(Carta carta) {
		// TODO - implement HereToSlay.utilizzaEffetto
		throw new UnsupportedOperationException();
	}

	public Integer getPaRimasti() {
		return this.turnoAttuale.getPaRimasti();
	}

	public void checkVittoriaPerClassi(Player player) {
		Boolean vittoria = this.tavolo.checkVittoria(player.getId()).vittoria();
		int numClassiDiverse = this.tavolo.checkVittoria(player.getId()).numClassiDiverse();
		if (vittoria) {
			this.gui.messaggioVittoria(player.getNome(), "numero di classi diverse di eroi");
			System.exit(0);
		}else
			this.gui.numClassiDiverse(numClassiDiverse);
	}

	public Integer tiraDadi() {
		int n = 0;
		int risultato = 0;
		while (n < 2) {
			risultato = risultato + this.dado.tiraDado();
			n = n + 1;
		}
		return risultato;
	}

	public void resetTimer() {
		Fase faseCorrente = turnoAttuale.getFaseCorrente();

		if (faseCorrente instanceof FaseModificatori faseModificatori) {
			generatoreDiEventi.resetTimerL(faseModificatori);
		}

	}

	public void fineTurno(int i) {
		// TODO
		//this.giocatoreAttivo = elencoGiocatori.get(i);
	}

}