package it.univaq.controller;
import it.univaq.entity.*;
import it.univaq.technical.*;
import it.univaq.ui.FinestraTemporale;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.ui.Player;

import it.univaq.technical.Turno.Risultato;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HereToSlay {


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

	public Player getGiocatoreAttivo() {
		return this.giocatoreAttivo;
	}

	public void setGeneratoreDiEventi(GeneratoreDiEventi generatore) {
		this.generatoreDiEventi = generatore;
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

	/**
	 *
	 * @param mossaSelezionata
	 */
	public void sceltaMossa(int mossaSelezionata) {
		Risultato risultato = this.turnoAttuale.verificaPA(mossaSelezionata);
		if (!risultato.successo()) { // [successo == False]
			// 1.2: messaggioErrorePa() chiamato su GeneratoreDiEventi
			this.generatoreDiEventi.messaggioErrorePA();
			// 1.3: messaggioMossaSelezionata (ritorno al chiamante)
			System.out.println("Errore: PA insufficienti.");
		} else {
			// 1.4: iniziaFase(faseMossaGiocata) chiamato su Turno
			switch (mossaSelezionata) {
				case 1:
					this.sceltaEroe();
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
					System.out.println("Scarta Mano e Pesca 5");
					break;
				default:
					System.out.println("Mossa non valida");
					//this.richiestaMossa();
			}
		}

	}

	private void sceltaEroe() {
		CartaEroe cartaScelta;

        System.out.println("\n");
        System.out.println("SCEGLI L'EROE DA GIOCARE");
        System.out.println("-".repeat(40));
        List<CartaEroe> eroiInMano = this.giocatoreAttivo.getMano().getCarteMano().stream()
                .filter(c -> c instanceof CartaEroe)
                .map(c -> (CartaEroe) c)
                .toList();
        if (eroiInMano.isEmpty()) {
            System.out.println("Non hai Eroi da giocare nella tua mano.");
        } else {
            System.out.printf("%-4s | %-18s | %-10s | %-5s%n", "NUMERO", "NOME", "CLASSE", "REQ.");
            System.out.println("-".repeat(40));

            for (int i = 0; i < eroiInMano.size(); i++) {
                CartaEroe e = eroiInMano.get(i);
                System.out.printf("[%d]    | %-18s | %-10s | %d+%n",
                        (i + 1),
                        e.getNome(),
                        e.getClasseEroe(),
                        e.getRequisito());
            }
            System.out.println("-".repeat(40));
            System.out.print("Digita il numero dell'eroe: ");
        }
        int sceltaCarta = scanner.nextInt();
        scanner.nextLine();
        while (true) {
            if (sceltaCarta > eroiInMano.size()) {
                System.out.println("Devi digitare un numero valido");
            } else {
                cartaScelta = eroiInMano.get(sceltaCarta - 1);
                System.out.println(giocatoreAttivo.getNome() + " gioca la carta: " + cartaScelta.getNome());
                FaseGiocaCarta faseGiocaCarta = new FaseGiocaCarta(cartaScelta); // La carta viene salvata nella FaseGiocaCarta tramite iniezione
                turnoAttuale.iniziaFase(faseGiocaCarta);
                break;
            }
        }
        //In teoria qui andrebbe la fase modificatori
        System.out.println("\n--- Fase 2: Finestra di Sfida ---");
        System.out.println("In attesa di reazioni dagli avversari...");
        // Simuliamo che nessuno giochi una CartaSfida, quindi scatta il timeout
        this.timeout();
		this.giocaCarta(cartaScelta); //Aggiunge la carta al party e verifica la condizione di vittoria della partita
        this.richiestaSceltaEffetto(cartaScelta); //Richiesta di attivazione dell'effetto della carta
    }
	//Gioca carta Oggetto
	public void giocaCarta(CartaOggetto cartaOggetto, CartaEroe cartaEroe) {
		System.out.println("da fare");
	}

	//Gioca carta Eroe
	public void giocaCarta(CartaEroe cartaEroe) {
//		Fase faseCorrente = turnoAttuale.getFaseCorrente();
//		if (faseCorrente instanceof FaseGiocaCarta faseGiocaCarta) {
//			faseGiocaCarta.salvaCartaGiocata(cartaEroe);
			this.tavolo.aggiungiCartaParty(cartaEroe, this.giocatoreAttivo.getId());
			this.checkVittoria(this.giocatoreAttivo);
//		} else {
//			System.out.println("Errore di flusso: La fase corrente non è una FaseGiocaCarta!");
//			System.out.println(turnoAttuale.getFaseCorrente());
//		}
	}

	//Gioca carta Sfida
	public void giocaCarta(CartaSfida cartaSfida) {
		System.out.println("da fare");
		// TODO

	}

	//Gioca Carta Modificatore
	public float giocoCarta(CartaModificatore carta, Player target, float valoreCarta) {
		// 1. Recupero la fase corrente dal turno (deve essere FaseModificatori)
		Fase faseCorrente = turnoAttuale.getFaseCorrente();

		if (faseCorrente instanceof FaseModificatori faseModificatori) {
			// 2. Controllo se la finestra temporale per giocare modificatori è ancora attiva
			if (generatoreDiEventi.isTempoValido()) {

				// 3. Reset del timer per permettere altre risposte
				generatoreDiEventi.resetTimerL(faseModificatori);

				// 4. Calcolo e aggiornamento del punteggio sul target
				float nuovoPunteggio = faseModificatori.calcoloPunteggio(valoreCarta, target);

				// 5. La carta viene rimossa dalla mano e messa negli scarti
				this.elencoGiocatori.getFirst().getMano().getCarteMano().remove(carta);
				this.tavolo.scartaCarta(carta);

				System.out.println("Modificatore applicato correttamente.");
				System.out.println("Nuovo punteggio provvisorio per " + target.getNome() + ": " + nuovoPunteggio);

				// 6. Mostro i punteggi aggiornati
				faseModificatori.ottieniPunteggi(target.getId());
				return nuovoPunteggio;
			} else {
				System.out.println("Errore: Tempo scaduto, non puoi più giocare carte Modificatore!");
				return 0;
			}
		} else {
			System.out.println("Errore di flusso: Non puoi giocare un Modificatore in questa fase!");
			return 0;
		}
	}


	public void timeout() {
		System.out.println("Ricevuto timeout! Nessuno ha giocato una carta Sfida.");

		// 2.1: fineFaseAttuale() -> Chiude la FaseSfida
		//turnoAttuale.fineFaseAttuale();

		System.out.println("La carta Eroe entra in gioco senza ostacoli.");

	}

	public void richiestaSceltaEffetto(CartaEroe cartaScelta) {
		String scelta;
		while (true) {
			System.out.println("Vuoi provare ad attivare l'effetto di " + cartaScelta.getNome() + "? (Si/No)");
			scelta = this.scanner.nextLine().trim();
			if (scelta.equalsIgnoreCase("Si")) {
				// Gestione Fase Effetto
				FaseEffetto faseEffetto = new FaseEffetto();
				faseEffetto.salvaCarta(cartaScelta);
				this.turnoAttuale.aggiungiFase(faseEffetto);
				// Tiro dadi
				Integer valoreDadi = this.tiraDadi();
				// Gestione Fase Modificatori
				FaseModificatori faseModificatori = new FaseModificatori();
				faseModificatori.salvaPunteggio(giocatoreAttivo.getId(), valoreDadi);
				this.turnoAttuale.aggiungiFase(faseModificatori);
				System.out.println("Il valore attuale del tiro è: " + valoreDadi + ", inizio fase modificatori");
				generatoreDiEventi.startTimerL(turnoAttuale.getFaseCorrente());
				// Entriamo in fase modificatori
				this.faseModificatori();
				break;
			}

			if (scelta.equalsIgnoreCase("No")) {
				// Se dice No, usciamo e basta senza fare altro
				System.out.println("Effetto non attivato, fine utilizzo PA");
				break;
			}

			System.out.println("Se c'è scritto si o no magari significa che devi mette quelli eh?");
		}

	}

	public String checkAttivazioneEffetto(float punteggioDefinitivo) {
		turnoAttuale.fineFaseAttuale();
		Fase faseEffetto = this.turnoAttuale.getFaseCorrente();
		if (faseEffetto instanceof FaseEffetto faseEffetto1) {
			Boolean attivazione = faseEffetto1.checkAttivazioneEffetto(punteggioDefinitivo);
			if (attivazione) {
				faseEffetto1.ottieniEffetto();
				return "Fine Punto Azione, l'effetto della carta è: " + faseEffetto1.ottieniEffetto();
			} else {
				return "Non puoi attivare l'effetto";
			}
		}
		return "Non puoi attivare l'effetto";
	}

	public Boolean checkPaRimasti() {
		// 1.6 e 1.7: checkPaRimasti() -> ritorna paRimanenti
		int paRimasti = turnoAttuale.getPaRimasti();
		//se non ci sono più PA, il turno finisce
		if (paRimasti <= 0) {
			System.out.println("MessaggioFineTurno - Il tuo turno è terminato.");
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

	public void checkVittoria(Player player) {
		Boolean vittoria = this.tavolo.checkVittoria(player.getId());
		if (vittoria){
			System.out.println("Vittoria di: " + player.getNome());
			System.exit(0);
		}
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

//	public CartaEroe sceltaCartaEroe(int i) {
//		Fase faseSceltaCarta = this.turnoAttuale.getFaseCorrente();
//		Carta cartaScelta = faseSceltaCarta.getCarteGiocabili().get(i - 1);
//		if (faseSceltaCarta instanceof FaseGiocaCarta faseGiocaCarta) {
//			faseGiocaCarta.salvaCartaGiocata(cartaScelta);
//		}
//		System.out.println("[AZIONE] " + giocatoreAttivo.getNome() + " gioca la carta: " + cartaScelta.getNome());
//		return (CartaEroe) cartaScelta;
//	}

	public void faseModificatori() {
		FaseModificatori faseModificatori = (FaseModificatori) this.turnoAttuale.getFaseCorrente();
		//float dadiBase = Float.parseFloat(risultatoLancio);
		Player playerAttivo = this.elencoGiocatori.getFirst();
		Float valoreTiroFinale = 0F;
		int noConsecutivi = 0;
		int numeroGiocatori = this.elencoGiocatori.size();
		float valoreModif = 0;
		String scelta;

		while (this.generatoreDiEventi.isTempoValido()) {

			while (true) {
				System.out.println(this.elencoGiocatori.getFirst().getNome() + ", Vuoi giocare un modificatore? (Si/No)");
				scelta = scanner.nextLine();

				if (scelta.equalsIgnoreCase("Si") || scelta.equalsIgnoreCase("No")) {
					break; // L'input è valido, usciamo dal ciclo
				}

				System.out.println("Se è scritto si o no magari significa che devi mette quelli eh?");
			}


			if (!generatoreDiEventi.isTempoValido()) {
				System.out.println("\n[!] Peccato! Hai premuto Invio, ma il tempo è SCADUTO proprio ora.");
				break; // Esce immediatamente dal ciclo while
			}

			if (scelta.equalsIgnoreCase("Si")) {
				List<CartaModificatore> modificatoriDisponibili = this.elencoGiocatori.getFirst().getMano().getCarteMano().stream()
						.filter(carta -> carta instanceof CartaModificatore)
						.map(c -> (CartaModificatore) c)
						.toList();
				//No modificatori
				if (modificatoriDisponibili.isEmpty()) {
					System.out.println("Non hai carte Modificatore in mano!");
				} else {
					// Filtriamo solo i modificatori e li stampiamo numerati
					System.out.println("\n--- SCEGLI UN MODIFICATORE DA GIOCARE ---");
					// Intestazione della tabella
					System.out.printf("%-8s | %-18s | %-10s | %-10s%n", "NUMERO", "TIPO", "VALORE +", "VALORE -");
					System.out.println("-".repeat(55));

					for (int i = 0; i < modificatoriDisponibili.size(); i++) {
						CartaModificatore mod = modificatoriDisponibili.get(i);

						// Gestione estetica dei valori: se null mettiamo" / ", altrimenti formattiamo il numero
						String vPos = (mod.getValorePositivo() != null) ? String.format("%+.0f", mod.getValorePositivo()) : " / ";
						String vNeg = (mod.getValoreNegativo() != null) ? String.format("%.0f", mod.getValoreNegativo()) : " / ";

						// Stampa della riga
						System.out.printf("[%d]      | %-18s | %-10s | %-10s%n",
								(i + 1),
								"Modificatore",
								vPos,
								vNeg);
					}

					System.out.println("-".repeat(55));
					System.out.print("Digita il numero del modificatore: ");

					int sceltaCartaModif = scanner.nextInt();
					scanner.nextLine();

					CartaModificatore modifScleto = modificatoriDisponibili.get(sceltaCartaModif - 1);


					if (modifScleto.getValorePositivo() != null && modifScleto.getValoreNegativo() != null) {
						System.out.println("Scegli il valore della carta modificatore");
						System.out.println("1 Applica: " + modifScleto.getValorePositivo());
						System.out.println("2 Applica: " + modifScleto.getValoreNegativo());

						int sceltaSegno = scanner.nextInt();
						scanner.nextLine();

						if (!generatoreDiEventi.isTempoValido()) {
							System.out.println("Tempo scaduto durante la scelta del valore!");
							break;
						}

						if (sceltaSegno == 1) {
							valoreModif = modifScleto.getValorePositivo();
						} else if (sceltaSegno == 2) {
							valoreModif = modifScleto.getValoreNegativo();
						} else {
							System.out.println("Bastardo scegli tra 1 e due");
						}
					} else if (modifScleto.getValorePositivo() != null && modifScleto.getValoreNegativo() == null) {
						valoreModif = modifScleto.getValorePositivo();
					} else if (modifScleto.getValoreNegativo() != null && modifScleto.getValorePositivo() == null) {
						valoreModif = modifScleto.getValoreNegativo();
					}
					valoreTiroFinale = this.giocoCarta(modifScleto, playerAttivo, valoreModif);
					noConsecutivi = 0;
				}
			} else {
				noConsecutivi++;
				this.resetTimer();
			}

			if (noConsecutivi >= numeroGiocatori) {
				String confermaTermina;

				while (true) {
					System.out.println("\n[?] Entrambi avete passato. Volete terminare la fase ora senza attendere il timer? (Si/No)");
					confermaTermina = scanner.nextLine();

					if (confermaTermina.equalsIgnoreCase("Si") || confermaTermina.equalsIgnoreCase("No")) {
						break; // L'input è valido, usciamo dal ciclo
					}

					System.out.println("Se è scritto si o no magari significa che devi mette quelli eh?");
				}

				if (confermaTermina.equalsIgnoreCase("Si")) {
					valoreTiroFinale = faseModificatori.ottieniPunteggi(playerAttivo.getId());
					generatoreDiEventi.stopTimer(); // Ferma il task del messaggio "Tempo Scaduto"
					break; // Esci dal ciclo e vai al calcolo finale
				} else {
					// Se dicono No, resettiamo uno dei counter per dare un'altra chance
					// o lasciamo che il timer scorra normalmente.
					noConsecutivi = 0;
				}
			}
			Collections.rotate(elencoGiocatori, -1);
		}
		this.turnoAttuale.fineFaseAttuale(); //Fine Fase Modificatori
		System.out.printf("Valore finale del tiro di "+ playerAttivo.getNome()+ ": "+ "%+.0f", valoreTiroFinale);
		System.out.println("\n--- Fase 4: Verifica Requisiti ---");
		String esitoEffetto = this.checkAttivazioneEffetto(valoreTiroFinale); // [cite: 112, 146]
		System.out.println("[ESITO] " + esitoEffetto);
		this.turnoAttuale.fineFaseAttuale(); //Fine Fase Effetto
		this.turnoAttuale.fineFaseAttuale(); //Fine Fase Gioco Carta
	}
}