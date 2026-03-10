package it.univaq.entity;

import it.univaq.ui.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tavolo {

    private PilaScarti pilaScarti;
	private Map<Integer, Party> partyMap;

	public Tavolo(List<Player> players) {
        this.pilaScarti = new PilaScarti();
        this.partyMap = new HashMap<>();

		for (Player p : players) {
			// Estraiamo l'ID e lo associamo a un nuovo Party
			this.partyMap.put(p.getId(), new Party());
		}
	}

	/**
	 * 
	 * @param carta
	 */
	public void scartaCarta(Carta carta) {

        pilaScarti.aggiungiCarta(carta);
	}

	/**
	 *
	 * @param playerId
	 */
	public Boolean checkVittoria(Integer playerId) {
		return partyMap.get(playerId).checkVittoria();
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

	public void aggiungiCartaParty (CartaEroe carta,int playerId){
			partyMap.get(playerId).inserisciCarta(carta);

	}

}