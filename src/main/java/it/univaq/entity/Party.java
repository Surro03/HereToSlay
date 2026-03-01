package it.univaq.entity;

import it.univaq.technical.FaseGiocaCarta;
import it.univaq.entity.CartaMostro;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.List;

public class Party {

	private Integer Player;
	private List<Carta> Carte;
	private Set<ClasseEroe> classiPresenti = new HashSet<>();

	/**
	 * 
	 *
	 */
	public void checkVittoria() {
		for(Carta Carta: Carte){
			if (Carta instanceof CartaEroe cartaEroe){
				classiPresenti.add(cartaEroe.getClasseEroe());
			}else {
				System.out.println("Errore di flusso: La carta non è una CartaEroe!");
			}
		}
		System.out.println("Classi uniche presenti nel Party: " + classiPresenti.size());
		if (classiPresenti.size() >= 6) { //
			System.out.println("VITTORIA! Il giocatore " + Player + " ha radunato 6 classi diverse!");
		}
	}

	/**
	 * 
	 * @param Player
	 */
	public void ottieniNumClassi(int Player) {
		// TODO - implement Party.ottieniNumClassi
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Carta
	 */
	public void inserisciCarta(Carta Carta) {
		Carte.add(Carta);
		System.out.println("lista carte aggiornato");
	}

}