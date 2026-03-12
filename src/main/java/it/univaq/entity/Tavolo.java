package it.univaq.entity;

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

	public record VittoriaPerClassi(Boolean vittoria, int numClassiDiverse) {}

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
	public VittoriaPerClassi checkVittoria(Integer playerId) {
		Party partyPlayer = partyMap.get(playerId);
        return new VittoriaPerClassi(partyPlayer.checkVittoria(), partyPlayer.numClassiDiverse());
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