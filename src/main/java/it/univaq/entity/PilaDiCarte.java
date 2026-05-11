package it.univaq.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PilaDiCarte<T extends Carta> {

	protected final List<T> carte;

	public PilaDiCarte() {
		this.carte = new ArrayList<>();
	}

	public PilaDiCarte(List<T> carteIniziali) {
		this.carte = new ArrayList<>(carteIniziali);
	}

	public void aggiungiInCima(T carta) {
		this.carte.addFirst(carta);
	}

	public T pescaDallaCima() {
		if (carte.isEmpty()) return null;
		return this.carte.removeFirst();
	}

	public void mischia() {
		Collections.shuffle(this.carte);
	}

	public int size() {
		return this.carte.size();
	}

	public boolean isEmpty() {
		return this.carte.isEmpty();
	}
}