package it.univaq.entity;

import it.univaq.technical.FaseGiocaCarta;
import it.univaq.entity.CartaMostro;

import java.util.*;
import java.util.List;

public class Party {

	private Integer player;
	private List<Carta> party;
	private Set<ClasseEroe> classiPresenti = new HashSet<>();

	public Party() {
		this.party = new ArrayList<>();
	}

	/**
	 *
	 * @param
	 *
	 */
	public Boolean checkVittoria() {
		for(Carta Carta: party){
			if (Carta instanceof CartaEroe cartaEroe){
				classiPresenti.add(cartaEroe.getClasseEroe());
			}else {
				System.out.println("Errore di flusso: La carta non è una CartaEroe!");
			}
		}
		System.out.println("Classi uniche presenti nel party: " + classiPresenti.size());
        //
        //System.out.println("VITTORIA! Il giocatore " + player + " ha radunato 6 classi diverse!");
        return classiPresenti.size() >= 6;
	}

	/**
	 * 
	 * @param player
	 */
	public void ottieniNumClassi(int player) {
		// TODO - implement party.ottieniNumClassi
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Carta
	 */
	public void inserisciCarta(Carta Carta) {
		party.add(Carta);
		System.out.println("lista carte aggiornato");
	}

}