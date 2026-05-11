package it.univaq.entity;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

public class Party {

	private final List<CartaEroe> eroiInGioco;

	public Party() {
		this.eroiInGioco = new ArrayList<>();
	}

	public int numClassiDiverse() {
		return (int) this.eroiInGioco.stream()
				.map(CartaEroe::getClasseEroe)
				.distinct()
				.count();
	}

	public boolean checkVittoria() {
		return numClassiDiverse() >= 6;
	}

	public boolean hasPartyEmpty() {
		return this.eroiInGioco.isEmpty();
	}

	public void inserisciEroe(CartaEroe cartaEroe) {
		this.eroiInGioco.add(cartaEroe);
	}

	public void rimuoviEroe(CartaEroe cartaEroe) {
		this.eroiInGioco.remove(cartaEroe);
	}

	// Se a qualche fase serve leggere gli eroi, restituisco una vista sicura!
	public List<CartaEroe> getEroiNelParty() {
		return Collections.unmodifiableList(this.eroiInGioco);
	}
}