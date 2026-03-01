package it.univaq.entity;

import it.univaq.ui.Player;

import java.util.HashMap;
import java.util.Map;

public class Tavolo {

	/**
	 * 
	 * @param Carta
	 */
	Map<Player, Party> Party =new HashMap<>();
	public void scartaCarta(Carta Carta) {
		// TODO - implement Tavolo.scartaCarta
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Player
	 */
	public void checkVittoria(Player Player) {
		Party.get(Player).checkVittoria();
	}

	/**
	 * 
	 * @param Carta
	 */
	public void ScartaCarta(Carta Carta) {
		// TODO - implement Tavolo.ScartaCarta
		throw new UnsupportedOperationException();
	}

	public void operation() {
		// TODO - implement Tavolo.operation
		throw new UnsupportedOperationException();
	}

	public void aggiungiCartaParty (Carta carta, Player player){
			Party.get(player).inserisciCarta(carta);

	}

}