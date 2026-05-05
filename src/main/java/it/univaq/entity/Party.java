package it.univaq.entity;

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
        return classiPresenti.size() >= 6;
	}

	public int numClassiDiverse() {
		for(CartaEroe cartaEroe: party){
			classiPresenti.add(cartaEroe.getClasseEroe());
		}
		return classiPresenti.size();
	}

	public boolean hasPartyEmpty() {
		return party.isEmpty();
	}

	public Integer getPlayer() {
		return player;
	}

	/**
	 * 
	 * @param cartaEroe
	 */
	public void inserisciCarta(CartaEroe cartaEroe) {
		party.add(cartaEroe);
	}

}