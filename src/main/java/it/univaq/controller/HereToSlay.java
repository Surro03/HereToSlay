package it.univaq.controller;
import it.univaq.entity.*;
import it.univaq.technical.Fase;
import it.univaq.technical.*;
import it.univaq.technical.FaseModificatori;
import it.univaq.technical.FaseEffetto;
import it.univaq.ui.FinestraTemporale;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.technical.Turno;
import it.univaq.ui.Player;

import it.univaq.technical.Turno.Risultato;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HereToSlay {

	private Integer maxGiocatori;
	private Integer idPartita;
	private Integer opzioni;
	private List<Player> elencoGiocatori;
	private Player giocatoreAttivo;
	private final Tavolo tavolo;
	private Turno turnoAttuale;
	private final Dado dado;
	private Carta cartaAttiva;
    private GeneratoreDiEventi generatoreDiEventi;
    private FinestraTemporale  finestraTemporale;
	private Fase FaseCorrente;
	Scanner tastiera = new Scanner(System.in);

	public HereToSlay(Integer maxGiocatori, Integer idPartita, Integer opzioni, List<Player> elencoGiocatori) {
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
	}
/*
	/**
	 * 
	 * @param carta
	 * @param tipo
	 * @param target
	 * @param opzione
	 */
	/*public void giocaCarta(Carta carta, Integer tipo, Player target, Integer opzione) {
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
				FaseModificatori faseModificatori = new FaseModificatori();
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
	}*/


	//Gioca carta Oggetto
	public void giocaCarta (CartaOggetto cartaOggetto, CartaEroe cartaEroe){
		System.out.println("da fare");
	}
    //Gioca carta Modificatore
	public void giocaCarta ( CartaModificatore carta, Player player){
		System.out.println("da fare");
	}
	//Gioca carta Eroe
	public  void giocaCarta(CartaEroe cartaEroe){
		FaseCorrente = turnoAttuale.getFaseCorrente();
		if (FaseCorrente instanceof FaseGiocaCarta faseGiocaCarta) {
			faseGiocaCarta.salvaCartaGiocata(cartaEroe);
			System.out.println("Carta salvata correttamente nella fase.");
			tavolo.aggiungiCartaParty(cartaEroe, giocatoreAttivo);
			tavolo.checkVittoria(giocatoreAttivo);
		}else {
			System.out.println("Errore di flusso: La fase corrente non è una FaseGiocaCarta!");
		}
	}

    //Gioca carta Sfida
	public void giocaCarta(CartaSfida cartaSfida){
			System.out.println("da fare");
			// TODO

	}

	public void timeout() {
		System.out.println("HereToSlay: Ricevuto timeout! Nessuno ha giocato una carta Sfida.");

		// 2.1: fineFaseAttuale() -> Chiude la FaseSfida
		turnoAttuale.fineFaseAttuale();

		System.out.println("HereToSlay: La carta Eroe entra in gioco senza ostacoli.");
		// ... Qui proseguirà il diagramma (es. richiestaUtilizzoEffetto) ...
	}

    public String rispostaUtente(String scelta) {
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
		Risultato risultato = this.turnoAttuale.verificaPA(mossaSelezionata);
		if (!risultato.successo()) { // [successo == False]
			// 1.2: messaggioErrorePa() chiamato su GeneratoreDiEventi
			generatoreDiEventi.messaggioErrorePA();
			// 1.3: messaggioMossaSelezionata (ritorno al chiamante)
			System.out.println("Errore: PA insufficienti.");
		} else {
			// 1.4: iniziaFase(faseMossaGiocata) chiamato su Turno
		switch (mossaSelezionata) {
			case 1:
				System.out.println("Gioca Carta Eroe");
				FaseGiocaCarta faseGiocaCarta = new FaseGiocaCarta();
				turnoAttuale.iniziaFase(faseGiocaCarta);
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
		int paRimasti = turnoAttuale.getPaRimasti();
		//se non ci sono più PA, il turno finisce
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

	public Integer getPaRimasti() {
		return this.turnoAttuale.getPaRimasti();
	}

	public Boolean checkVittoria(Player player){
		return tavolo.checkVittoria(player);
	}

	public Integer tiraDadi() {
        int n = 0;
		int risultato = 0;
		while (n<2){
			risultato = risultato + this.dado.tiraDado();
			n = n + 1;
		}
		return risultato;
	}


}