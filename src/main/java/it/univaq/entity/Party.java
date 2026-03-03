package it.univaq.entity;

import it.univaq.technical.FaseGiocaCarta;
import it.univaq.entity.CartaMostro;

import java.util.*;
import java.util.List;

public class Party {

	private Integer player;
	private List<CartaEroe> party;
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
		for(CartaEroe cartaEroe: party){
				classiPresenti.add(cartaEroe.getClasseEroe());
		}
		System.out.println("Classi uniche presenti nel party: " + classiPresenti.size());
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
	 * @param cartaEroe
	 */
	public void inserisciCarta(CartaEroe cartaEroe) {
		party.add(cartaEroe);
		System.out.println("lista carte aggiornato");
	}

}