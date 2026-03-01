package it.univaq.entity;

import it.univaq.ui.Player;

import java.util.HashMap;
import java.util.Map;

public class Tavolo {

    private PilaScarti pilaScarti;
    Map<Player, Party> Party =new HashMap<>();
	/**
	 * 
	 * @param carta
	 */
	public void scartaCarta(Carta carta) {
        pilaScarti.aggiungiCarta(carta);
	}

	/**
	 * 
	 * @param player
	 */
	public void checkVittoria(Player player) {
		Party.get(player).checkVittoria();
	}

	/**
	 * 
	 * @param carta
	 */
	public void ScartaCarta(Carta carta) {
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