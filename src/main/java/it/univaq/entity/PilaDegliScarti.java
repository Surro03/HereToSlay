package it.univaq.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PilaDegliScarti extends PilaDiCarte<Carta> {

	public List<Carta> getCarteVisibili() {
		// Restituisce una vista di sola lettura.
		return Collections.unmodifiableList(this.carte);
	}

	// Il metodo per prelevare materialmente la carta scelta
	public Carta prelevaCartaSpecifica(int indice) {
		return this.carte.remove(indice);
	}

}